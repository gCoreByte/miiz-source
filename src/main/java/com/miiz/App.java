package com.miiz;

import com.miiz.auth.UserAuth;
import com.miiz.database.Database;
import com.miiz.group.WindowGroupHandler;
import com.miiz.todolist.ToDoList;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Database database = new Database();
        UserAuth userAuth = new UserAuth(database);

        // TODO: User authentication here


        WindowGroupHandler windowGroupHandler = new WindowGroupHandler(database, userAuth.getUser());

        System.out.println("Sissejuhatav/ selgitav tekst");
        System.out.println();
        boolean invalid_input = true;

        while (invalid_input){
            invalid_input = false;
            System.out.println("Palun vali tegevus!");
            System.out.println();
            System.out.println("Kuva To Do nimekiri:        1");
            System.out.println("Ava focus-mode:             2");
            System.out.println("Ava notepad:                3");
            System.out.println("Kuva workspaceide valik:    4");
            Scanner scan = new Scanner(System.in);
            System.out.println("Sisesta tegevusele vastav number: ");
            String str_input = scan.nextLine();
            String[] arr_input = str_input.split(" ");
            String user_input = arr_input[0];

            switch (user_input) {
                case "1" -> System.out.println("Valisid To Do nimekirja");

                // To Do list
                case "2" -> System.out.println("Valisid Focus Modei");

                // Focus
                case "3" -> System.out.println("Valisid notepadi");

                // notepad
                case "4" -> System.out.println("Valisid workspacei");

                //workspace
                default -> {
                    System.out.println("Vigane sisend!");
                    System.out.println("Sisesta valik uuesti");
                    invalid_input = true;
                }
            }
        }
    }
}
