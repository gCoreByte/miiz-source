package com.miiz.controllers;

import com.miiz.AppNew;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class startController {

    @FXML
    private Button logIn;
    @FXML
    private Button register;

    public void loginStart(ActionEvent event) throws Exception {
        AppNew app = new AppNew();
        app.changeScene("/login.fxml");
    }

    public void registerStart(ActionEvent event) throws Exception {
        AppNew app = new AppNew();
        app.changeScene("/register.fxml");
    }

}