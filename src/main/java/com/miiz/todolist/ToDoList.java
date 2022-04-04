package com.miiz.todolist;

import com.miiz.database.Database;

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

    public void setId(long id) {
        this.id = id;
    }

    public void addListLineInit(ListLine line) {
        listLines.add(line);
    }

    public void addLine(String lineContent, Long lineId){
        ListLine newLine = new ListLine(lineId, lineContent, id);
        listLines.add(newLine);
    }

    public void editLine(int lineNumber, String lineContent){
        listLines.get(lineNumber).setContent(lineContent);
    }

    public void deleteLine(int lineNumber){
        listLines.remove(lineNumber-1);
    }

    public void printListLines(){
        for (int i = 1; i < listLines.size(); i++) {
            System.out.println("  " + i + ". " + listLines.get(i-1).getContent());
        }
    }
}
