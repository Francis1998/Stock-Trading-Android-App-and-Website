package com.example.httptest.model;

public class EarningModel {
    private Double actual;
    private Double estimate;
    private String period;
    private Double surprise;
    private Double surprisePercent;
    private String symbol;

    public EarningModel(Double actual, Double estimate, String period, Double surprise, Double surprisePercent, String symbol) {
        this.actual = actual;
        this.estimate = estimate;
        this.period = period;
        this.surprise = surprise;
        this.surprisePercent = surprisePercent;
        this.symbol = symbol;
    }

    public Double getActual() {
        return actual;
    }

    public void setActual(Double actual) {
        this.actual = actual;
    }

    public Double getEstimate() {
        return estimate;
    }

    public void setEstimate(Double estimate) {
        this.estimate = estimate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Double getSurprise() {
        return surprise;
    }

    public void setSurprise(Double surprise) {
        this.surprise = surprise;
    }

    public Double getSurprisePercent() {
        return surprisePercent;
    }

    public void setSurprisePercent(Double surprisePercent) {
        this.surprisePercent = surprisePercent;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
