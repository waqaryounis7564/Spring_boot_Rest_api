package com.example.demo.entity;

import java.util.List;

public class SenatorFiling {
    private Integer filingId;
    private String firstName;
    private String lastName;
    private String officeName;
    private String x2iqFirstName;
    private String x2iqLastName;
    private String x2iqOfficeName;
    private String filerType;
    private String reportType;
    private String dateFiled;
    private String sourceUrl;
    private String isAmended;
    private List<SenatorTrades> senatorTradesList;
    private List<String> jpgLinks;

    public Integer getFilingId() {
        return filingId;
    }

    public void setFilingId(Integer filingId) {
        this.filingId = filingId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getX2iqFirstName() {
        return x2iqFirstName;
    }

    public void setX2iqFirstName(String x2iqFirstName) {
        this.x2iqFirstName = x2iqFirstName;
    }

    public String getX2iqLastName() {
        return x2iqLastName;
    }

    public void setX2iqLastName(String x2iqLastName) {
        this.x2iqLastName = x2iqLastName;
    }

    public String getX2iqOfficeName() {
        return x2iqOfficeName;
    }

    public void setX2iqOfficeName(String x2iqOfficeName) {
        this.x2iqOfficeName = x2iqOfficeName;
    }

    public String getFilerType() {
        return filerType;
    }

    public void setFilerType(String filerType) {
        this.filerType = filerType;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getDateFiled() {
        return dateFiled;
    }

    public void setDateFiled(String dateFiled) {
        this.dateFiled = dateFiled;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getIsAmended() {
        return isAmended;
    }

    public void setIsAmended(String isAmended) {
        this.isAmended = isAmended;
    }

    public List<SenatorTrades> getSenatorTradesList() {
        return senatorTradesList;
    }

    public void setSenatorTradesList(List<SenatorTrades> senatorTradesList) {
        this.senatorTradesList = senatorTradesList;
    }

    public List<String> getJpgLinks() {
        return jpgLinks;
    }

    public void setJpgLinks(List<String> jpgLinks) {
        this.jpgLinks = jpgLinks;
    }


}
