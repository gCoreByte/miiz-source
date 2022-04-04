package com.miiz.todolist;

import com.miiz.auth.User;
import com.miiz.database.Database;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Scanner;

public class ToDoListHandler {

    private final Database database;
    private final Scanner scan;
    private final User user;

    public ToDoListHandler(Database database, User user, Scanner scan) {
        this.database = database;
        this.user = user;
        this.scan = scan;
    }

    public void main() {

        List<ToDoList> lists = database.getToDoLists();
        boolean invalid_input = true;

        while (invalid_input) {
            invalid_input = false;
            System.out.println("Palun vali tegevus:");
            System.out.println();
            System.out.println("1 - Loo uus To Do nimekiri");
            System.out.println("2 - Muuda To Do nimekirja");
            System.out.println("3 - Kustuta To Do nimekiri");
            System.out.println("4 - Tagasi algusesse");
            int user_input = scanInputInt("Sisesta tegevusele vastav number");

            switch (user_input) {
                case 1 -> {
                    System.out.println("Valisid To Do nimekirja loomise");
                    System.out.println();
                    String name = scanInputString("Sisesta uue nimekirja pealkiri");
                    while (name.contains("false input")) {
                        name = scanInputString("Sisesta lühem nimekirja pealkiri");
                    }
                    ToDoList newList = new ToDoList(name, user.getId());
                    newList = database.addTodoList(newList);
                    lists.add(newList);
                }
                case 2 -> {
                    System.out.println("Valisid To Do nimekirja muutmise");
                    System.out.println();
                    int pickedListIndex = pickToDoList(lists, "Vali nimekiri, mida soovid muuta") - 1;
                    if (pickedListIndex == -1) {
                        System.out.println("Vigane sisend");
                    } else {
                        ToDoList changeL = lists.get(pickedListIndex);
                        editToDoList(changeL);
                    }
                }
                case 3 -> {
                    System.out.println("Valisid To Do nimekirja kustutamise");
                    System.out.println();
                    int listIndex = pickToDoList(lists, "Vali nimekiri, mille soovid kustutada") - 1;

                    if (listIndex == -1) {
                        System.out.println("Vigane sisend");
                    } else {
                        database.deleteToDoList(lists.get(listIndex));
                    }
                }
                case 4 -> {
                    return;
                }
                default -> {
                    System.out.println("Vigane sisend!");
                    System.out.println("Sisesta valik uuesti");
                    invalid_input = true;
                }
            }
        }
    }

    public int scanInputInt(String task){
        System.out.println(task);

        String input = scan.nextLine();
        String[] parts = input.split(" ");
        String firstNr = parts[0];

        try {
            if (Integer.parseInt(firstNr) > 0)
                return Integer.parseInt(firstNr);
            else return -1;
        }
        catch(Exception e) {
            return -1;
        }

    }

    public String scanInputString(String task){
        System.out.println(task);
        return scan.nextLine();
    }

    public int pickToDoList(List<ToDoList> lists, String task){

        for (int i = 0; i < lists.size(); i++) {
            System.out.println(i + ". " + lists.get(i).getListName());
            lists.get(i).printListLines();
            System.out.println();
        }

        return scanInputInt(task);

    }

    public void editToDoList(ToDoList list){
        System.out.println(list.getListName());
        list.printListLines();

        System.out.println("Mida sooviksd teha?");
        System.out.println("1 - lisada rida");
        System.out.println("2 - kustutada rida");
        System.out.println("3 - muuda rida");
        System.out.println("4 - mine tagasi");
        System.out.println();

        int pick = scanInputInt("Vali tegevusele vastav number");

        while (true){
            switch (pick) {
                case 1 -> {
                    String newLineC = scanInputString("Sisesta lisatav rida");
                    while (newLineC.equals("false input")) {
                        newLineC = scanInputString("Sisesta lühem lisatav rida");
                    }
                    ListLine newLine = new ListLine(newLineC);
                    list.addListLineInit(newLine);
                    database.editToDoList(list);
                }
                case 2 -> {
                    int lineIndex = scanInputInt("Sisesta kustutava rea number") - 1;
                    while (lineIndex == -1) {
                        lineIndex = scanInputInt("Sisesta reanumber uuesti") - 1;
                    }

                    database.deleteToDoListLine(list.getListLines().get(lineIndex));
                    list.deleteLine(lineIndex);
                }
                case 3 -> {
                    int lineIndex = scanInputInt("Sisesta muudetava rea number") - 1;
                    while (lineIndex == -1) {
                        lineIndex = scanInputInt("Sisesta reanumber uuesti") - 1;
                    }
                    String newLine = scanInputString("Sisesta rea uus sisu");
                    list.getListLines().get(lineIndex).setContent(newLine);
                    database.editToDoListLine(list.getListLines().get(lineIndex));

                }
                case 4 -> {
                    return;
                }
                default -> {

                }
            }
        }
    }
}
