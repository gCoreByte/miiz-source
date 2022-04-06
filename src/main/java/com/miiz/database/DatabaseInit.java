package com.miiz.database;

import java.sql.*;
import java.util.List;

/**
 * Initialisation for database
 * This class contains methods for the database itself and SQL to create new tables if needed
 */
public class DatabaseInit {
    // connection string for easy modification
    private final String connectionString = "jdbc:sqlite:sqlite.db";
    private Connection conn;

    protected DatabaseInit() {
        // first run table generation
        String USER_SQL = """
                CREATE TABLE IF NOT EXISTS User(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username varchar(255) NOT NULL UNIQUE,
                hashedPw varchar(255) NOT NULL
                )
                """;
        String TODOLIST_SQL = """
                CREATE TABLE IF NOT EXISTS ToDoList(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title varchar(255) DEFAULT "ToDo List",
                ownerid bigint NOT NULL
                )
                """;
        String LISTLINE_SQL = """
                CREATE TABLE IF NOT EXISTS ListLine(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                content varchar(255) DEFAULT "Rida",
                ownerid int NOT NULL,
                FOREIGN KEY (ownerid) REFERENCES ToDoList(id)
                )
                """;
        String WINDOWGROUP_SQL = """
                CREATE TABLE IF NOT EXISTS WindowGroup(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name varchar(255) DEFAULT "Grupp",
                ownerid bigint NOT NULL,
                FOREIGN KEY (ownerid) REFERENCES User(id)
                )
                """;
        String WINDOWGROUPURL_SQL = """
                CREATE TABLE IF NOT EXISTS WindowGroupUrl(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                ownerid bigint NOT NULL,
                url text NOT NULL,
                FOREIGN KEY (ownerid) REFERENCES WindowGroup(id)
                )
                """;
        String GENRE_SQL = """
                CREATE TABLE IF NOT EXISTS Genre(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name varchar(255) NOT NULL
                )
                """;
        String SONG_SQL = """
                CREATE TABLE IF NOT EXISTS Song(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name varchar(255) NOT NULL,
                url text NOT NULL,
                genre int DEFAULT 1,
                FOREIGN KEY (genre) REFERENCES Genre(id)
                )
                """;

        List<String> SQL_STRINGS = List.of(USER_SQL, TODOLIST_SQL, LISTLINE_SQL, WINDOWGROUP_SQL, WINDOWGROUPURL_SQL,
                GENRE_SQL, SONG_SQL);
        // establish connection
        connect();

        try (Statement statement = createStatement()) {
            SQL_STRINGS.forEach(s -> {
                try {
                    statement.executeUpdate(s);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            // if something goes wrong we cannot continue running the program
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
            e.printStackTrace();
        }
    }

    // check if our connection is still alive
    // with sqlite it is better practice to simply reuse one connection
    // with bigger databases it is best practice to use a connection pool and open a new connection
    // for each query
    protected void isValid() {
        try {
            if (!conn.isValid(1)) {
                connect();
            }
        } catch (SQLException e) {
            // this is a fatal error - database connection has died and we must exit
            e.printStackTrace();
            System.exit(-1);
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
        rs.next();
        long returnValue = rs.getLong(1);
        lastRow.close();
        return returnValue;
    }

}
