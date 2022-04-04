package com.miiz.database;

import java.sql.*;

public class DatabaseInit {
    private final String connectionString = "jdbc:sqlite:sqlite.db";
    private Connection conn;

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
}
