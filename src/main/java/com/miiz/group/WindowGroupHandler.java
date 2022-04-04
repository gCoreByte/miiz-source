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

    // main method
    public static void main() {
        Scanner inputReader = new Scanner(System.in);

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
