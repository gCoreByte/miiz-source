package com.miiz.todolist;

import com.miiz.auth.User;
import com.miiz.database.Database;

import java.util.List;
import java.util.Scanner;

import static com.miiz.utils.Utils.*;

public class ToDoListHandlerOld {

    private final Database database;
    private final Scanner scan;
    private final User user;
    private final List<ToDoList> lists;

    public ToDoListHandlerOld(Database database, User user, Scanner scan) {
        this.database = database;
        this.user = user;
        this.scan = scan;
        this.lists = database.getToDoLists();
    }


    // Methods to manage To Do Lists
    /**
     * Create a new To Do List and add it to the database
     */
    private void newToDoList() {
        System.out.println("Sisestage uue To Do Listi pealkiri:");
        String user_input = scan.nextLine().strip();
        if (user_input.length() > 255) {
            System.out.println("Vigane sisend. Maksimaalne rea pikkus on 255 karakterit.");
            divider();
            return;
        }
        ToDoList newList = new ToDoList(user_input, user.getId());
        newList = database.addTodoList(newList);
        lists.add(newList);
        System.out.println("To Do List loodud.");
        divider();
    }

    /**
     * Select a To Do List to edit
     */
    private void editToDoListCall() {
        System.out.println("Millist To Do Listi soovite muuta?");
        for (int i = 0; i < lists.size(); i++) {
            System.out.println((i + 1) + ". " + lists.get(i).getListName());
        }
        String user_input = scan.nextLine().strip();
        if (!tryParse(user_input)) {
            System.out.println("Vigane sisend.");
            divider();
            return;
        }
        int input = Integer.parseInt(user_input) - 1;
        if (input < 0 || input >= lists.size()) {
            System.out.println("Vigane sisend.");
            divider();
            return;
        }
        ToDoList change = lists.get(input);
        editToDoList(change);
    }

    /**
     * Deletes the selected To Do List form the database and the list of To Do Lists
     * @param listIndex index of the selected To Do List
     */
    private void deleteList(int listIndex){
        database.deleteToDoList(lists.get(listIndex));
        lists.remove(lists.get(listIndex));
        System.out.println("To Do List on kustutatud.");
        divider();
    }

    /**
     * Prints the name of the selected To Do List and its contents
     */
    private void printList(){
        int listIndex = pickToDoList("Vali To Do List, mida soovite kuvada.") ;

        if (invalidReqIndex(listIndex))
            return;

        listIndex --;
        System.out.println(lists.get(listIndex).getListName());

        if (!listIsEmpty(lists.get(listIndex))){
            lists.get(listIndex).printListLines();
            divider();
        }
    }

    // Methods to manage a To Do List

    /**
     * Adds a new task to the end of the selected To Do List
     * @param list Selected To Do List
     * @param newLineC Inserted line
     */
    private void addLine(ToDoList list, String newLineC){

        ListLine newLine = new ListLine(newLineC, list.getId());
        newLine = database.addListLine(newLine);
        list.addLine(newLine);
        System.out.println("Rida lisatud.");
        divider();
    }

    /**
     * Deletes the selected line
     * @param list previously selected To Do List
     * @param lineIndex index of the selected line
     */
    private void deleteLine(ToDoList list, int lineIndex){

        database.deleteToDoListLine(list.getListLines().get(lineIndex));
        list.deleteLine(lineIndex);
        System.out.println("Rida kustutatud.");
        divider();
    }

    /**
     * Overwrites the selected line
     * @param list previosly selected To Do List
     * @param newLine inserted line
     * @param lineIndex index of the selected line
     */
    private void editLine(ToDoList list, String newLine, int lineIndex){

        list.getListLines().get(lineIndex).setContent(newLine);
        database.editToDoListLine(list.getListLines().get(lineIndex));
        System.out.println("Rida muudetud.");
        divider();

    }

    /**
     * Prints a To Do List which has atleast one element (must be checked before)
     * @param list Selected list
     */
    private void printNotEmptyList( ToDoList list){
        System.out.println(list.getListName());
        for (int i = 0; i < list.getListLines().size(); i++) {
            System.out.println("  " + (i+1) + ". " + list.getListLines().get(i).getContent());
        }
    }

    // Methods for getting and validating user input and other actions

    /**
     * Scans user input and returns it as an integer if possible
     * Mostly used to select lines or lists
     * @param task printed for users
     * @return valid user input or an indicator of invalid input
     */
    private int scanInputInt(String task){
        System.out.println(task);

        String input = scan.nextLine();

        if (!tryParse(input)) {
            System.out.println("Vigane sisend.");
            divider();
            return -1;
        }

        return Integer.parseInt(input);
    }

    /**
     * Scans user input and returns it if it is less than 255 characters (max for database)
     * @param task printed for users
     * @return valid user input or an indicator of invalid input
     */
    private String scanInputString(String task){
        System.out.println(task);
        String line = scan.nextLine();
        if (line.length() > 255)
            return "false input";
        else
            return line;
    }

    /**
     * Checks if the list is empty or not
     * @param list selected list
     * @return true if the list is empty, false if the list is not empty
     */
    private boolean listIsEmpty(ToDoList list){
        if (list.getListLines().size() == 0){
            System.out.println("To Do List on tühi.");
            divider();
            return true;
        }
        return false;
    }

    /**
     * Some methods return -1 as and indicator of an invalid input. This method checks it.
     * @param index index for user input
     * @return true, if the input is invalid, false, if valid
     */
    private boolean invalidReqIndex(int index){
        if (index == -1) {
            System.out.println("Vigane sisend.");
            return true;
        }
        return false;
    }

    /**
     * Some methods return -1 as and indicator of an invalid input.
     * input is invalid If -1 was previously returned or the index is out of bounds.
     * @param index previosly returned index from user input
     * @param list previously selected To Do List
     * @return true if the index is invalid, false if not
     */
    private boolean invalidReqIndexList(int index, ToDoList list){
        if (index == -1 || index > list.getListLines().size()-1) {
            System.out.println("Vigane sisend.");
            return true;
        }
        return false;
    }

    /**
     * Checks if the user has any To Do Lists or not
     * @return true, if there are no To Do Lists, false if there are
     */
    private boolean noLists(){
        return lists.size() == 0;
    }

    /**
     * Prints all the titles of the users To Do Lists
     * @param task for the user
     * @return the number of the picked List
     */
    private int pickToDoList(String task){
        for (int i = 0; i < lists.size(); i++) {
            System.out.println(i + 1 + ". " + lists.get(i).getListName());
            divider();
        }

        System.out.println(task);

        String input = scan.nextLine();

        if (!tryParse(input) || Integer.parseInt(input) > lists.size()) {
            System.out.println("Vigane sisend.");
            divider();
            return -1;
        }

        return Integer.parseInt(input);

    }

    /**
     * Section for managing individual To Do Lists
     * @param list selected list
     */
    private void editToDoList(ToDoList list) {
        while (true) {
            divider();

            System.out.println(list.getListName());
            if (list.getListLines().size() == 0){
                System.out.println("To Do List on tühi.");
            }

            else {
                for (int i = 0; i < list.getListLines().size(); i++) {
                    System.out.println("  " + (i+1) + ". " + list.getListLines().get(i).getContent());
                }
            }

            divider();

            System.out.println("Palun valige tegevus:");
            System.out.println("0 - Tagasi");
            System.out.println("1 - Lisada rida");
            System.out.println("2 - Kustutada rida");
            System.out.println("3 - Muuda rida");

            int pick = scanInputInt("Valige tegevusele vastav number");

            divider();

            switch (pick) {
                case 1 -> {
                    String newLineC = scanInputString("Sisestage lisatav rida:");

                    if (newLineC.equals("false input")) {
                        System.out.println("Vigane sisend.");
                        continue;
                    }

                    addLine(list, newLineC);
                }

                case 2 -> {
                    if (listIsEmpty(list))
                        return;

                    printNotEmptyList(list);

                    int lineIndex = scanInputInt("Sisestage kustutava rea number") - 1;

                    if (invalidReqIndexList(lineIndex, list))
                        continue;

                    deleteLine(list, lineIndex);
                }

                case 3 -> {
                    if (listIsEmpty(list))
                        return;

                    printNotEmptyList(list);

                    int lineIndex = scanInputInt("Sisestage muudetava rea number") - 1;

                    if (invalidReqIndexList(lineIndex, list))
                        continue;

                    String newLine = scanInputString("Sisestage rea uus sisu:");
                    if (newLine.equals("false input"))
                        continue;

                    editLine(list, newLine, lineIndex);
                }

                case 0 -> {
                    return;
                }

                default -> {

                }
            }
        }
    }

    public void main() {

        while (true) {
            if (lists.size() == 0)
                System.out.println("Teil ei ole ühtegi To Do Listi");

            else {
                System.out.println("Sinu To Do Listid: ");
                for (int i = 0; i < lists.size(); i++) {
                    System.out.println((i + 1) + ". " + lists.get(i).getListName());
                }
            }

            divider();
            System.out.println("0 - Tagasi");
            System.out.println("1 - Loo To Do List.");
            System.out.println("2 - Muuda To Do Listi");
            System.out.println("3 - Kustuta To Do List");
            System.out.println("4 - Kuva To Do List");

            String user_input = scan.nextLine().strip();

            divider();

            switch (user_input) {
                case "1" -> newToDoList();

                case "2" -> {
                    if (noLists())
                        continue;

                    editToDoListCall();

                }
                case "3" -> {
                    if (noLists())
                        continue;

                    int listIndex = pickToDoList("Valige To Do List, mida soovite kustutada.");

                    if (invalidReqIndex(listIndex))
                        continue;

                    listIndex--;
                    deleteList(listIndex);
                }
                case "4" ->{
                    if (noLists())
                        continue;

                    printList();
                }
                case "0" -> {
                    return;
                }
                default -> {
                    System.out.println("Vigane sisend.");
                    System.out.println("Sisestage valik uuesti.");
                    divider();
                }
            }
        }
    }
}