package com.miiz.controllers;

import com.miiz.App;
import com.miiz.auth.UserAuth;
import javafx.fxml.FXMLLoader;

import java.util.Objects;

/**
 * Controls the starting layout and handles communication between the user and the program
 */
public class StartController {

    // higher app;
    private final App app;
    private final UserAuth userAuth;
    public StartController(App app) {
        this.app = app;
        this.userAuth = new UserAuth(app.database);
        app.stage.setResizable(false);
    }

    public StartController(App app, UserAuth userAuth) {
        this.app = app;
        this.userAuth = userAuth;
    }


    /**
     * Takes the user to the login layout
     * @throws Exception
     */
    public void loginStart() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/login.fxml")));
        LoginController loginController = new LoginController(app, userAuth);
        fxmlLoader.setController(loginController);
        app.load(fxmlLoader);
    }

   /**
     * Takes the user to the registration layout
     * @throws Exception
     */
    public void registerStart() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/register.fxml")));
        RegisterController registerController = new RegisterController(app, userAuth);
        fxmlLoader.setController(registerController);
        app.load(fxmlLoader);
    }

}