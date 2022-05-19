package com.miiz.controllers;

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

public class RegisterController {

    private final AppNew app;
    private final UserAuth userAuth;
    public RegisterController(AppNew app, UserAuth userAuth) {
        this.app = app;
        this.userAuth = userAuth;

    }

    @FXML
    private Label registerFail;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public void register(ActionEvent event) throws IOException { // when button pressed
        if (userAuth.userRegister(username.getText(), password.getText())) {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/mainApp.fxml")));
            MainController mainController = new MainController(app);
            fxmlLoader.setController(mainController);
            app.load(fxmlLoader);
        } else {
            registerFail.setText("Kasutajanimi on juba võetud.");
        }
    }

    public void exit(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/startScreen.fxml")));
        StartController startController = new StartController(app, userAuth);
        fxmlLoader.setController(startController);
        app.load(fxmlLoader);

    }
}