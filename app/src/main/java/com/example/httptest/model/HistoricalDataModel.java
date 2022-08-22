package com.example.httptest.model;

import java.util.List;

public class HistoricalDataModel {
    private String s;
    private List<Double> c;
    private List<Double> h;
    private List<Double> l;
    private List<Double> o;
    private List<Long> t;
    private List<Long> v;

    public HistoricalDataModel(String s, List<Double> c, List<Double> h, List<Double> l, List<Double> o, List<Long> t, List<Long> v) {
        this.s = s;
        this.c = c;
        this.h = h;
        this.l = l;
        this.o = o;
        this.t = t;
        this.v = v;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public List<Double> getC() {
        return c;
    }

    public void setC(List<Double> c) {
        this.c = c;
    }

    public List<Double> getH() {
        return h;
    }

    public void setH(List<Double> h) {
        this.h = h;
    }

    public List<Double> getL() {
        return l;
    }

    public void setL(List<Double> l) {
        this.l = l;
    }

    public List<Double> getO() {
        return o;
    }

    public void setO(List<Double> o) {
        this.o = o;
    }

    public List<Long> getT() {
        return t;
    }

    public void setT(List<Long> t) {
        this.t = t;
    }

    public List<Long> getV() {
        return v;
    }

    public void setV(List<Long> v) {
        this.v = v;
    }
}
