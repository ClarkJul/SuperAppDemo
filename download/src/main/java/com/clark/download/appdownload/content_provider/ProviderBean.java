package com.clark.download.appdownload.content_provider;

public class ProviderBean {
    public int id;
    public String name;
    public long size;

    @Override
    public String toString() {
        return "ProviderBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size=" + size +
                '}';
    }
}
