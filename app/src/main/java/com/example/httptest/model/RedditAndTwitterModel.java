package com.example.httptest.model;

public class RedditAndTwitterModel {
    private String atTime;
    private Long mention;
    private Double positiveScore;
    private Double negativeScore;
    private Long positiveMention;
    private Long negativeMention;
    private Double score;

    public RedditAndTwitterModel(String atTime, Long mention, Double positiveScore, Double negativeScore, Long positiveMention, Long negativeMention, Double score) {
        this.atTime = atTime;
        this.mention = mention;
        this.positiveScore = positiveScore;
        this.negativeScore = negativeScore;
        this.positiveMention = positiveMention;
        this.negativeMention = negativeMention;
        this.score = score;
    }

    public String getAtTime() {
        return atTime;
    }

    public void setAtTime(String atTime) {
        this.atTime = atTime;
    }

    public Long getMention() {
        return mention;
    }

    public void setMention(Long mention) {
        this.mention = mention;
    }

    public Double getPositiveScore() {
        return positiveScore;
    }

    public void setPositiveScore(Double positiveScore) {
        this.positiveScore = positiveScore;
    }

    public Double getNegativeScore() {
        return negativeScore;
    }

    public void setNegativeScore(Double negativeScore) {
        this.negativeScore = negativeScore;
    }

    public Long getPositiveMention() {
        return positiveMention;
    }

    public void setPositiveMention(Long positiveMention) {
        this.positiveMention = positiveMention;
    }

    public Long getNegativeMention() {
        return negativeMention;
    }

    public void setNegativeMention(Long negativeMention) {
        this.negativeMention = negativeMention;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
