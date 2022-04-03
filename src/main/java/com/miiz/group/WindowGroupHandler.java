package com.miiz.group;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

// group module handler
public class WindowGroupHandler {
    // TEMPORARY FILENAME
    private static final String filename = "windowgroup.txt";

    private void saveGroups(List<WindowGroup> groups) {

    }

    private static List<WindowGroup> getGroups() throws IOException {
        // TODO: database integration
        // we will currently save the groups in a text file as a temporary solution

        // TEMP: read from file

        List<WindowGroup> groups = new ArrayList<>();
        File file = new File(filename);
        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)){
            while (scanner.hasNext()) {
                String name = scanner.next();
                WindowGroup group = new WindowGroup(name);
                while (scanner.hasNext()) {
                    String line2 = scanner.next();
                    if (line2.equals(";")) {
                        break;
                    }
                    group.addUrl(line2);
                }
                groups.add(group);
            }
        }
        return groups;

    }

    // main method
    public static void main() throws IOException {
        Scanner inputReader = new Scanner(System.in);
        List<WindowGroup> groups = getGroups();
        // main loop
        while (true) {
            groups.forEach(System.out::println);
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
