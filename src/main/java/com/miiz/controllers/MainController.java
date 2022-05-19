package com.miiz.controllers;

import com.miiz.App;
import com.miiz.AppNew;
import com.miiz.auth.UserAuth;
import com.miiz.group.WindowGroupHandler;
import com.miiz.notepad.NotepadHandler;
import com.miiz.song.Genre;
import com.miiz.song.Song;
import com.miiz.song.SongHandler;
import com.miiz.todolist.ToDoListHandler;
import javafx.beans.binding.ListExpression;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

public class MainController {

    private ToDoListHandler toDoListHandler;
    private NotepadHandler notepadHandler;
    private SongHandler songHandler;
    private WindowGroupHandler windowGroupHandler;

    private final AppNew app;
    public MainController(AppNew app) {
        this.app = app;
        //this.toDoListHandler = new ToDoListHandler(app.database, app.database.getUser());
        this.notepadHandler = new NotepadHandler(app.database);
        this.songHandler = new SongHandler(app.database);

        app.stage.setResizable(true);
    }

    @FXML
    private TextArea fileContent;

    @FXML
    private TextField fileName;

    @FXML
    private ChoiceBox<String> filePicker;

    @FXML
    private Button randomSong;

    @FXML
    private ChoiceBox<String> genrePicker;

    @FXML
    private ChoiceBox<String> songPicker;

    @FXML
    private Label latestSong;

    public void logout() throws IOException {
        app.database.setUser(null);
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/startScreen.fxml")));
        StartController startController = new StartController(app);
        fxmlLoader.setController(startController);
        Parent pane = fxmlLoader.load();
        app.stage.setScene(new Scene(pane, 600, 400));
    }

    public void saveFile() throws IOException {
        notepadHandler.saveFileText(fileContent.getText(), fileName.getText());
        notesChanged();
    }

    public void notesChanged() {
        String[] filesList = notepadHandler.getFiles();
        ObservableList<String> o = FXCollections.observableArrayList(filesList);
        filePicker.setItems(o);
        filePicker.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> {
            try {
                fileContent.setText(notepadHandler.getFileText(filePicker.getItems().get((Integer) number2)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void deleteFile() {
        notepadHandler.deleteFile(filePicker.getValue());
        notesChanged();
    }

    public void pickRandomSong(){
        String title = songHandler.pickRandomSong();
        latestSong.setText(title);
    }

    public void pickByGenre(){
        ObservableList<String> o = FXCollections.observableArrayList(Genre.genres);
        genrePicker.setItems(o);
        String genre = genrePicker.getSelectionModel().getSelectedItem();
        String title = songHandler.pickSongByGenre(genre);
        latestSong.setText(title);
    }

    public void pickSong(){
        ObservableList<String> o = FXCollections.observableArrayList(songHandler.getSongTitles());
        songPicker.setItems(o);
        String songTitle = songPicker.getSelectionModel().getSelectedItem();
        String title = songHandler.pickSong(songTitle);
        latestSong.setText(title);
    }
}
