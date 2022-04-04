package com.miiz.database;

import java.sql.*;

public class DatabaseInit {
    private final String connectionString = "jdbc:sqlite:sqlite.db";
    private Connection conn;

    public DatabaseInit() {
        // first run table generation
        String USER_SQL = "";
        String TODOLIST_SQL = """
                CREATE TABLE ToDoList
                """;
        String LISTLINE_SQL = "";

        String WINDOWGROUP_SQL = "";
        String SONGS_SQL = "";
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

    // first run db generation

}
