package com.example.demo.entity;

import java.math.BigDecimal;

public class SenatorTrades {
    private Integer filingId;
    private Integer tradeIndex;
    private String transactionDate;
    private String owner;
    private String ticker;
    private String assetName;
    private String assetType;
    private String transactionType;
    private String amount;
    private String comment;
    private String jpgLink;
    private String gvkey;
    private String sector;
    private Boolean isCapitalGain = false;
    private String permIdAsset;
    private String permIdFiler;
    private String assetSubCategory;
    private String price;
    private String currencyCode;
    private BigDecimal compustatPrice;
    private String compustatCurrencyCode;
    private Integer amountAsNumber;
    private String shareRange;
    private String originalName;

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getShareRange() {
        return shareRange;
    }

    public void setShareRange(String shareRange) {
        this.shareRange = shareRange;
    }

    private String rowCreation;

    public Integer getFilingId() {
        return filingId;
    }

    public void setFilingId(Integer filingId) {
        this.filingId = filingId;
    }

    public Integer getTradeIndex() {
        return tradeIndex;
    }

    public void setTradeIndex(Integer tradeIndex) {
        this.tradeIndex = tradeIndex;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getJpgLink() {
        return jpgLink;
    }

    public void setJpgLink(String jpgLink) {
        this.jpgLink = jpgLink;
    }

    public String getGvkey() {
        return gvkey;
    }

    public void setGvkey(String gvkey) {
        this.gvkey = gvkey;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getPermIdAsset() {
        return permIdAsset;
    }

    public void setPermIdAsset(String permIdAsset) {
        this.permIdAsset = permIdAsset;
    }

    public String getPermIdFiler() {
        return permIdFiler;
    }

    public void setPermIdFiler(String permIdFiler) {
        this.permIdFiler = permIdFiler;
    }

    public Boolean getCapitalGain() {
        return isCapitalGain;
    }

    public void setCapitalGain(Boolean capitalGain) {
        isCapitalGain = capitalGain;
    }

    public String getAssetSubCategory() {
        return assetSubCategory;
    }

    public void setAssetSubCategory(String assetSubCategory) {
        this.assetSubCategory = assetSubCategory;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getRowCreation() {
        return rowCreation;
    }

    public void setRowCreation(String rowCreation) {
        this.rowCreation = rowCreation;
    }

    public BigDecimal getCompustatPrice() {
        return compustatPrice;
    }

    public void setCompustatPrice(BigDecimal compustatPrice) {
        this.compustatPrice = compustatPrice;
    }

    public String getCompustatCurrencyCode() {
        return compustatCurrencyCode;
    }

    public void setCompustatCurrencyCode(String compustatCurrencyCode) {
        this.compustatCurrencyCode = compustatCurrencyCode;
    }

    public Integer getAmountAsNumber() {
        return amountAsNumber;
    }

    public void setAmountAsNumber(Integer amountAsNumber) {
        this.amountAsNumber = amountAsNumber;
    }
}
