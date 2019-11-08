package com.android.clark.superappdemo.main.bean;

public class RightBean {
    private String data;
    private int Type;
    private boolean header;
    public int position;

    public RightBean(String data, int type) {
        this.data = data;
        Type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }
}
