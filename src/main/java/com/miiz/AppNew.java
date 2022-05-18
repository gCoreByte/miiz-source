package com.miiz;

import com.miiz.controllers.StartController;
import com.miiz.database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AppNew extends Application {

    public Stage stage;
    public Database database;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // INITIALISE APP
        database = new Database();

        stage = primaryStage;
        primaryStage.setResizable(false); // See võiks olla kõigel peale main appi

        // start menu
        StartController startController = new StartController(this);
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/startScreen.fxml")));
        fxmlLoader.setController(startController);
        Parent startScreen = fxmlLoader.load();

        primaryStage.setTitle("Miiz");
        primaryStage.setScene(new Scene(startScreen, 600, 400));
        primaryStage.show();
    }

    public void load(FXMLLoader fxmlLoader) throws IOException {
        stage.getScene().setRoot(fxmlLoader.load());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
