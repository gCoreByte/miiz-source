package com.miiz.todolist;


import com.miiz.database.Database;

import java.util.List;

public class ToDoListHandler {

    private final Database database;
    private final List<ToDoList> lists;

    public ToDoListHandler(Database database) {
        this.database = database;
        this.lists = database.getToDoLists();
    }

    /**
     * Create a new todolist
     * @param name - name of list
     */
    public void addList(String name) {
        ToDoList newList = new ToDoList(name, database.getUser().getId());
        newList = database.addTodoList(newList);
        lists.add(newList);
    }

    /**
     * Edit the name of a todolist
     * @param list - list to edit
     * @param name - new name
     */
    public void editList(ToDoList list, String name) {
        lists.remove(list);
        list.setListName(name);
        lists.add(list);
        database.editToDoList(list);
    }

    /**
     * Get all todolists
     * @return all todolists
     */
    public List<ToDoList> getLists() {
        return lists;
    }

    /**
     * Delete a todolist
     * @param list - list to delete
     */
    public void deleteList(ToDoList list) {
        database.deleteToDoList(list);
        lists.remove(list);
    }

    /**
     * Add a new line to a todolist
     * @param list - list to add line to
     * @param line - line contents
     */
    public void addLine(ToDoList list, String line) {
        ListLine newLine = new ListLine(line, list.getId());
        newLine = database.addListLine(newLine);
        list.addLine(newLine);
    }

    /**
     * Deletes a line from a todolist
     * @param list - list to delete from
     * @param line - line to delete
     */
    public void deleteLine(ToDoList list, ListLine line) {
        list.deleteLine(line);
        database.deleteToDoListLine(line);
    }


    /**
     * Edits the contents of a line
     * @param list - list the line belongs to
     * @param line - line to edit
     * @param newvalue - new contents of the line
     */
    public void editLine(ToDoList list, ListLine line, String newvalue) {
        list.deleteLine(line);
        line.setContent(newvalue);
        database.editToDoListLine(line);
        list.addLine(line);
    }
}
