package com.miiz.todolist;

public class ListLine {
    private long id;
    private String content;
    private long parent_id;

    public ListLine(String content) {
        this.content = content;
    }

    public ListLine(long id, String content, long parent_id) {
        this.id = id;
        this.content = content;
        this.parent_id = parent_id;
    }

    public ListLine(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public long getParent_id() {
        return parent_id;
    }
}
