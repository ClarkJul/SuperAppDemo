package com.android.clark.superappdemo.main.bean;

public class LeftBean {
    private String data;
    private boolean select;

    public LeftBean(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
