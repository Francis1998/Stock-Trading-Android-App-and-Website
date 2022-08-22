package com.example.httptest.model;

public class RecommendationTrendModel {
    private Integer buy;
    private Integer hold;
    private String period;
    private Integer sell;
    private Integer strongBuy;
    private Integer strongSell;
    private String symbol;

    public RecommendationTrendModel() {
    }

    public RecommendationTrendModel(Integer buy, Integer hold, String period, Integer sell, Integer strongBuy, Integer strongSell, String symbol) {
        this.buy = buy;
        this.hold = hold;
        this.period = period;
        this.sell = sell;
        this.strongBuy = strongBuy;
        this.strongSell = strongSell;
        this.symbol = symbol;
    }

    public Integer getBuy() {
        return buy;
    }

    public void setBuy(Integer buy) {
        this.buy = buy;
    }

    public Integer getHold() {
        return hold;
    }

    public void setHold(Integer hold) {
        this.hold = hold;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getSell() {
        return sell;
    }

    public void setSell(Integer sell) {
        this.sell = sell;
    }

    public Integer getStrongBuy() {
        return strongBuy;
    }

    public void setStrongBuy(Integer strongBuy) {
        this.strongBuy = strongBuy;
    }

    public Integer getStrongSell() {
        return strongSell;
    }

    public void setStrongSell(Integer strongSell) {
        this.strongSell = strongSell;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
