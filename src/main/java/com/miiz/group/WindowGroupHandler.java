package com.miiz.group;

import com.miiz.auth.User;
import com.miiz.database.Database;
import com.miiz.utils.exceptions.InvalidChoiceException;
import com.miiz.utils.exceptions.InvalidInputException;


import java.util.List;
import java.util.Scanner;

import static com.miiz.utils.Utils.divider;
import static com.miiz.utils.Utils.tryParse;

/**
 * WindowGroup user interface class
 * This class handles all user-backend communication for the WindowGroup module
 */
public class WindowGroupHandler {
    private final Database database;
    private final List<WindowGroup> groupList;

    /**
     * constructor
     * @param database - database class to make queries
     */
    public WindowGroupHandler(Database database) {
        this.database = database;
        // initialise the WindowGroup list
        this.groupList = database.getWindowGroups();
    }

    public List<WindowGroup> getLists() {
        return groupList;
    }


    /**
     * Creates a new WindowGroup
     */
    public void newGroup(String name) {
        WindowGroup group = new WindowGroup(name, database.getUser().getId());
        // add group to database, database returns WindowGroup with ID attached
        group = database.addWindowGroup(group);
        groupList.add(group);
    }


    /**
     * Opens a WindowGroups windows if the user selects a valid one
     * @throws UnsupportedOperationException if users PC does not support opening windows
     */
    public void openGroup(WindowGroup group) throws UnsupportedOperationException {
        // opens the group
        List<WindowURL> failedUrls = group.openGroup();
        // removing malformed URLs
        for (WindowURL url : failedUrls) {
            database.deleteWindowGroupUrl(url);
            group.removeUrl(url);
        }
    }

    /**
     * Deletes a WindowGroup if the user selects a valid one
     */
    public void deleteGroup(WindowGroup group) {
        // deleting the WindowURLs from db
        database.deleteWindowGroup(group);
        groupList.remove(group);
    }

    /**
     * Adds a new WindowURL to a WindowGroup
     */
    public void addGroupUrl(WindowGroup group, String url) {
        // adding URL
        WindowURL windowURL = new WindowURL(group.getId(), url);
        // get WindowURL with an ID to save
        windowURL = database.addWindowGroupUrl(windowURL);
        group.addUrl(windowURL);
    }



    /**
     * Deletes a WindowURL from a WindowGroup
     */
    public void deleteGroupUrl(WindowGroup group, WindowURL url) {
        // deleting url
        database.deleteWindowGroupUrl(url);
        group.removeUrl(url);
    }


}
