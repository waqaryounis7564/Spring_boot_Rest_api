package com.example.demo.service;

import com.example.demo.entity.SenatorFiling;
import com.example.demo.entity.SenatorTrades;
import com.example.demo.util.ParameterUtils;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SenatorData {


    public JSONObject scrapeData(boolean isFullRun) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("key", "value");
        try (WebClient webClient = new WebClient()) {
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(true);
            final HtmlPage page = webClient.getPage("https://efdsearch.senate.gov/search/home/");

            final DomElement ipbtn = page.getElementById("agree_statement");
            final HtmlPage page2 = ipbtn.click();
            DomElement chkbox = page2.querySelector("[value='11']");
            HtmlInput start_date = page2.querySelector("[name='submitted_start_date']");
            HtmlInput end_date = page2.querySelector("[name='submitted_end_date']");
            DomElement searchBtn = page2.querySelector("#searchForm > div > button");
            chkbox.click();

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String dateFrom = getStartDate(dateFormat, isFullRun);
            String dateTo = getEndDate(dateFormat);

            start_date.click();
            start_date.setValueAttribute(dateFrom);

            end_date.click();
            end_date.setValueAttribute(dateTo);

            HtmlPage xhr = searchBtn.click();
            webClient.waitForBackgroundJavaScript(10000);
            String result = xhr.asXml();
            Document res = Jsoup.parse(result);
            Elements report_rows = res.select("tbody>tr");
            do {
                try (WebClient detailWebClient = new WebClient()) {
                    detailWebClient.getOptions().setCssEnabled(false);
                    detailWebClient.getOptions().setJavaScriptEnabled(true);
                    HtmlPage tempPage = detailWebClient.getPage("https://efdsearch.senate.gov" + report_rows.get(0).select("td:nth-child(4)>a").attr("href"));
                    final DomElement agr = tempPage.getElementById("agree_statement");
                    agr.click();
                    report_rows.forEach(
                            r -> consumeEachFiling(r, detailWebClient)
                    );
                }
                if (Jsoup.parse(xhr.asXml()).getElementById("filedReports_next").hasClass("disabled"))
                    break;
                xhr = xhr.getElementById("filedReports_next").click();
                webClient.waitForBackgroundJavaScript(10000);
                report_rows = Jsoup.parse(xhr.asXml()).select("tbody>tr");

            } while (report_rows.size() > 0);
        }
        return obj;
    }

    private void consumeEachFiling(Element report_row, WebClient detailWebClient) {

        String name = report_row.select("td:nth-child(1)").text();
        String lastName = report_row.select("td:nth-child(2)").text();
        String[] officeNameFilerType = report_row.select("td:nth-child(3)").text().split("\\(");
        String officeName = officeNameFilerType[0];
        String filerType;
        if (officeNameFilerType.length > 1) {
            filerType = officeNameFilerType[1].split("\\)")[0];
        } else {
            filerType = officeNameFilerType[0];
            officeName = null;
        }
        String report_Link = report_row.select("td:nth-child(4)>a").attr("href");
        String fillingDate = report_row.select("td:nth-child(5)").text();
        fillingDate = ParameterUtils.getDateInYourFormat(fillingDate, "MM/dd/yyyy", "yyyy-MM-dd");
//        System.out.println("--------------" + fillingDate + "-------------------");

        String reportUrl = "https://efdsearch.senate.gov" + report_Link;

        SenatorFiling senatorFiling = new SenatorFiling();
        senatorFiling.setFirstName(name);
        senatorFiling.setLastName(lastName);
        senatorFiling.setSourceUrl(reportUrl);
        senatorFiling.setFilerType(filerType);
        senatorFiling.setOfficeName(officeName);
        senatorFiling.setDateFiled(fillingDate);
        senatorFiling.setReportType(report_row.select("td:nth-child(4)>a").text());
        Document cv;
        final HtmlPage page3;
        try {
            page3 = detailWebClient.getPage(reportUrl);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        String to = page3.getWebResponse().getContentAsString();
        cv = Jsoup.parse(to);

        if (report_Link.contains("search/view/paper/")) {
            List<String> links = new ArrayList<>();
            Elements jpgData = cv.getElementsByClass("img-wrap");
            for (Element value : jpgData) {
                String jpg = value.getElementsByTag("img").attr("src");
                links.add(jpg);
            }
            senatorFiling.setJpgLinks(links);
            writeToDB(senatorFiling);
            return;
        }
        Elements rows = cv.body().select("tbody>tr");
        List<SenatorTrades> senatorTradesList = new ArrayList<>();
        for (Element row : rows) {
            SenatorTrades senatorTrades = new SenatorTrades();
            senatorTrades.setTradeIndex(Integer.valueOf(row.select("td:nth-child(1)").text()));

            senatorTrades.setTransactionDate(ParameterUtils.getDateInYourFormat(row.select("td:nth-child(2)").text(), "MM/dd/yyyy", "yyyy-MM-dd"));
            senatorTrades.setOwner(row.select("td:nth-child(3)").text());
            senatorTrades.setTicker(row.select("td:nth-child(4)").text());
            ArrayList<TextNode> assetName = (ArrayList<TextNode>) row.select("td:nth-child(5)").textNodes();
            senatorTrades.setAssetName(String.valueOf(assetName.get(0)));
            senatorTrades.setAssetType(row.select("td:nth-child(6)").text());
            senatorTrades.setTransactionType(row.select("td:nth-child(7)").text());
            senatorTrades.setAmount(row.select("td:nth-child(8)").text());
            senatorTrades.setComment(row.select("td:nth-child(9)").text());
            senatorTradesList.add(senatorTrades);
        }
        senatorFiling.setSenatorTradesList(senatorTradesList);
        writeToDB(senatorFiling);
    }

    private String getStartDate(SimpleDateFormat simpleDateFormat, boolean isFullRun) {
        Calendar cal = Calendar.getInstance();
        if (isFullRun) {
            cal.add(Calendar.YEAR, -5);
        } else {
            cal.add(Calendar.MONTH, -1);
        }
        Date result = cal.getTime();
        return simpleDateFormat.format(result);
    }

    private String getEndDate(SimpleDateFormat simpleDateFormat) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, LocalDate.now().getDayOfMonth() + 1);
        cal.set(Calendar.MONTH, LocalDate.now().getMonthValue() - 1);
        Date day_ago = cal.getTime();
        return simpleDateFormat.format(day_ago);
    }

    private void writeToDB(SenatorFiling senatorFiling) {
        System.out.println(MessageFormat.format("{0}-----{1}", senatorFiling.getFirstName(), senatorFiling.getDateFiled()));
        System.out.println("********");
        if (senatorFiling.getJpgLinks() != null) {
            System.out.println(MessageFormat.format("Jpg links size : {0}", senatorFiling.getJpgLinks().size()));
        }
        System.out.println("********");
        if (senatorFiling.getSenatorTradesList() != null) {
            System.out.println(MessageFormat.format("Senator trades size : {0}", senatorFiling.getSenatorTradesList().size()));
        }
        System.out.println("------------------------------");
    }

}
