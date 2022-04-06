package com.miiz;

import com.miiz.auth.UserAuth;
import com.miiz.database.Database;
import com.miiz.group.WindowGroupHandler;
import com.miiz.song.Song;
import com.miiz.song.SongHandler;
import com.miiz.todolist.ToDoList;
import com.miiz.todolist.ToDoListHandler;
import java.util.Scanner;
import static com.miiz.utils.Utils.divider;

public class App {

    /**
     * User authentication -> get user input and handle choices
     * @param scanner - the shared scanner from main
     * @param userAuth - userAuth class to handle checking against the db
     */
    private static void authentication(Scanner scanner, UserAuth userAuth) {
        while (true) {
            System.out.println("Programmi kasutamiseks on vajalik sisselogimine.");
            System.out.println("1 - Logi sisse");
            System.out.println("2 - Tee konto");
            String input = scanner.nextLine().strip();
            switch (input) {
                case "1" -> {
                    // if login succeeds, we return out of auth, breaking the loop
                    boolean result = login(scanner, userAuth);
                    if (result) {
                        System.out.println("Edukalt sisse logitud!");
                        System.out.println("----------------------------------");
                        return;
                    }
                    System.out.println("Sisselogimine ebaõnnestus.");
                }
                case "2" -> {
                    // checking if registration was successful
                    boolean result = register(scanner, userAuth);
                    if (result) {
                        System.out.println("Kasutaja loodud! Logige palun sisse.");
                    }
                    else {
                        System.out.println("Kasutajanimi on juba olemas.");
                    }
                }
            }
            System.out.println("----------------------------------");
        }
    }

    /**
     * Connects user input to userAuth.login()
     * @param scanner - shared scanner from main
     * @param userAuth - userAuth class to handle checking against the db
     * @return true if login was successful, otherwise false
     */
    private static boolean login(Scanner scanner, UserAuth userAuth) {
        System.out.println("Sisestage kasutajanimi:");
        String username = scanner.nextLine().strip();
        System.out.println("Sisestage parool:");
        String password = scanner.nextLine();
        return userAuth.userLogin(username, password);
    }

    /**
     * Connects user input to userAuth.register()
     * @param scanner - shared scanner from main
     * @param userAuth - userAuth class to handle checking against the db
     * @return true if registration was successful, otherwise false
     */
    private static boolean register(Scanner scanner, UserAuth userAuth) {
        System.out.println("Sisestage kasutajanimi:");
        String username = scanner.nextLine().strip();
        System.out.println("Sisestage parool:");
        String password = scanner.nextLine();
        return userAuth.userRegister(username, password);
    }

    /**
     * Main method
     * @param args - cmdline args
     */
    public static void main(String[] args) {
        // apparently there can be issues if multiple scanners read system.in
        // to bypass this we will use the same scanner in all modules
        Scanner scan = new Scanner(System.in);

        Database database = new Database();
        UserAuth userAuth = new UserAuth(database);

        // TODO: Move user authentication interface to UserAuthHandler?
        authentication(scan, userAuth);

        // initialise our handlers
        WindowGroupHandler windowGroupHandler = new WindowGroupHandler(database, userAuth.getUser(), scan);
        ToDoListHandler toDoListHandler = new ToDoListHandler(database, userAuth.getUser(), scan);
        SongHandler songHandler = new SongHandler(database, scan);

        // TODO: change sissejuhatav/selgitav tekst
        System.out.println("Sissejuhatav/ selgitav tekst");
        divider();

        while (true){
            System.out.println("Palun vali tegevus:");
            System.out.println();
            System.out.println("0 - Sulge programm");
            System.out.println("1 - To Do List");
            System.out.println("2 - Muusika");
            System.out.println("3 - Töölaud");
            System.out.println("Sisesta tegevusele vastav number: ");
            String str_input = scan.nextLine();
            String[] arr_input = str_input.split(" ");
            String user_input = arr_input[0];

            switch (user_input) {
                case "1" -> toDoListHandler.main();

                case "2" -> songHandler.main();

                case "3" -> windowGroupHandler.main();

                case "0" -> System.exit(0);

                default -> System.out.println("Vigane sisend.");
            }
        }
    }
}
