package com.miiz.todolist;

public class ListLine {
    private long id;
    private String content;
    private long parentid;

    public ListLine(String content) {
        this.content = content;
    }

    public ListLine(long id, String content, long parentid) {
        this.id = id;
        this.content = content;
        this.parentid = parentid;
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

    public void setId(long id) { this.id = id; }

    public long getParentid() {
        return parentid;
    }
}
