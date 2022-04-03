package com.miiz.group;

import java.util.ArrayList;
import java.util.List;

/**
 * WindowGroup data class
 */
public class WindowGroup {
    private String name;
    private final List<String> urls;

    public WindowGroup(String name) {
        this.name = name;
        urls = new ArrayList<>();
    }

    // constructor for entries gotten from db
    public WindowGroup(String name, List<String> urls) {
        this.name = name;
        this.urls = urls;
    }

    public void openGroup() {
        // TODO
    }

    public void addUrl(String url) {
        urls.add(url);
    }

    public void removeUrl(String url) {
        urls.remove(url);
    }

    // getters/setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUrls() {
        return urls;
    }

    @Override
    public String toString() {
        return name + "\n" +
                urls.toString();
    }
}
