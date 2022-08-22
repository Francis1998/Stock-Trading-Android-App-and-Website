package com.example.httptest.model;

import java.util.List;

public class autoCompleteModel {
    private Long count;
    private List<autoCompleteItemModel> result;
    private String searchWord;

    public autoCompleteModel(Long count, List<autoCompleteItemModel> result, String searchWord) {
        this.count = count;
        this.result = result;
        this.searchWord = searchWord;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<autoCompleteItemModel> getResult() {
        return result;
    }

    public void setResult(List<autoCompleteItemModel> result) {
        this.result = result;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }
}
