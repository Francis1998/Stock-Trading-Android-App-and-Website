package com.example.httptest.model;

public class MovieModel {
    private String ticker;
    private String sharesNumber;
    private Double c;
    private Double d;
    private Double dp;

    public MovieModel(String ticker, String sharesNumber, Double c, Double d, Double dp) {
        this.ticker = ticker;
        this.sharesNumber = sharesNumber; // also is name
        this.c = c;
        this.d = d;
        this.dp = dp;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getSharesNumber() {
        return sharesNumber;
    }

    public void setSharesNumber(String sharesNumber) {
        this.sharesNumber = sharesNumber;
    }

    public Double getC() {
        return c;
    }

    public void setC(Double c) {
        this.c = c;
    }

    public Double getD() {
        return d;
    }

    public void setD(Double d) {
        this.d = d;
    }

    public Double getDp() {
        return dp;
    }

    public void setDp(Double dp) {
        this.dp = dp;
    }

    @Override
    public String toString() {
        return "MovieModel{" +
                "ticker='" + ticker + '\'' +
                ", sharesNumber='" + sharesNumber + '\'' +
                ", c=" + c +
                ", d=" + d +
                ", dp=" + dp +
                '}';
    }
    //    private String title;
//    private String image;
//
//
//    public MovieModel(String title, String image) {
//        this.title = title;
//        this.image = image;
//    }
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }

//    private String title;
//
//    private String country;
//    private String currency;
//    private String exchange;
//    private String finnhubIndustry;
//    private String ipo;
//    private String logo;
//    private double marketCapitalization;
//    private String name;
//    private String phone;
//    private double shareOutstanding;
//    private String ticker;
//    private String weburl;
//
//    public MovieModel(String country, String currency, String exchange, String finnhubIndustry, String ipo, String logo, double marketCapitalization, String name, String phone, double shareOutstanding, String ticker, String weburl) {
//        this.country = country;
//        this.currency = currency;
//        this.exchange = exchange;
//        this.finnhubIndustry = finnhubIndustry;
//        this.ipo = ipo;
//        this.logo = logo;
//        this.marketCapitalization = marketCapitalization;
//        this.name = name;
//        this.phone = phone;
//        this.shareOutstanding = shareOutstanding;
//        this.ticker = ticker;
//        this.weburl = weburl;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    public String getCurrency() {
//        return currency;
//    }
//
//    public void setCurrency(String currency) {
//        this.currency = currency;
//    }
//
//    public String getExchange() {
//        return exchange;
//    }
//
//    public void setExchange(String exchange) {
//        this.exchange = exchange;
//    }
//
//    public String getFinnhubIndustry() {
//        return finnhubIndustry;
//    }
//
//    public void setFinnhubIndustry(String finnhubIndustry) {
//        this.finnhubIndustry = finnhubIndustry;
//    }
//
//    public String getIpo() {
//        return ipo;
//    }
//
//    public void setIpo(String ipo) {
//        this.ipo = ipo;
//    }
//
//    public String getLogo() {
//        return logo;
//    }
//
//    public void setLogo(String logo) {
//        this.logo = logo;
//    }
//
//    public double getMarketCapitalization() {
//        return marketCapitalization;
//    }
//
//    public void setMarketCapitalization(double marketCapitalization) {
//        this.marketCapitalization = marketCapitalization;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public double getShareOutstanding() {
//        return shareOutstanding;
//    }
//
//    public void setShareOutstanding(double shareOutstanding) {
//        this.shareOutstanding = shareOutstanding;
//    }
//
//    public String getTicker() {
//        return ticker;
//    }
//
//    public void setTicker(String ticker) {
//        this.ticker = ticker;
//    }
//
//    public String getWeburl() {
//        return weburl;
//    }
//
//    public void setWeburl(String weburl) {
//        this.weburl = weburl;
//    }

}
