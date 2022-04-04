package com.miiz.group;

import java.util.ArrayList;
import java.util.List;

/**
 * WindowGroup data class
 */
public class WindowGroup {
    private long id;
    private String name;
    private final List<WindowURL> urls;

    public WindowGroup(String name) {
        this.name = name;
        this.urls = new ArrayList<>();
    }

    // constructor for entries gotten from db
    public WindowGroup(long id, String name) {
        this.id = id;
        this.name = name;
        this.urls = new ArrayList<>();
    }

    public void openGroup() {
        // TODO
    }

    public void addUrl(WindowURL url) {
        urls.add(url);
    }

    public void removeUrl(WindowURL url) {
        urls.remove(url);
    }

    // getters/setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WindowURL> getUrls() {
        return urls;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + "\n" +
                urls.toString();
    }
}
