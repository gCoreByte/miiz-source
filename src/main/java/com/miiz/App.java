package com.miiz;

import com.miiz.auth.UserAuth;
import com.miiz.database.Database;
import com.miiz.group.WindowGroupHandler;
import com.miiz.song.Song;
import com.miiz.song.SongHandler;
import com.miiz.todolist.ToDoList;
import com.miiz.todolist.ToDoListHandler;

import java.util.Scanner;

public class App {


    public static void authentication(Scanner scanner, UserAuth userAuth) {
        while (true) {
            System.out.println("Programmi kasutamiseks on vajalik sisselogimine.");
            System.out.println("1 - Logi sisse");
            System.out.println("2 - Tee konto");
            String input = scanner.nextLine().strip();
            switch (input) {
                case "1" -> {
                    boolean result = login(scanner, userAuth);
                    if (result) {
                        System.out.println("Edukalt sisse logitud!");
                        System.out.println("-------------");
                        return;
                    }
                    System.out.println("Sisselogimine ebaõnnestus.");
                }
                case "2" -> {
                    boolean result = register(scanner, userAuth);
                    if (result) {
                        System.out.println("Kasutaja loodud! Logige palun sisse.");
                    }
                    else {
                        System.out.println("Kasutajanimi on juba olemas.");
                    }
                }
            }
            System.out.println("-------------");
        }
    }

    public static boolean login(Scanner scanner, UserAuth userAuth) {
        System.out.println("Sisestage kasutajanimi:");
        String username = scanner.nextLine().strip();
        System.out.println("Sisestage parool:");
        String password = scanner.nextLine();
        return userAuth.userLogin(username, password);
    }

    public static boolean register(Scanner scanner, UserAuth userAuth) {
        System.out.println("Sisestage kasutajanimi:");
        String username = scanner.nextLine().strip();
        System.out.println("Sisestage parool:");
        String password = scanner.nextLine();
        return userAuth.userRegister(username, password);
    }

    public static void main(String[] args) {
        // apparently there can be issues if multiple scanners read system.in
        // to bypass this we will use the same scanner in all modules
        Scanner scan = new Scanner(System.in);

        Database database = new Database();
        UserAuth userAuth = new UserAuth(database);
        // TEMPORARY
        //userAuth.userLogin("a","b");

        // TODO: Move user authentication interface to UserAuthHandler?
        authentication(scan, userAuth);


        WindowGroupHandler windowGroupHandler = new WindowGroupHandler(database, userAuth.getUser(), scan);
        ToDoListHandler toDoListHandler = new ToDoListHandler(database, userAuth.getUser(), scan);
        SongHandler songHandler = new SongHandler(database, scan);

        System.out.println("Sissejuhatav/ selgitav tekst");
        System.out.println();
        while (true){
            System.out.println("Palun vali tegevus!");
            System.out.println();
            System.out.println("Kuva To Do nimekiri:        1");
            System.out.println("Ava muusika valik:          2");
            System.out.println("Kuva workspaceide valik:    3");
            System.out.println("Sulge programm:             4");
            System.out.println("Sisesta tegevusele vastav number: ");
            String str_input = scan.nextLine();
            String[] arr_input = str_input.split(" ");
            String user_input = arr_input[0];

            switch (user_input) {
                case "1" -> toDoListHandler.main();
                // To Do list

                case "2" -> songHandler.main();
                // Music

                case "3" -> windowGroupHandler.main();
                //workspace

                case "4" -> System.exit(0);

                default -> {
                    System.out.println("Vigane sisend!");
                    System.out.println("Sisesta valik uuesti");
                }
            }
        }
    }
}
