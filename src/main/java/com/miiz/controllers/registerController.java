package com.miiz.controllers;

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

    public void register(ActionEvent event) throws IOException { // when button pressed
        registerUser();
    }

    private void registerUser() throws IOException {
        //AppNew m = new AppNew();
        //m.changeScene("startScreen.fxml"); // tagasi algusesse
        //register meetod appist
        // if register successful järgmine stseen m.changeScene("startScreen.fxml")
        // else registerFail.setText("Selle nimega kasutaja on juba vms");
    }
}
