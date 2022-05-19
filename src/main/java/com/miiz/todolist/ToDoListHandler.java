package com.miiz.todolist;

import com.miiz.AppNew;
import com.miiz.database.Database;

import javax.xml.crypto.Data;
import java.util.List;

public class ToDoListHandler {

    private final Database database;
    private final List<ToDoList> lists;

    public ToDoListHandler(Database database) {
        this.database = database;
        this.lists = database.getToDoLists();
    }

    public void addList(String name) {
        ToDoList newList = new ToDoList(name, database.getUser().getId());
        newList = database.addTodoList(newList);
        lists.add(newList);
    }

    public void editList(ToDoList list, String name) {
        lists.remove(list);
        list.setListName(name);
        lists.add(list);
        database.editToDoList(list);
    }

    public List<ToDoList> getLists() {
        return lists;
    }

    public void deleteList(ToDoList list) {
        database.deleteToDoList(list);
        lists.remove(list);
    }

    public void addLine(ToDoList list, String line) {
        ListLine newLine = new ListLine(line, list.getId());
        newLine = database.addListLine(newLine);
        list.addLine(newLine);
    }

    public void deleteLine(ToDoList list, ListLine line) {
        list.deleteLine(line);
        database.deleteToDoListLine(line);
    }


    public void editLine(ToDoList list, ListLine line, String newvalue) {
        list.deleteLine(line);
        line.setContent(newvalue);
        database.editToDoListLine(line);
        list.addLine(line);
    }
}
