package com.miiz.group;

public class WindowURL {
    private long id;
    private long ownerid;
    private String url;

    public WindowURL(long ownerid, String url) {
        this.ownerid = ownerid;
        this.url = url;
    }

    public WindowURL(long id, long ownerid, String url) {
        this.id = id;
        this.ownerid = ownerid;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(long ownerid) {
        this.ownerid = ownerid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
