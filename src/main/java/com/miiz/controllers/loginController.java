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
        userLogin(new UserAuth(new Database())); //TODO properly

    }

    private void userLogin(UserAuth userAuth) throws IOException {
        AppNew m = new AppNew();
        if (userAuth.userLogin(usernameIN.getText(), passwordIN.getText())){
            m.changeScene("mainApp.fxml");
        }
        else{
            loginFail.setText("Sisselogimine ebaõnnestus");
        }
    }
}
