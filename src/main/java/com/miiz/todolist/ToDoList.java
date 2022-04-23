package com.miiz.todolist;


import java.util.ArrayList;
import java.util.List;

public class ToDoList {

    private List<ListLine> listLines;
    private String listName;
    private long id;
    private long ownerid;

    public ToDoList(String listName, long ownerid) {
        this.listName = listName;
        this.ownerid = ownerid;
        this.listLines = new ArrayList<>();
    }

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

    public void setId(long id) {
        this.id = id;
    }

    public long getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(long ownerid) {
        this.ownerid = ownerid;
    }

    public void addLine(ListLine line) {
        listLines.add(line);
    }


    public void editLine(int lineNumber, String lineContent){
        listLines.get(lineNumber).setContent(lineContent);
    }

    public void deleteLine(int lineNumber){
        listLines.remove(lineNumber);
    }

    public void printListLines(){
        for (int i = 0; i < listLines.size(); i++) {
            System.out.println("  " + i + 1 + ". " + listLines.get(i).getContent());
        }
    }
}
