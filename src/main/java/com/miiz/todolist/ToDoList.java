package com.miiz.todolist;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {

    private List<ListLine> listLines;
    private String listName;
    private long id;
    private long ownerid;


    public ToDoList(List<ListLine> listLines, String listName, long id, long ownerid) {
        this.listLines = listLines;
        this.listName = listName;
        this.id = id;
        this.ownerid = ownerid;
    }

    public ToDoList(String listName, long id, long ownerid) {
        this.listLines = new ArrayList<>();
        this.listName = listName;
        this.id = id;
        this.ownerid = ownerid;
    }

    public List<ListLine> getListLines() {
        return listLines;
    }

    public void setListLines(List<ListLine> listLines) {
        this.listLines = listLines;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public Long getId() {
        return id;
    }

    public void addListLineInit(ListLine line) {
        listLines.add(line);
    }

    public void newList(int listNumber, String listName){

    }

    public void deleteList(int listNumber){

    }

    public void addLine(int listNumber, int lineNumber, String lineContent){

    }

    public void editLine(int listNumber, int lineNumber, String lineContent){

    }

    public void deleteLine(int listNumber, int lineNumber){

    }


}