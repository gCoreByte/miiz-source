package com.miiz.todolist;

import com.miiz.auth.User;
import com.miiz.database.Database;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Scanner;

import static com.miiz.utils.Utils.tryParse;

public class ToDoListHandler {

    private final Database database;
    private final Scanner scan;
    private final User user;
    private final List<ToDoList> lists;

    public ToDoListHandler(Database database, User user, Scanner scan) {
        this.database = database;
        this.user = user;
        this.scan = scan;
        this.lists = database.getToDoLists();
    }

    public void main() {

        while (true) {
            // TODO: show all todolist names
            System.out.println("1 - Loo uus To Do nimekiri");
            System.out.println("2 - Muuda To Do nimekirja");
            System.out.println("3 - Kustuta To Do nimekiri");
            System.out.println("4 - Tagasi algusesse");
            String user_input = scan.nextLine().strip();

            switch (user_input) {
                case "1" -> newToDoList();
                case "2" -> editToDoListCall();
                case "3" -> {
                    System.out.println("Valisid To Do nimekirja kustutamise");
                    System.out.println();
                    int listIndex = pickToDoList(lists, "Vali nimekiri, mille soovid kustutada") - 1;

                    if (listIndex == -1) {
                        System.out.println("Vigane sisend");
                    } else {
                        database.deleteToDoList(lists.get(listIndex));
                    }
                }
                case "4" -> {
                    return;
                }
                default -> {
                    System.out.println("Vigane sisend!");
                    System.out.println("Sisesta valik uuesti");
                }
            }
        }
    }

    private void newToDoList() {
        System.out.println("Sisesta uue nimekirja pealkiri:");
        String user_input = scan.nextLine().strip();
        if (user_input.length() > 255) {
            System.out.println("Maksimaalne pikkus on 255 karakterit.");
            return;
        }
        ToDoList newList = new ToDoList(user_input, user.getId());
        newList = database.addTodoList(newList);
        lists.add(newList);
        System.out.println("Lisatud!");
    }

    private void editToDoListCall() {
        System.out.println("Millist nimekirja soovite muuta?");
        String user_input = scan.nextLine().strip();
        if (!tryParse(user_input)) {
            System.out.println("Vigane sisend.");
            return;
        }
        int input = Integer.parseInt(user_input) - 1;
        if (input < 0 || input >= lists.size()) {
            System.out.println("Vigane sisend.");
            return;
        }
        ToDoList change = lists.get(input);
        editToDoList(change);
    }

    public int scanInputInt(String task){
        System.out.println(task);

        String input = scan.nextLine();

        if (!tryParse(input)) {
            // vigane sisend
        }

    }

    public String scanInputString(String task){
        System.out.println(task);
        String line = scan.nextLine();
        if (line.length() > 255)
            return "false input";
        else
            return line;
    }

    public int pickToDoList(List<ToDoList> lists, String task){
        for (int i = 0; i < lists.size(); i++) {
            System.out.println(i + 1 + ". " + lists.get(i).getListName());
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
