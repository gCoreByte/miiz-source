package com.miiz;

import com.miiz.controllers.startController;
import com.miiz.database.Database;
import com.miiz.database.DatabaseInit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class AppNew extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // INITIALISE APP
        Database database = new Database();

        stage = primaryStage;
        primaryStage.setResizable(false); // See võiks olla kõigel peale main appi

        // start menu
        startController startController = new startController(this);
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/startScreen.fxml")));
        fxmlLoader.setController(startController);
        Parent startScreen = fxmlLoader.load();

        primaryStage.setTitle("Miiz");
        primaryStage.setScene(new Scene(startScreen, 600, 400));
        primaryStage.show();
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        stage.getScene().setRoot(pane);
    }

    public void changeScene(FXMLLoader fxmlLoader) throws IOException {
        Parent pane = fxmlLoader.load();
        stage.getScene().setRoot(pane);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
