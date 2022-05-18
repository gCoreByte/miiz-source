package com.miiz;

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
        stage = primaryStage;
        primaryStage.setResizable(false); // See võiks olla kõigel peale main appi
        URL url = getClass().getResource("/startScreen.fxml");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/startScreen.fxml")));
        primaryStage.setTitle("Miiz");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        stage.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
