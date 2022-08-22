package com.example.httptest.model;

import java.util.List;

public class SocialSentiment {
    private List<RedditAndTwitterModel> reddit;
    private String symbol;
    private List<RedditAndTwitterModel> twitter;

    public SocialSentiment() {
    }

    public SocialSentiment(List<RedditAndTwitterModel> reddit, String symbol, List<RedditAndTwitterModel> twitter) {
        this.reddit = reddit;
        this.symbol = symbol;
        this.twitter = twitter;
    }

    public List<RedditAndTwitterModel> getReddit() {
        return this.reddit;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public List<RedditAndTwitterModel> getTwitter() {
        return this.twitter;
    }

    public void setReddit(List<RedditAndTwitterModel> reddit) {
        this.reddit = reddit;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setTwitter(List<RedditAndTwitterModel> twitter) {
        this.twitter = twitter;
    }
}
