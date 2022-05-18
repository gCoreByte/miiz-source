package com.miiz.controllers;

import com.miiz.App;
import com.miiz.AppNew;
import com.miiz.auth.UserAuth;
import com.miiz.database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.util.Objects;


public class startController {

    // higher app;
    private AppNew app;
    public startController(AppNew app) {
        this.app = app;
    }

    @FXML
    private Button logIn;
    @FXML
    private Button register;

    public void loginStart(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/login.fxml")));
        loginController loginController = new loginController(database);
        fxmlLoader.setController(loginController);
        app.changeScene(fxmlLoader);
    }

    public void registerStart(ActionEvent event) throws Exception {
        app.changeScene("/register.fxml");
    }

}