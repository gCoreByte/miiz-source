package com.miiz.todolist;

import com.miiz.auth.User;
import com.miiz.database.Database;

import java.util.List;
import java.util.Scanner;

import static com.miiz.utils.Utils.*;

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
        separatorWOS();
        while (true) {

            if (lists.size() == 0)
                System.out.println("Sul pole ühtegi To Do Listi");

            else {
                System.out.println("Sinu To Do Listid: ");
                for (int i = 0; i < lists.size(); i++) {
                    System.out.println((i + 1) + ". " + lists.get(i).getListName());
                }
            }

            separatorWOS();

            System.out.println("1 - Loo To Do List.");
            System.out.println("2 - Muuda To Do Listi");
            System.out.println("3 - Kustuta To Do List");
            System.out.println("4 - Kuva To Do List");
            System.out.println("5 - Tagasi");

            String user_input = scan.nextLine().strip();

            separator();

            switch (user_input) {
                case "1" -> newToDoList();

                case "2" -> {
                    if (noLists())
                        return;

                    editToDoListCall();

                }
                case "3" -> {
                    if (noLists())
                        return;

                    int listIndex = pickToDoList("Vali To Do List, mille soovid kustutada.") - 1;

                    if (invalidReqIndex(listIndex))
                        return;

                    deleteList(listIndex);
                }
                case "4" ->{
                    if (noLists())
                        return;

                    printList();
                }
                case "5" -> {
                    return;
                }
                default -> {
                    System.out.println("Vigane sisend!");
                    System.out.println("Sisesta valik uuesti");
                }
            }
        }
    }

    private void editToDoList(ToDoList list) {
        while (true) {
            separatorWOS();

            System.out.println(list.getListName());
            if (list.getListLines().size() == 0){
                System.out.println("To Do List on tühi.");
            }

            else {
                for (int i = 0; i < list.getListLines().size(); i++) {
                    System.out.println("  " + (i+1) + ". " + list.getListLines().get(i).getContent());
                }
            }

            separatorWOS();

            System.out.println("Mida sooviksd teha?");
            System.out.println("1 - lisada rida");
            System.out.println("2 - kustutada rida");
            System.out.println("3 - muuda rida");
            System.out.println("4 - mine tagasi");

            int pick = scanInputInt("Vali tegevusele vastav number");

            separator();

            switch (pick) {
                case 1 -> {
                    String newLineC = scanInputString("Sisesta lisatav rida:");

                    if (newLineC.equals("false input")) {
                        System.out.println("Vigane sisend.");
                        separator();
                        return;
                    }

                    addLine(list, newLineC);
                }

                case 2 -> {
                    if (listIsEmpty(list))
                        return;

                    int lineIndex = scanInputInt("Sisesta kustutava rea number") - 1;

                    if (invalidReqIndexList(lineIndex, list))
                        return;

                    deleteLine(list, lineIndex);
                }

                case 3 -> {
                    if (listIsEmpty(list))
                        return;

                    int lineIndex = scanInputInt("Sisesta muudetava rea number") - 1;

                    if (invalidReqIndexList(lineIndex, list))
                        return;

                    String newLine = scanInputString("Sisesta rea uus sisu:");
                    if (newLine.equals("false input"))
                        return;

                    editLine(list, newLine, lineIndex);
                }

                case 4 -> {
                    return;
                }

                default -> {

                }
            }
        }
    }
    /*******************************************************************************************************************
    TO DO LISTS
     */

    private void newToDoList() {
        System.out.println("Sisesta uue To Do Listi pealkiri:");
        String user_input = scan.nextLine().strip();
        if (user_input.length() > 255) {
            System.out.println("Vigane sisend. Maksimaalne rea pikkus on 255 karakterit.");
            separator();
            return;
        }
        ToDoList newList = new ToDoList(user_input, user.getId());
        newList = database.addTodoList(newList);
        lists.add(newList);
        System.out.println("To Do List loodud.");
        separator();
    }

    private void editToDoListCall() {
        System.out.println("Millist To Do Listi soovite muuta?");
        for (int i = 0; i < lists.size(); i++) {
            System.out.println((i + 1) + ". " + lists.get(i).getListName());
        }
        String user_input = scan.nextLine().strip();
        if (!tryParse(user_input)) {
            System.out.println("Vigane sisend.");
            separator();
            return;
        }
        int input = Integer.parseInt(user_input) - 1;
        if (input < 0 || input >= lists.size()) {
            System.out.println("Vigane sisend.");
            separator();
            return;
        }
        ToDoList change = lists.get(input);
        editToDoList(change);
    }

    private void deleteList(int listIndex){
        database.deleteToDoList(lists.get(listIndex));
        lists.remove(lists.get(listIndex));
        System.out.println("To Do List on kustutatud.");
        separator();
    }

    private void printList(){
        int listIndex = pickToDoList("Vali To Do List, mida soovid kuvada.") - 1;
        if (invalidReqIndex(listIndex))
            return;
        System.out.println(lists.get(listIndex).getListName());

        if (!listIsEmpty(lists.get(listIndex))){
            lists.get(listIndex).printListLines();
            separator();
        }
    }

    /*******************************************************************************************************************
     TO DO LIST
     */

    private void addLine(ToDoList list, String newLineC){

        ListLine newLine = new ListLine(newLineC);
        newLine = database.addListLine(newLine);
        list.addLine(newLine);
        System.out.println("Rida lisatud!");
        separator();
    }

    private void deleteLine(ToDoList list, int lineIndex){

        database.deleteToDoListLine(list.getListLines().get(lineIndex));
        list.deleteLine(lineIndex);
        System.out.println("Rida kustutatud.");
        separator();
    }

    private void editLine(ToDoList list, String newLine, int lineIndex){

        list.getListLines().get(lineIndex).setContent(newLine);
        database.editToDoListLine(list.getListLines().get(lineIndex));
        System.out.println("Rida muudetud.");
        separator();

    }

    /*******************************************************************************************************************
    INPUT
     */

    private int scanInputInt(String task){
        System.out.println(task);

        String input = scan.nextLine();

        if (!tryParse(input)) {
            System.out.println("Vigane sisend.");
            separator();
            return -1;
        }

        return Integer.parseInt(input);
    }

    private String scanInputString(String task){
        System.out.println(task);
        String line = scan.nextLine();
        if (line.length() > 255)
            return "false input";
        else
            return line;
    }

    private boolean listIsEmpty(ToDoList list){
        if (list.getListLines().size() == 0){
            System.out.println("To Do List on tühi.");
            separator();
            return true;
        }
        return false;
    }

    private boolean invalidReqIndex(int index){
        if (index == -1) {
            System.out.println("Vigane sisend.");
            separator();
            return true;
        }
        return false;
    }

    private boolean invalidReqIndexList(int index, ToDoList list){
        if (index == -1 || index > list.getListLines().size()-1) {
            System.out.println("Vigane sisend.");
            separator();
            return true;
        }
        return false;
    }

    private boolean noLists(){
        if(lists.size() == 0){
            System.out.println("Sul pole ühtegi To Do Listi.");
            separator();
            return true;
        }
        return false;
    }

    private int pickToDoList(String task){
        for (int i = 0; i < lists.size(); i++) {
            System.out.println(i + 1 + ". " + lists.get(i).getListName());
            separatorWOS();
        }

        System.out.println(task);

        String input = scan.nextLine();

        if (!tryParse(input) || Integer.parseInt(input) > lists.size()) {
            System.out.println("Vigane sisend.");
            separator();
            return -1;
        }

        return Integer.parseInt(input);

    }
}