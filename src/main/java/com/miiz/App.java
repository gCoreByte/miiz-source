package com.miiz;

import com.miiz.auth.UserAuth;
import com.miiz.database.Database;
import com.miiz.group.WindowGroupHandler;
import com.miiz.todolist.ToDoList;
import com.miiz.todolist.ToDoListHandler;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        // apparently there can be issues if multiple scanners read system.in
        // to bypass this we will use the same scanner in all modules
        Scanner scan = new Scanner(System.in);

        Database database = new Database();
        UserAuth userAuth = new UserAuth(database);
        // TEMPORARY
        userAuth.userLogin("a","b");

        // TODO: User authentication here

        WindowGroupHandler windowGroupHandler = new WindowGroupHandler(database, userAuth.getUser(), scan);
        ToDoListHandler toDoListHandler = new ToDoListHandler(database, userAuth.getUser(), scan);

        System.out.println("Sissejuhatav/ selgitav tekst");
        System.out.println();
        while (true){
            System.out.println("Palun vali tegevus!");
            System.out.println();
            System.out.println("Kuva To Do nimekiri:        1");
            System.out.println("Ava focus-mode:             2");
            System.out.println("Ava notepad:                3");
            System.out.println("Kuva workspaceide valik:    4");
            System.out.println("Sulge programm:             5");
            System.out.println("Sisesta tegevusele vastav number: ");
            String str_input = scan.nextLine();
            String[] arr_input = str_input.split(" ");
            String user_input = arr_input[0];

            switch (user_input) {
                case "1" -> toDoListHandler.main();
                // To Do list

                case "2" -> System.out.println("Valisid muusika");
                // Music

                case "3" -> System.out.println("Not implemented.");

                case "4" -> windowGroupHandler.main();
                //workspace

                case "5" -> System.exit(0);

                default -> {
                    System.out.println("Vigane sisend!");
                    System.out.println("Sisesta valik uuesti");
                }
            }
        }
    }
}
