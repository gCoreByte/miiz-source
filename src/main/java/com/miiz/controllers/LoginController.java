package com.miiz.controllers;

import com.miiz.App;
import com.miiz.AppNew;
import com.miiz.auth.User;
import com.miiz.auth.UserAuth;
import com.miiz.database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Objects;

public class LoginController {

    public UserAuth userAuth;
    private AppNew app;

    public LoginController(AppNew app, UserAuth userAuth) {
        this.userAuth = userAuth;
        this.app = app;
    }

    @FXML
    private Label loginFail;
    @FXML
    private TextField usernameIN;
    @FXML
    private PasswordField passwordIN;

    public void login(ActionEvent event) throws IOException { // when button pressed
        if (userAuth.userLogin(usernameIN.getText(), passwordIN.getText())) {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/mainApp.fxml")));
            MainController mainController = new MainController(app);
            fxmlLoader.setController(mainController);
            app.load(fxmlLoader);
        } else {
            loginFail.setText("Sisselogimine ebaõnnestus.");
        }

    }

    public void exit(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/startScreen.fxml")));
        StartController startController = new StartController(app, userAuth);
        fxmlLoader.setController(startController);
        app.load(fxmlLoader);

    }
}


