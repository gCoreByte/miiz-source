package com.miiz.auth;

/**
 * General user authentication class
 */
public class UserAuth {

    // user token and secret - "remember me"
    private String userToken;
    private String userSecret;

    public UserAuth(String userToken, String userSecret) {
        this.userToken = userToken;
        this.userSecret = userSecret;
    }
    //TODO

    /**
     * Tries to log the user in.
     *
     * @param username - user email to log in with
     * @param password - user password to log in with
     * @return boolean - true if success, false if error
     */
    public boolean userLogin(String username, String password) {
        return false;
    }

    /**
     * Tries to register an account.
     *
     * @param username - user email to register with
     * @param password - user password to log in with
     * @return boolean - true if succes, false if error
     */
    public boolean userRegister(String username, String password) {
        return false;
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
