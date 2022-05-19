package com.miiz.controllers;

import com.miiz.App;
import com.miiz.auth.UserAuth;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.util.Objects;


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


    public void loginStart() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/login.fxml")));
        LoginController loginController = new LoginController(app, userAuth);
        fxmlLoader.setController(loginController);
        app.load(fxmlLoader);
    }

    public void registerStart() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/register.fxml")));
        RegisterController registerController = new RegisterController(app, userAuth);
        fxmlLoader.setController(registerController);
        app.load(fxmlLoader);
    }

}