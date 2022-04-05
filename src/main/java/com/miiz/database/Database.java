package com.miiz.database;

import com.miiz.auth.User;
import com.miiz.group.WindowURL;
import com.miiz.song.Genre;
import com.miiz.song.Song;
import com.miiz.todolist.ListLine;
import com.miiz.todolist.ToDoList;
import com.miiz.group.WindowGroup;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database extends DatabaseInit {

    private User user;

    public Database() {
        super();
    }

    // THIS WILL BE CALLED AFTER LOGGING IN
    public void setUser(User user) {
        this.user = user;
    }

    public List<ToDoList> getToDoLists() {
        isValid();
        String sql1 = "SELECT * FROM ToDoList WHERE ownerid = ?";
        String sql2 = "SELECT * FROM ListLine WHERE ownerid = ?";
        List<ToDoList> toDoLists = new ArrayList<>();

        try (PreparedStatement statement = createPrepStatement(sql1)) {
             statement.setLong(1, user.getId());
             ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ToDoList toDoList = new ToDoList(rs.getString("title"), rs.getLong("id"), rs.getLong("ownerid"));
                toDoLists.add(toDoList);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }
        return toDoLists;
    }

    public void deleteToDoList(ToDoList toDoList) {
        isValid();
        String sql = "DELETE FROM ToDoList WHERE id = ?";
        try (PreparedStatement statement = createPrepStatement(sql)) {
            statement.setLong(1, toDoList.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editToDoList(ToDoList toDoList) {
        isValid();
        String sql = "UPDATE ToDoList SET title = ? WHERE id = ?";
        try (PreparedStatement statement = createPrepStatement(sql)){
            statement.setString(1, toDoList.getListName());
            statement.setLong(2, toDoList.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ToDoList addTodoList(ToDoList toDoList) {
        isValid();
        String sql = "INSERT INTO ToDoList (title, ownerid) VALUES (?, ?)";
        try (PreparedStatement statement = createPrepStatement(sql)){
            statement.setString(1, toDoList.getListName());
            statement.setLong(2, user.getId());
            statement.executeUpdate();
            toDoList.setId(getLastRowId());
            return toDoList;
        } catch (Exception e) {
            e.printStackTrace();
            return toDoList;
        }
    }

    public void editToDoListLine(ListLine line) {
        isValid();
        String sql = "UPDATE ToDoList SET content = ? WHERE id = ?";
        try (PreparedStatement statement = createPrepStatement(sql)) {
            statement.setString(1, line.getContent());
            statement.setLong(2, line.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteToDoListLine(ListLine line) {
        isValid();
        String sql = "DELETE FROM ListLine WHERE id = ?";
        try (PreparedStatement statement = createPrepStatement(sql)) {
            statement.setLong(1, line.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<WindowGroup> getWindowGroups() {
        isValid();
        String sql1 = "SELECT * FROM WindowGroup WHERE ownerid = ?";
        String sql2 = "SELECT * FROM WindowGroupUrl WHERE ownerid = ?";
        List<WindowGroup> groups = new ArrayList<>();
        try (PreparedStatement statement = createPrepStatement(sql1)) {
            statement.setLong(1, user.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                WindowGroup group = new WindowGroup(rs.getLong("id"), rs.getString("name"), rs.getLong("ownerid"));
                try (PreparedStatement statement2 = createPrepStatement(sql2)) {
                    statement2.setLong(1, rs.getLong("id"));
                    ResultSet rs2 = statement2.executeQuery();
                    while (rs2.next()) {
                        WindowURL url = new WindowURL(rs2.getLong("id"), rs2.getLong("ownerid"), rs2.getString("url"));
                        group.addUrl(url);
                    }
                } catch (Exception e) {
                    System.out.println("Something went wrong getting group urls.");
                }
                groups.add(group);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groups;
    }

    public WindowGroup addWindowGroup(WindowGroup group) {
        isValid();
        String sql = "INSERT INTO WindowGroup (name, ownerid) VALUES (?, ?)";
        try (PreparedStatement statement = createPrepStatement(sql)) {
            statement.setString(1, group.getName());
            statement.setLong(2, group.getOwnerid());
            statement.executeUpdate();
            group.setId(getLastRowId());
            return group;
        } catch (Exception e) {
            e.printStackTrace();
            return group;
        }
    }

    public void editWindowGroup(WindowGroup group) {
        super.isValid();
        // TODO
    }

    public void deleteWindowGroup(WindowGroup group) {
        isValid();
        String sql = "DELETE FROM WindowGroup WHERE id = ?";
        try (PreparedStatement statement = createPrepStatement(sql)) {
            statement.setLong(1, group.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WindowURL addWindowGroupUrl(WindowURL windowURL) {
        isValid();
        String sql = "INSERT INTO WindowGroupUrl (ownerid, url) VALUES (?, ?)";
        try (PreparedStatement statement = createPrepStatement(sql)) {
            statement.setLong(1, windowURL.getOwnerid());
            statement.setString(2, windowURL.getUrl());
            statement.executeUpdate();
            windowURL.setId(getLastRowId());
            return windowURL;
        } catch (Exception e) {
            e.printStackTrace();
            return windowURL;
        }
    }

    public void editWindowGroupUrl(WindowGroup group, String url) {
        super.isValid();
        // TODO
    }

    public void deleteWindowGroupUrl(WindowURL windowURL) {
        isValid();
        String sql = "DELETE FROM WindowGroupUrl WHERE id = ?";
        try (PreparedStatement statement = createPrepStatement(sql)) {
            statement.setLong(1, windowURL.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // used later when auth is implemented

    public User getUserByUsername(String username) {
        isValid();
        String sql = "SELECT * FROM User WHERE username = ?";
        try (PreparedStatement statement = createPrepStatement(sql)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return new User(rs.getLong("id"), rs.getString("username"), rs.getString("hashedPw"));
        } catch (Exception e) {
            e.printStackTrace();
            return new User();
        }
    }

    public void addUser(User user) {
        isValid();
        String sql = "INSERT INTO User (username, hashedPw) VALUES (?, ?)";
        try (PreparedStatement statement = createPrepStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getHashedPw());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeUser() {
        super.isValid();
        // TODO
    }

    public List<Song> getSongs(){
        isValid();
        String sql = "SELECT * FROM song";

        List<Song> songs = new ArrayList<>();

        try (Statement statement = createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {

                Song song = new Song(rs.getString("name"), rs.getString("author"), rs.getString("url"), rs.getInt("genre"), rs.getLong("id"));
                songs.add(song);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return songs;
    }

    public List<Song> getSongsByGenre(int genre){
        isValid();
        String sql = "SELECT * FROM song WHERE genre = ?";

        List<Song> songsByGenre = new ArrayList<>();

        try (PreparedStatement statement = createPrepStatement(sql)) {
            statement.setInt(1, genre);
            ResultSet rs = statement.executeQuery();
            rs.next();
            Song song = new Song(rs.getString("name"), rs.getString("author"), rs.getString("url"), rs.getInt("genre"), rs.getLong("id"));
            songsByGenre.add(song);

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return songsByGenre;
    }
}
