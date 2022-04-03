package com.miiz;

public class ToDoList {

    private String lineContent;
    private String listName;
    private Long id;
    private int listNumber;
    private int lineNumber;

    public ToDoList(String listLine, String listName, Long id, int listNumber, int lineNumber) {
        this.lineContent = listLine;
        this.id = id;
        this.listNumber = listNumber;
        this.lineNumber = lineNumber;
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getLineContent() {
        return lineContent;
    }

    public void setLineContent(String lineContent) {
        this.lineContent = lineContent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getListNumber() {
        return listNumber;
    }

    public void setListNumber(int listNumber) {
        this.listNumber = listNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return "ToDoList{" +
                "listLine='" + lineContent + '\'' +
                ", id=" + id +
                ", listNumber=" + listNumber +
                ", lineNumber=" + lineNumber +
                '}';
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
