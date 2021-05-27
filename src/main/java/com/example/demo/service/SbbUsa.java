package com.example.demo.service;

import com.example.demo.util.CssDataNullException;
import com.example.demo.util.ParameterUtils;
import com.example.demo.util.RequestSetter;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.zip.GZIPInputStream;

public class SbbUsa {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String getResponseFromHttpConnection(HttpURLConnection httpURLConnection) throws IOException {
        try (InputStream inputStream = httpURLConnection.getInputStream()) {
            Reader reader;
            if ("gzip".equals(httpURLConnection.getContentEncoding())) {
                reader = new InputStreamReader(new GZIPInputStream(inputStream));
            } else {
                reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            }
            try (BufferedReader br = new BufferedReader(reader)) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                return sb.toString();
            }
        }
    }


    final String[] keywords = {"10-K", "10-Q", "20-F"};

    public boolean startBot() {
        crawlCurrentData();
        return true;
    }


    public void crawlHistory() {
        logger.error("USA History starts");
        Arrays.stream(keywords).forEach(this::crawlHistoricData);
        logger.error("USA History completed");

    }

    private String getResponseForUS(String url) throws IOException {
        URL urls = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
        RequestSetter requestSetter = new RequestSetter();
        requestSetter.setMethod("GET");
        requestSetter.setContentType("application/atom+xml");
        requestSetter.setHost("www.sec.gov");
        requestSetter.setIsInstanceFollowRedirects(true);
        requestSetter.setIsDoOutput(true);
        conn = requestSetter.getHttpUrlConnection(conn);
        conn.addRequestProperty("Connection", "keep-alive");
        return getResponseFromHttpConnection(conn);
    }

    private void crawlHistoricData(String searchKeyWord) {
        int totalAnnouncements = 1;
        String year = new SimpleDateFormat("yyyy").format(new Date());
        int pageSize = 80;
        for (int start = 1; start <= totalAnnouncements; start += pageSize) {
            String url = "https://www.sec.gov/cgi-bin/srch-edgar?text=" + searchKeyWord + "&start=" + start + "&count=" + pageSize + "&first=" + year + "&last=" + year;
            String response;
            try {
                response = getResponseForUS(url);
            } catch (IOException e) {
                logger.error(e.getMessage() + Arrays.toString(e.getStackTrace()));
                return;
            }
            Document document = Jsoup.parse(response);
            int numberOfDiv = document.getElementsByTag("div").size();
            if (numberOfDiv != 1)
                continue;

            if (totalAnnouncements == 1)
                totalAnnouncements = Integer.parseInt(document.getElementsByTag("div").first().getElementsByTag("b").first().ownText());

            Elements rows = document.getElementsByTag("div").first().getElementsByTag("table").get(1).getElementsByTag("tr");
            for (Element row : rows) {
                if (row.getElementsByTag("td").size() != 6)
                    continue;

                if (!containsKeywords(row.getElementsByTag("td").get(3).ownText()))
                    continue;

                String companyName = row.getElementsByTag("td").get(1).text();
                String headline = row.getElementsByTag("td").get(3).ownText() + " - " + companyName;
                String rawDate = row.getElementsByTag("td").get(4).ownText();
                String thisFormat = "MM/dd/yyyy";
                String requiredFormat = "yyyy-MM-dd";
                String announcementDate = ParameterUtils.getDateInYourFormat(rawDate, thisFormat, requiredFormat);
                if (announcementDate == null) {
                    logger.error("date format not recognized " + rawDate);
                    return;
                }
                String savingUrl = "http://www.sec.gov" + row.getElementsByTag("td").get(1).getElementsByTag("a").first().attr("href");


                String stringSavingUrl = savingUrl.replace("http://", "https://");
                String detailedResponse = null;
                try {
                    detailedResponse = getResponseFromUSArchieve2(stringSavingUrl);
                } catch (IOException e) {
                    logger.error(e.getMessage() + Arrays.toString(e.getStackTrace()));
                    return;
                }
                Document documentDetailed = Jsoup.parse(detailedResponse);
                companyName = documentDetailed.getElementsByClass("companyName").first().getElementsByTag("a").first().ownText().split("\\s+")[0];


            }
        }
    }

    private String getResponseFromUSArchieve2(String sourceUrl) throws IOException {
        String response = "";
        try (WebClient webClient = new WebClient()) {
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            HtmlPage page = webClient.getPage(sourceUrl);
            response = page.asXml();
        }
        return response;
    }

    private boolean containsKeywords(String thekeyword) {
        return Arrays.asList(keywords).contains(thekeyword);
    }

    private void crawlCurrentData() {
        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://www.sec.gov/cgi-bin/current?q1=0&q2=0&q3=");
        urls.add("https://www.sec.gov/cgi-bin/current?q1=0&q2=1&q3=");
        urls.forEach(url -> {
            try {
                String formTen = crawlFormTen(url);
                processFormTenData(formTen);
            } catch (IOException | CssDataNullException e) {
                logger.error(e.getMessage() + Arrays.toString(e.getStackTrace()));
            }
        });
        try {
            String formTwenty = crawlFormTwenty();
            processFormTwentyData(formTwenty);
        } catch (IOException | CssDataNullException e) {
            logger.error(e.getMessage() + Arrays.toString(e.getStackTrace()));
        }

    }

    private String crawlFormTwenty() throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(new Date(System.currentTimeMillis() - 7L * 24 * 3600 * 1000));
        String url = "https://www.sec.gov/cgi-bin/srch-edgar?text=" + date + "+AND+20-F&first=2021&last=2021";
        return getResponseForUS(url);
    }

    private String crawlFormTen(String srcUrl) throws IOException {
        return getResponseForUS(srcUrl);
    }

    private void processFormTenData(String response) throws CssDataNullException {
        Document document = Jsoup.parse(response);
        Elements elements = document.select("pre>a:nth-child(odd)");
        if (elements.size() == 0) throw new CssDataNullException();
        for (Element anchor : elements) {
            String companyName = "";
            String headline = "";
            String announcementDate = "";
            if (!containsKeywords(anchor.text())) continue;
            String savingUrl = "http://www.sec.gov" + anchor.attr("href");

            String stringSavingUrl = savingUrl.replace("http://", "https://");
            String detailedResponse;
            try {
                detailedResponse = getResponseFromUSArchieve2(stringSavingUrl);
            } catch (IOException e) {
                logger.error(Arrays.toString(e.getStackTrace()) + e.getMessage());
                continue;
            }
            Document documentDetailed = Jsoup.parse(detailedResponse);
            announcementDate = documentDetailed.select("#formDiv > div.formContent > div:nth-child(1) > div:nth-child(2)").text();
            if (announcementDate.isEmpty())
                throw new CssDataNullException("unable to get filing date, css query breaks");
            String name = documentDetailed.select("#filerDiv > div.companyInfo > span").text().split("\\(Filer\\)")[0];
            if (name.isEmpty()) throw new CssDataNullException("unable to get company name, css query breaks");
            headline = anchor.text() + "-" + name;

            logger.info(headline + ' ' + announcementDate);


        }
    }

    private void processFormTwentyData(String response) throws CssDataNullException {
        Document document = Jsoup.parse(response);
        String rawData = document.select("body > div > center > b").text();
        boolean noData = rawData.contains("No documents matched your query.");
        if (noData) {
            throw new CssDataNullException("fillings for 20-f is not uploaded yet");
        }
        Elements rows = document.getElementsByTag("div").first().getElementsByTag("table").get(1).getElementsByTag("tr");
        for (Element row : rows) {
            if (row.getElementsByTag("td").size() != 6)
                continue;

            if (!containsKeywords(row.getElementsByTag("td").get(3).ownText()))
                continue;

            String companyName = row.getElementsByTag("td").get(1).text();
            String headline = row.getElementsByTag("td").get(3).ownText() + " - " + companyName;
            String rawDate = row.getElementsByTag("td").get(4).ownText();
            String thisFormat = "MM/dd/yyyy";
            String requiredFormat = "yyyy-MM-dd";
            String announcementDate = ParameterUtils.getDateInYourFormat(rawDate, thisFormat, requiredFormat);
            if (announcementDate == null) {
                logger.error("date format not recognized " + rawDate);
                return;
            }
            String savingUrl = "http://www.sec.gov" + row.getElementsByTag("td").get(1).getElementsByTag("a").first().attr("href");

            String stringSavingUrl = savingUrl.replace("http://", "https://");
            String detailedResponse;
            try {
                detailedResponse = getResponseFromUSArchieve2(stringSavingUrl);
            } catch (IOException e) {
                logger.error(Arrays.toString(e.getStackTrace()) + e.getMessage());
                return;
            }
            Document documentDetailed = Jsoup.parse(detailedResponse);

            logger.info(headline + ' ' + announcementDate);

        }

    }
}
