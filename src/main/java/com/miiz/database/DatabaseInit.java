package com.miiz.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

    public Statement createStatement() throws SQLException {
        isValid();
        return conn.createStatement();
    }
}
