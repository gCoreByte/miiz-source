package com.miiz.todolist;

import com.miiz.database.Database;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {

    private List<ListLine> listLines;
    private String listName;
    private Long id;


    public ToDoList(List<ListLine> listLines, String listName, Long id) {
        this.listLines = listLines;
        this.listName = listName;
        this.id = id;
    }

    public ToDoList(String listName, Long id) {
        this.listLines = new ArrayList<>();
        this.listName = listName;
        this.id = id;
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

    public void addLine(String lineContent, Long lineId, Long listId){
        // Lisan andmebaasi rea ja sealt saan ta ide
        ListLine newLine = new ListLine(lineId, lineContent, id);
        listLines.add(newLine);
    }

    public void editLine(int lineNumber, String lineContent){
        listLines.get(lineNumber).setContent(lineContent);
    }

    public void deleteLine(int lineNumber){
        listLines.remove(lineNumber-1);
    }


}
