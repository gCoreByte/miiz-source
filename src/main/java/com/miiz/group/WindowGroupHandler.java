package com.miiz.group;

import com.miiz.auth.User;
import com.miiz.database.Database;


import java.util.List;
import java.util.Scanner;

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
            System.out.println("See ei ole valiidne valik.");
            return -1;
        }
        int choice = Integer.parseInt(input) - 1;
        if (choice >= list.size() || choice < 0) {
            System.out.println("See ei ole valiidne valik.");
            return -1;
        }
        return choice;
    }

    /**
     * Creates a new WindowGroup if the user input is valid
     */
    private void newGroup() {
        System.out.println("Sisesta uue grupi nimi:");
        String input = inputReader.nextLine().strip();
        if (input.length() > 255) {
            System.out.println("Grupi nime maksimaalne pikkus on 255 karakterit.\nGruppi ei ole lisatud.");
        }
        if (input.length() > 0)  {
            WindowGroup group = new WindowGroup(input, user.getId());
            // add group to database, database returns WindowGroup with ID attached
            group = database.addWindowGroup(group);
            groupList.add(group);
            System.out.println("Lisatud!");
        } else {
            System.out.println("See ei ole sobiv nimi.\nGruppi ei ole lisatud.");

        }

    }

    /**
     * Opens a WindowGroups windows if the user selects a valid one
     */
    private void openGroup() {
        System.out.println("Vali millist gruppi avada:");
        for (int i = 0; i < groupList.size(); i++) {
            System.out.println(i + 1 + ". " + groupList.get(i).getName());
        }
        // get user input
        String input = inputReader.nextLine().strip();
        int choice = choiceChecker(input, groupList);
        if (choice == -1) { return; }
        // get the group and show the user the contained links
        WindowGroup group = groupList.get(choice);
        System.out.println("Grupis on järgnevad lingid:");
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
    private void deleteGroup() {
        System.out.println("Millist gruppi soovite kustutada?");
        // get user input
        String input = inputReader.nextLine().strip();
        int choice = choiceChecker(input, groupList);
        if (choice == -1) { return; }
        // get the group and ask for confirmation
        WindowGroup group = groupList.get(choice);
        System.out.println("Kustutatav grupp: " + group.getName());
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
        }
    }

    /**
     * Adds a new WindowURL to a WindowGroup
     */
    private void addGroupUrl() {
        System.out.println("Millisele grupile soovite URLi lisada?");
        // user input
        String input = inputReader.nextLine().strip();
        int choice = choiceChecker(input, groupList);
        if (choice == -1) { return; }
        // getting URL to add
        WindowGroup group = groupList.get(choice);
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
    private void removeGroupUrl() {
        System.out.println("Millisest grupist soovite URLi eemaldada?");
        // user input
        String input = inputReader.nextLine().strip();
        int choice = choiceChecker(input, groupList);
        if (choice == -1) { return; }
        // show windowgroup urls, get users choice
        WindowGroup group = groupList.get(choice);
        for (int i = 0; i < group.getUrls().size(); i++) {
            System.out.println(i+1 + ". " + group.getUrls().get(i));
        }
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
        }
    }

    /**
     * Helper method to print out all groups
     */
    private void printAllGroups() {
        for (int i = 0; i < groupList.size(); i++) {
            System.out.println(i+1 + ". " + groupList.get(i));
            System.out.println("------------");
        }
    }

    /**
     * Main method of WindowGroupHandler
     */
    public void main() {

        // main input loop
        while (true) {
            printAllGroups();
            System.out.println("1 - uus grupp");
            System.out.println("2 - ava grupp");
            System.out.println("3 - kustuta grupp");
            System.out.println("4 - lisa url grupis");
            System.out.println("5 - kustuta url grupis");
            System.out.println("6 - tagasi");
            String input = inputReader.nextLine().strip();
            switch (input) {

                case "1" -> newGroup();
                case "2" -> openGroup();
                case "3" -> deleteGroup();
                case "4" -> addGroupUrl();
                case "5" -> removeGroupUrl();
                case "6" -> {
                    // exit out of module
                    return;
                }
                // user input is invalid
                default -> System.out.println("See ei ole valiidne sisend.");
            }
        }
    }
}
