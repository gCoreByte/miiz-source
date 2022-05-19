package com.miiz.controllers;

import com.miiz.App;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Objects;

public class RegisterController {

    private final App app;
    private final UserAuth userAuth;
    public RegisterController(App app, UserAuth userAuth) {
        this.app = app;
        this.userAuth = userAuth;

    }

    @FXML
    private Label registerFail;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public void onEnter(KeyEvent event) throws IOException {
        if (event.getCode() != KeyCode.ENTER) {
            return;
        }
        if (event.getSource() == username) {
            password.requestFocus();
            password.selectEnd();
        } else if (event.getSource() == password) {
            register();
        }
    }

    public void register() throws IOException { // when button pressed
        if (userAuth.userRegister(username.getText(), password.getText())) {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/mainApp.fxml")));
            MainController mainController = new MainController(app);
            fxmlLoader.setController(mainController);
            app.load(fxmlLoader);
        } else {
            registerFail.setText("Kasutajanimi on juba võetud.");
        }
    }

    public void exit() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/startScreen.fxml")));
        StartController startController = new StartController(app, userAuth);
        fxmlLoader.setController(startController);
        app.load(fxmlLoader);

    }
}
