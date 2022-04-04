package com.miiz.group;

import com.miiz.auth.User;
import com.miiz.database.Database;


import java.util.List;
import java.util.Scanner;

// group module handler
public class WindowGroupHandler {
    private final Database database;
    private final User user;
    private final List<WindowGroup> groupList;
    private final Scanner inputReader;

    public WindowGroupHandler(Database database, User user, Scanner inputReader) {
        this.database = database;
        this.user = user;
        this.groupList = database.getWindowGroups();
        this.inputReader = inputReader;
    }

    private boolean tryParse(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private int choiceChecker(String input) {
        if (!tryParse(input)) {
            System.out.println("See ei ole valiidne valik.");
            return -1;
        }
        int choice = Integer.parseInt(input) - 1;
        if (choice >= groupList.size() || choice < 0) {
            System.out.println("See ei ole valiidne valik.");
            return -1;
        }
        return choice;
    }

    private void newGroup(Scanner inputReader) {
        System.out.println("Sisesta uue grupi nimi:");
        String input = inputReader.nextLine().strip();
        if (input.length() != 0) {
            WindowGroup group = new WindowGroup(input);
            group = database.addWindowGroup(group);
            groupList.add(group);
            System.out.println("Lisatud!");
        } else {
            System.out.println("See ei ole sobiv nimi.\nGruppi ei ole lisatud.");
        }

    }

    private void openGroup(Scanner inputReader) {
        System.out.println("Vali millist gruppi avada:");
        for (int i = 0; i < groupList.size(); i++) {
            System.out.println(i + 1 + ". " + groupList.get(i).getName());
        }
        String input = inputReader.nextLine().strip();
        int choice = choiceChecker(input);
        if (choice == -1) { return; }
        // open the group
        WindowGroup group = groupList.get(choice);
        System.out.println("Grupis on järgnevad lingid:");
        for (WindowURL url : group.getUrls()) {
            System.out.println(url.getUrl());
        }
        System.out.println("Avan? Y/N");
        input = inputReader.nextLine().strip().toUpperCase();
        if (input.equals("Y")) {
            List<WindowURL> failedUrls = group.openGroup();
            for (WindowURL url : failedUrls) {
                database.deleteWindowGroupUrl(url);
                group.removeUrl(url);
            }
        }
    }

    private void deleteGroup(Scanner inputReader) {
        System.out.println("Millist gruppi soovite kustutada?");
        String input = inputReader.nextLine().strip();
        int choice = choiceChecker(input);
        if (choice == -1) { return; }
        WindowGroup group = groupList.get(choice);
        System.out.println("Kustutatav nimekiri: " + group.getName());
        System.out.println("Oled kindel? Y/N");
        input = inputReader.nextLine().toUpperCase().strip();
        if (input.equals("Y")) {
            for (WindowURL url : group.getUrls()) {
                database.deleteWindowGroupUrl(url);
            }
            database.deleteWindowGroup(group);
            groupList.remove(group);
            System.out.println("Kustutatud!");
        }
    }

    private void addGroupUrl(Scanner inputReader) {
        System.out.println("Millisele grupile soovite URLi lisada?");
        String input = inputReader.nextLine().strip();
        int choice = choiceChecker(input);
        if (choice == -1) { return; }
        WindowGroup group = groupList.get(choice);
        System.out.println("Mis on lisatav URL?");
        input = inputReader.nextLine().strip();

        WindowURL windowURL = new WindowURL(group.getId(), input);
        windowURL = database.addWindowGroupUrl(windowURL);
        group.addUrl(windowURL);
        System.out.println("Lisatud!");
    }

    // main method
    public void main() {

        // main loop
        while (true) {
            // TODO: print all groups
            System.out.println("1 - uus grupp");
            System.out.println("2 - ava grupp");
            System.out.println("3 - kustuta grupp");
            System.out.println("4 - lisa url grupis");
            System.out.println("5 - kustuta url grupis");
            System.out.println("6 - tagasi");
            String input = inputReader.nextLine().strip();
            switch (input) {
                // uus grupp
                case "1" -> {
                    newGroup(inputReader);
                }
                // ava grupp
                case "2" -> {
                    openGroup(inputReader);
                }
                case "3" -> {
                    // kustuta grupp
                    deleteGroup(inputReader);
                }
                case "4" -> {
                    // lisa url grupis
                    addGroupUrl(inputReader);
                }
                case "5" -> {
                    // todo
                }
                case "6" -> {
                    return;
                }
                default -> {
                    System.out.println("See ei ole valiidne sisend.");
                }
            }
        }
    }
}
