package com.miiz.database;

import com.miiz.todolist.ListLine;
import com.miiz.todolist.ToDoList;
import com.miiz.group.WindowGroup;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database extends DatabaseInit {

    public Database() {
        super();
    }

    public List<ToDoList> getToDoLists() {
        isValid();
        String sql1 = "SELECT * FROM ToDoList";
        String sql2 = "SELECT * FROM ListLine WHERE parentid = ?";
        List<ToDoList> toDoLists = new ArrayList<>();

        try (Statement statement = createStatement();
             ResultSet rs = statement.executeQuery(sql1)) {
            while (rs.next()) {
                ToDoList toDoList = new ToDoList(rs.getString("title"), rs.getLong("id"));
                toDoLists.add(toDoList);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong.");
            return new ArrayList<>();
        }
        for (ToDoList todolist : toDoLists) {
            try (PreparedStatement statement = createPrepStatement(sql2)) {
                statement.setLong(1, todolist.getId());
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    ListLine line = new ListLine(rs.getLong("id"), rs.getString("content"), todolist.getId());
                    todolist.addListLineInit(line);
                }
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }
        return toDoLists;
    }

    public void deleteToDoList(ToDoList toDoList) {
        isValid();
        String sql = "DELETE FROM ToDoList WHERE id = ?";
        try (PreparedStatement statement = createPrepStatement(sql)) {
            statement.setLong(1, toDoList.getId());
            statement.executeQuery();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

    public void editToDoList(ToDoList toDoList) {
        isValid();
        String sql = "UPDATE ToDoList SET title = ? WHERE id = ?";
        try (PreparedStatement statement = createPrepStatement(sql)){
            statement.setString(1, toDoList.getListName());
            statement.setLong(2, toDoList.getId());
            statement.executeQuery();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

    public void editToDoListLine(ListLine line) {
        isValid();
        String sql = "UPDATE ToDoList SET content = ? WHERE id = ?";
        try (PreparedStatement statement = createPrepStatement(sql)) {
            statement.setString(1, line.getContent());
            statement.setLong(2, line.getId());
            statement.executeQuery();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

    public void deleteToDoListLine(ListLine line) {
        isValid();
        String sql = "DELETE FROM ListLine WHERE id = ?";
        try (PreparedStatement statement = createPrepStatement(sql)) {
            statement.setLong(1, line.getId());
            statement.executeQuery();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

    public List<WindowGroup> getWindowGroups() {
        isValid();
        // TODO
        return new ArrayList<>();
    }

    public void addWindowGroup(WindowGroup group) {
        super.isValid();
        // TODO
    }

    public void editWindowGroup(WindowGroup group) {
        super.isValid();
        // TODO
    }

    public void deleteWindowGroup(WindowGroup group) {
        super.isValid();
        // TODO
    }

    public void addWindowGroupUrl(WindowGroup group, String url) {
        super.isValid();
        // TODO
    }

    public void editWindowGroupUrl(WindowGroup group, String url) {
        super.isValid();
        // TODO
    }

    public void removeWindowGroupUrl(WindowGroup group, String url) {
        super.isValid();
        // TODO
    }
    // used later when auth is implemented
/*
    public User getUser() {
        super.isValid();
        // TODO
    }

    public void removeUser() {
        super.isValid();
        // TODO
    }

 */
}
