package com.android.clark.superappdemo.download.downloadtwo;

import java.util.List;

public class AppInfo {
    public int id;
    public String pkg;
    public String icon;
    public String name;
    public String sdesc;
    public String fileurl;
    public int isfree;
    public int vercode;
    public String vername;
    public String size;
    public String updatetime;
    public long downloads;
    public int type;
    public int islocal;
    public String src;
    public List<Tags> tags;

    public int progress;
    public int currentState;

    static class Tags {
        public String name;
        public String color;
        public String bgcolor;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fileurl='" + fileurl + '\'' +
                ", progress=" + progress +
                ", currentState=" + currentState +
                '}';
    }
}
