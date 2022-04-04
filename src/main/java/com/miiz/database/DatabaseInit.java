package com.miiz.database;

import java.sql.*;
import java.util.List;

public class DatabaseInit {
    private final String connectionString = "jdbc:sqlite:sqlite.db";
    private Connection conn;

    public DatabaseInit() {
        // first run table generation
        String USER_SQL = """
                CREATE TABLE IF NOT EXISTS User(
                id bigint NOT NULL AUTO_INCREMENT,
                username varchar(255) NOT NULL UNIQUE,
                hashedPw varchar(255) NOT NULL,
                PRIMARY KEY (id)
                """;
        String TODOLIST_SQL = """
                CREATE TABLE IF NOT EXISTS ToDoList(
                id bigint NOT NULL AUTO_INCREMENT,
                title varchar(255) DEFAULT "ToDo List",
                ownerid bigint NOT NULL,
                PRIMARY KEY (id)
                )
                """;
        String LISTLINE_SQL = """
                CREATE TABLE IF NOT EXISTS ListLine(
                id bigint NOT NULL AUTO_INCREMENT,
                content varchar(255) DEFAULT "Rida",
                ownerid bigint NOT NULL AUTO_INCREMENT,
                PRIMARY KEY (id),
                FOREIGN KEY (ownerid) REFERENCES ToDoList(id)
                """;
        String WINDOWGROUP_SQL = """
                CREATE TABLE IF NOT EXISTS WindowGroup(
                id bigint NOT NULL AUTO_INCREMENT,
                name varchar(255) DEFAULT "Grupp",
                ownerid bigint NOT NULL,
                PRIMARY KEY (id),
                FOREIGN KEY (ownerid) REFERENCES User(id)
                """;
        String WINDOWGROUPURL_SQL = """
                CREATE TABLE IF NOT EXISTS WindowGroupUrl(
                id bigint NOT NULL AUTO_INCREMENT,
                ownerid bigint NOT NULL,
                url text NOT NULL,
                PRIMARY KEY (id),
                FOREIGN KEY (ownerid) REFERENCES WindowGroup(id)
                """;
        String GENRE_SQL = """
                CREATE TABLE IF NOT EXISTS Genre(
                id bigint NOT NULL AUTO_INCREMENT,
                name varchar(255) NOT NULL,
                PRIMARY KEY (id)
                """;
        String SONG_SQL = """
                CREATE TABLE IF NOT EXISTS Song(
                id bigint NOT NULL AUTO_INCREMENT,
                name varchar(255) NOT NULL,
                url text NOT NULL,
                genre int DEFAULT 0,
                PRIMARY KEY (id),
                FOREIGN KEY (genre) REFERENCES Genre(id)
                """;

        List<String> SQL_STRINGS = List.of(USER_SQL, TODOLIST_SQL, LISTLINE_SQL, WINDOWGROUP_SQL, WINDOWGROUPURL_SQL,
                GENRE_SQL, SONG_SQL);
        // establish connection
        connect();

        try (Statement statement = createStatement()) {
            SQL_STRINGS.forEach(s -> {
                try {
                    statement.executeQuery(s);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            System.out.println("Something went wrong with DB init.");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void connect() {
        try {
            conn = DriverManager.getConnection(connectionString);
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    protected void isValid() {
        try {
            if (!conn.isValid(1)) {
                connect();
            }
        } catch (SQLException e) {
            System.out.println(e);
            connect();
        }
    }

    protected Statement createStatement() throws SQLException {
        isValid();
        return conn.createStatement();
    }

    protected PreparedStatement createPrepStatement(String sql) throws SQLException {
        isValid();
        return conn.prepareStatement(sql);
    }

    protected long getLastRowId() throws SQLException {
        Statement lastRow = createStatement();
        ResultSet rs = lastRow.executeQuery("SELECT last_insert_rowid()");
        lastRow.close();
        rs.next();
        return rs.getLong(1);
    }

}
