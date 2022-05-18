package com.miiz.controllers;

import com.miiz.App;
import com.miiz.AppNew;
import com.miiz.auth.UserAuth;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Objects;

public class MainController {

    private AppNew app;
    public MainController(AppNew app) {
        this.app = app;
    }

    public void logout() throws IOException {
        app.database.setUser(null);
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/startScreen.fxml")));
        StartController startController = new StartController(app);
        fxmlLoader.setController(startController);
        app.load(fxmlLoader);
    }
}
