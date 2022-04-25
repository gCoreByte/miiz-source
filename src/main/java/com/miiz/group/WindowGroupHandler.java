package com.miiz.group;

import com.miiz.auth.User;
import com.miiz.database.Database;


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
    private final User user;
    private final List<WindowGroup> groupList;
    private final Scanner inputReader;

    /**
     * constructor
     * @param database - database class to make queries
     * @param user - current logged-in user, this will be deprecated soon and all user-requiring functionality will be moved to Database.java
     * @param inputReader - a shared input scanner to avoid weird bugs
     */
    public WindowGroupHandler(Database database, User user, Scanner inputReader) {
        this.database = database;
        this.user = user;
        // initialise the WindowGroup list
        this.groupList = database.getWindowGroups();
        this.inputReader = inputReader;
    }

    /**
     * Checks if the choice is a valid nr and not too big/small
     * @param input - the users input
     * @param list - the list we are checking the size against
     * @return the integer if it was a valid choice, -1 otherwise.
     */
    private int choiceChecker(String input, List<?> list) {
        if (!tryParse(input)) {
            System.out.println("Vigane sisend.");
            return -1;
        }
        int choice = Integer.parseInt(input) - 1;
        if (choice >= list.size() || choice < 0) {
            System.out.println("Vigane sisend.");
            return -1;
        }
        return choice;
    }

    /**
     * Creates a new WindowGroup if the user input is valid
     */
    protected boolean newGroup() {
        System.out.println("Sisesta uue töölaua nimi:");
        String input = inputReader.nextLine().strip();
        if (input.length() > 255) {
            System.out.println("Töölaua nime maksimaalne pikkus on 255 karakterit.\nTöölauda ei ole lisatud.");
            return false;
        }
        if (input.length() > 0)  {
            WindowGroup group = new WindowGroup(input, user.getId());
            // add group to database, database returns WindowGroup with ID attached
            group = database.addWindowGroup(group);
            groupList.add(group);
            System.out.println("Lisatud!");
        } else {
            System.out.println("See ei ole sobiv nimi.\nTöölauda ei ole lisatud.");
        }
        divider();
        return true;

    }

    /**
     * Opens a WindowGroups windows if the user selects a valid one
     */
    protected void openGroup() {
        printAllGroupsName();
        System.out.println("Vali millist töölauda avada:");
        // get user input
        String input = inputReader.nextLine().strip();
        int choice = choiceChecker(input, groupList);
        if (choice == -1) { return; }
        // get the group and show the user the contained links
        WindowGroup group = groupList.get(choice);
        if (group.getUrls().size() == 0) {
            System.out.println("Töölauas ei ole ühtegi linki, mida avada.");
            return;
        }
        System.out.println("Töölauas on järgnevad lingid:");
        for (WindowURL url : group.getUrls()) {
            System.out.println(url.getUrl());
        }
        System.out.println("Avan? Y/N");
        input = inputReader.nextLine().strip().toUpperCase();
        if (input.equals("Y")) {
            // opens the group
            List<WindowURL> failedUrls = group.openGroup();
            // removing malformed URLs
            for (WindowURL url : failedUrls) {
                database.deleteWindowGroupUrl(url);
                group.removeUrl(url);
            }
        }
    }

    /**
     * Deletes a WindowGroup if the user selects a valid one
     */
    protected void deleteGroup() {
        printAllGroupsName();
        System.out.println("Millist tööauda soovite kustutada?");
        // get user input
        String input = inputReader.nextLine().strip();
        int choice = choiceChecker(input, groupList);
        if (choice == -1) { return; }
        // get the group and ask for confirmation
        WindowGroup group = groupList.get(choice);
        System.out.println("Kustutatav töölaud: " + group.getName());
        System.out.println("Oled kindel? Y/N");
        input = inputReader.nextLine().toUpperCase().strip();
        if (input.equals("Y")) {
            // deleting the WindowURLs from db
            // this shouldnt be needed - cascading delete since they have a foreign key referencing the windowgroup
            //for (WindowURL url : group.getUrls()) {
            //    database.deleteWindowGroupUrl(url);
            //}
            // deleting the window group
            database.deleteWindowGroup(group);
            groupList.remove(group);
            System.out.println("Kustutatud!");
            divider();
        }
    }

    /**
     * Adds a new WindowURL to a WindowGroup
     */
    protected void addGroupUrl() {
        if (groupList.size() == 0) {
            System.out.println("Ühtegi töölauda ei ole.");
            return;
        }
        printAllGroupsName();
        System.out.println("Millisele töölauale soovite URLi lisada?");
        // user input
        String input = inputReader.nextLine().strip();
        int choice = choiceChecker(input, groupList);
        if (choice == -1) { return; }
        // getting URL to add
        WindowGroup group = groupList.get(choice);
        divider();
        System.out.println("Mis on lisatav URL?");
        input = inputReader.nextLine().strip();
        // adding URL
        WindowURL windowURL = new WindowURL(group.getId(), input);
        // get WindowURL with an ID to save
        windowURL = database.addWindowGroupUrl(windowURL);
        group.addUrl(windowURL);
        System.out.println("Lisatud!");
    }

    /**
     * Deletes a WindowURL from a WindowGroup
     */
    protected void removeGroupUrl() {
        if (groupList.size() == 0) {
            System.out.println("Ühtegi töölauda ei ole.");
            return;
        }
        printAllGroupsName();
        System.out.println("Millisest töölauast soovite URLi eemaldada?");
        // user input
        String input = inputReader.nextLine().strip();
        int choice = choiceChecker(input, groupList);
        if (choice == -1) { return; }
        // show windowgroup urls, get users choice
        WindowGroup group = groupList.get(choice);
        for (int i = 0; i < group.getUrls().size(); i++) {
            System.out.println(i+1 + ". " + group.getUrls().get(i));
        }
        divider();
        System.out.println("Millist URLi soovite kustutada?");
        input = inputReader.nextLine().strip();
        choice = choiceChecker(input, group.getUrls());
        if (choice == -1) { return; }
        // confirmation
        WindowURL url = group.getUrls().get(choice);
        System.out.println("Kustutatav URL: " + url);
        System.out.println("Olete kindel? Y/N");
        input = inputReader.nextLine().strip().toUpperCase();
        if (input.equals("Y")) {
            // deleting url
            database.deleteWindowGroupUrl(url);
            group.removeUrl(url);
            System.out.println("Kustutatud!");
            divider();
        }
    }

    /**
     * Helper method to print out all groups
     */
    // WILL LIKELY FIND USAGE IN GUI VERSION
    private void printAllGroups() {
        for (int i = 0; i < groupList.size(); i++) {
            System.out.println(i+1 + ". " + groupList.get(i));
            divider();
        }
    }

    /**
     * Helper method to print out only group names
     */
    private void printAllGroupsName() {
        for (int i = 0; i < groupList.size(); i++) {
            System.out.println(i + 1 + ". " + groupList.get(i).getName());
        }
        divider();
    }

    /**
     * Main method of WindowGroupHandler
     */
    public void main() {

        // main input loop
        while (true) {
            divider();
            printAllGroupsName();
            System.out.println("0 - Tagasi");
            System.out.println("1 - Uus töölaud");
            System.out.println("2 - Ava töölaud");
            System.out.println("3 - Kustuta töölaud");
            System.out.println("4 - Lisa URL töölauas");
            System.out.println("5 - Kustuta URL töölauas");
            String input = inputReader.nextLine().strip();
            divider();
            switch (input) {

                case "1" -> newGroup();
                case "2" -> openGroup();
                case "3" -> deleteGroup();
                case "4" -> addGroupUrl();
                case "5" -> removeGroupUrl();
                case "0" -> {
                    // exit out of module
                    return;
                }
                // user input is invalid
                default -> {
                    System.out.println("Vigane sisend.");
                    divider();
                }
            }
        }
    }
}
