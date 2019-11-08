package com.android.clark.superappdemo.main.bean;

import java.util.List;

public class MainBean {

    /**
     * header : 四大组件
     * data : [{"child":"服务"},{"child":"广播"},{"child":"内容提供者"}]
     */

    private String header;
    private List<DataBean> data;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * child : 服务
         */

        private String child;

        public String getChild() {
            return child;
        }

        public void setChild(String child) {
            this.child = child;
        }
    }
}
