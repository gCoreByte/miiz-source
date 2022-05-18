package com.miiz.controllers;

import com.miiz.AppNew;
import com.miiz.auth.UserAuth;
import com.miiz.database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class registerController {
    @FXML
    private Button registertB;
    @FXML
    private Label registerFail;
    @FXML
    private TextField usernameIN;
    @FXML
    private PasswordField passwordIN;
    @FXML
    private Button exitB;

    public void register(ActionEvent event) throws IOException { // when button pressed
        registerUser(new UserAuth(new Database())); //TODO properly
    }

    public void exit(ActionEvent event) throws IOException {
        AppNew m = new AppNew();
        m.changeScene("/mainApp.fxml");
    }

    private void registerUser(UserAuth userAuth) throws IOException {
        AppNew m = new AppNew();
        if (userAuth.userRegister(usernameIN.getText(), passwordIN.getText())){
            m.changeScene("/start.fxml");
        }
        else{
            registerFail.setText("Kasutajanimi on juba võetud");
        }
    }
}
