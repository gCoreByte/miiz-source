package com.miiz.group;

/**
 * WindowURL data class
 */
public class WindowURL {
    private long id;
    private long ownerid;
    private String url;

    public WindowURL(long ownerid, String url) {
        this.ownerid = ownerid;
        this.url = urlFixer(url);
    }

    public WindowURL(long id, long ownerid, String url) {
        this.id = id;
        this.ownerid = ownerid;
        this.url = urlFixer(url);
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
        this.url = urlFixer(url);
    }

    /**
     * Fixes URLs that are in the format of google.com etc.
     * @param url - input url
     * @return fixed url
     */
    private String urlFixer(String url) {
        if (!url.startsWith("http://")) {
            return "http://" + url;
        }
        return url;
    }

    @Override
    public String toString() {
        return url;
    }
}
