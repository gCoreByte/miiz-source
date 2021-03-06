package com.miiz.group;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * WindowGroup data class
 * It contains methods related to a single WindowGroup and provides ease-of-access from
 * database calls.
 */
public class WindowGroup implements WorkSpace{
    private long id;
    private final long ownerid;
    private String name;
    private final List<WindowURL> urls;

    public WindowGroup(String name, long ownerid) {
        this.name = name;
        this.urls = new ArrayList<>();
        this.ownerid = ownerid;
    }

    // constructor for entries gotten from db
    public WindowGroup(long id, String name, long ownerid) {
        this.id = id;
        this.name = name;
        this.urls = new ArrayList<>();
        this.ownerid = ownerid;
    }

    /**
     * Opens a group of windows
     * @return list of malformed URLs to be removed
     * @throws UnsupportedOperationException when PC does not support functionality.
     */
    public List<WindowURL> openGroup() throws UnsupportedOperationException {
        // https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
        if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            System.out.println("Teie arvuti  kahjuks ei toeta seda funktsionaalsust.");
            throw new UnsupportedOperationException("PC does not support functionality.");
        }
        List<WindowURL> failedUrls = new ArrayList<>();
        for (WindowURL url : urls) {
            try {
                Desktop.getDesktop().browse(new URI(url.getUrl()));
            } catch (URISyntaxException e) {
                System.out.println("URL " + url + " ei vasta nõuetele. See eemaldatakse nimekirjast.");
                failedUrls.add(url);
            } catch (Exception e) {
               e.printStackTrace();
            }
        }
        return failedUrls;
    }

    // getters, setters
    public void addUrl(WindowURL url) {
        urls.add(url);
    }

    public void removeUrl(WindowURL url) {
        urls.remove(url);
    }

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

    public long getId() {
        return id;
    }

    public long getOwnerid() {
        return ownerid;
    }

    @Override
    public String toString() {
        return name;
    }
}
