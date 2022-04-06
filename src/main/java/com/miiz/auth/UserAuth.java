package com.miiz.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.miiz.database.Database;

/**
 * General user authentication class
 * This class holds the secret and token for web-based authentication later
 * It also contains authentication methods
 */
public class UserAuth {

    // user token and secret - "remember me"
    private String userToken;
    private String userSecret;
    private User user; // temporary logged in user while we do not have web auth
    private final Database database;


    public UserAuth(Database database) {
        this.database = database;
    }


    /**
     * Tries to log the user in.
     *
     * @param username - user email to log in with
     * @param password - user password to log in with
     * @return boolean - true if success, false if error
     */
    // TODO: move user authentication to server
    public boolean userLogin(String username, String password) {
        username = username.toLowerCase();
        User user = database.getUserByUsername(username);
        // id -1 means no user found
        if (user.getId() == -1) {
            return false;
        }
        String hashedPw = user.getHashedPw();
        // BCrypt - check if password == input
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPw);
        if (result.verified) {
            // TODO: user secret+token doesn't matter right now - this is important for serverside auth
            setUserSecret("temp");
            setUserToken("temp");
            this.user = user;
            database.setUser(user);
            return true;
        }
        return false;
    }

    /**
     * Tries to register an account.
     *
     * @param username - user email to register with
     * @param password - user password to log in with
     * @return boolean - true if success, false if error
     */
    public boolean userRegister(String username, String password) {
        username = username.toLowerCase();
        User user = database.getUserByUsername(username);
        // id -1 means no user found
        if (user.getId() != -1) {
            // cant register duplicate username
            return false;
        }
        // BCrypt password hashing
        user = new User(username, BCrypt.withDefaults().hashToString(12, password.toCharArray()));
        database.addUser(user);
        return true;

    }

    // getters, setters
    public User getUser() {
        return this.user;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserSecret() {
        return userSecret;
    }

    public void setUserSecret(String userSecret) {
        this.userSecret = userSecret;
    }
}
