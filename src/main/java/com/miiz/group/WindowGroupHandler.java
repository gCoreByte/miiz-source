package com.miiz.group;

import com.miiz.database.Database;


import java.util.Scanner;

// group module handler
public class WindowGroupHandler {
    private final Database database;
    private final User user;

    public WindowGroupHandler(Database database, User user) {
        this.database = database;
        this.user = user;
    }

    private void newGroup(String name) {
        WindowGroup group = new WindowGroup(name);

    }

    // main method
    public void main() {
        Scanner inputReader = new Scanner(System.in);

        // main loop
        while (true) {
            System.out.println("1 - uus grupp");
            System.out.println("2 - ava grupp");
            System.out.println("3 - kustuta grupp");
            System.out.println("4 - lisa url grupis");
            System.out.println("5 - kustuta url grupis");
            System.out.println("6 - tagasi");
            String input = inputReader.nextLine().strip();
            switch (input) {
                case "1":
                    // todo
                case "2":
                    // todo
                case "3":
                    // todo
                case "4":
                    // todo
                case "5":
                    // todo
                case "6":
                    return;
                default:
                    continue;
            }
        }
    }
}
