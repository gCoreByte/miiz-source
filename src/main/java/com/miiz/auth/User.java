package com.miiz.auth;

public class User {

    private long id;
    private String username;
    private String hashedPw;

    // something went wrong
    public User() {
        this.id = -1;
    }

    public User(long id, String username, String hashedPw) {
        this.id = id;
        this.username = username;
        this.hashedPw = hashedPw;
    }

    public User(String username, String hashedPw) {
        this.username = username;
        this.hashedPw = hashedPw;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPw() {
        return hashedPw;
    }

    public void setHashedPw(String hashedPw) {
        this.hashedPw = hashedPw;
    }

    public long getId() {
        return id;
    }
}
