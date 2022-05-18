package com.miiz.controllers;

import com.miiz.AppNew;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class loginController {

    @FXML
    private Button loginB;
    @FXML
    private Label loginFail;
    @FXML
    private TextField usernameIN;
    @FXML
    private PasswordField passwordIN;

    public void login(ActionEvent event) throws IOException { // when button pressed
        userLogin();
    }

    private void userLogin() throws IOException {
        //AppNew m = new AppNew();
        //m.changeScene("main.fxml"); // uus stseen peale edukat sisselogimist
        //login meetod appist
        // if login successful järgmine stseen m.changeScene("mainApp.fxml")
        // else loginFail.setText("Sisselogimine ebaõnnestus");
    }
}
