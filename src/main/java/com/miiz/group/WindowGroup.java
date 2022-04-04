package com.miiz.group;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
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

    public List<WindowURL> openGroup() {
        // TODO https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
        if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            System.out.println("Teie arvuti ei toeta seda funktsionaalsust kahjuks.");
            return new ArrayList<>();
        }
        List<WindowURL> failedUrls = new ArrayList<>();
        for (WindowURL url : urls) {
            try {
                Desktop.getDesktop().browse(new URI(url.getUrl()));
            } catch (URISyntaxException e) {
                System.out.println("URL " + url + " ei vasta nõuetele. See eemaldatakse nimekirjast.");
                failedUrls.add(url);
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }
        return failedUrls;
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
