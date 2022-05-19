package com.miiz.controllers;

import com.miiz.AppNew;
import com.miiz.auth.UserAuth;
import com.miiz.notepad.NotepadHandler;
import com.miiz.song.Genre;
import com.miiz.song.SongHandler;
import com.miiz.todolist.ListLine;
import com.miiz.todolist.ToDoList;
import com.miiz.todolist.ToDoListHandler;
import com.miiz.todolist.TodoTree;
import javafx.beans.binding.ListExpression;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

public class MainController {

    private ToDoListHandler toDoListHandler;
    private NotepadHandler notepadHandler;
    private SongHandler songHandler;


    private final AppNew app;
    public MainController(AppNew app) {
        this.app = app;
        this.toDoListHandler = new ToDoListHandler(app.database);
        this.notepadHandler = new NotepadHandler(app.database);
        this.songHandler = new SongHandler(app.database);

        app.stage.setResizable(true);
    }

    // todolist

    @FXML
    private TreeView<TodoTree> todoTree;

    @FXML
    private TextField todoTitle;

    public void todoChanged() {
        // TREE TIME
        TreeItem<TodoTree> rootNode = new TreeItem<>();
        rootNode.setExpanded(true);

        List<ToDoList> lists = toDoListHandler.getLists();
        for (ToDoList list : lists) {
            TreeItem<TodoTree> toDoListTreeItem = new TreeItem<>(list);
            for (ListLine line : list.getListLines()) {
                TreeItem<TodoTree> listLineItem = new TreeItem<>(line);
                toDoListTreeItem.getChildren().add(listLineItem);
            }
            rootNode.getChildren().add(toDoListTreeItem);
        }
        todoTree.setRoot(rootNode);
    }

    public void addValue() {
        if (todoTree.getSelectionModel().getSelectedItem() == null) {
            toDoListHandler.addList(todoTitle.getText());
        }
        else if (todoTree.getSelectionModel().getSelectedItem().getValue() instanceof ToDoList) {
            toDoListHandler.addLine((ToDoList) todoTree.getSelectionModel().getSelectedItem().getValue(), todoTitle.getText());
        }
        todoChanged();
    }

    public void deleteValue() {
        if (todoTree.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if (todoTree.getSelectionModel().getSelectedItem().getValue() instanceof ToDoList) {
            toDoListHandler.deleteList((ToDoList) todoTree.getSelectionModel().getSelectedItem().getValue());
        } else if (todoTree.getSelectionModel().getSelectedItem().getValue() instanceof ListLine) {
            toDoListHandler.deleteLine((ToDoList) todoTree.getSelectionModel().getSelectedItem().getParent().getValue(), (ListLine) todoTree.getSelectionModel().getSelectedItem().getValue());
        }
        todoChanged();
    }

    public void editValue() {
        if (todoTree.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if (todoTree.getSelectionModel().getSelectedItem().getValue() instanceof ToDoList) {
            toDoListHandler.editList((ToDoList) todoTree.getSelectionModel().getSelectedItem().getValue(), todoTitle.getText());
        } else if (todoTree.getSelectionModel().getSelectedItem().getValue() instanceof ListLine) {
            toDoListHandler.editLine((ToDoList) todoTree.getSelectionModel().getSelectedItem().getParent().getValue(), (ListLine) todoTree.getSelectionModel().getSelectedItem().getValue(), todoTitle.getText());
        }
        todoChanged();
    }

    // notepad

    @FXML
    private TextArea fileContent;

    @FXML
    private TextField fileName;

    @FXML
    private ChoiceBox<String> filePicker;

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

    // Songs

    @FXML
    private Button randomSong;

    @FXML
    private ChoiceBox<String> genrePicker;

    @FXML
    private ChoiceBox<String> songPicker;

    @FXML
    private Label latestSong;

    public void pickRandomSong(){
        String title = songHandler.pickRandomSong();
        latestSong.setText(title);
    }

    public void songsChanged() {
        ObservableList<String> o = FXCollections.observableArrayList(Genre.genres);
        genrePicker.setItems(o);
        genrePicker.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> {
            try {
                String genre = genrePicker.getItems().get((Integer) number2);
                String title = songHandler.pickSongByGenre(genre);
                latestSong.setText(title);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ObservableList<String> i = FXCollections.observableArrayList(songHandler.getSongTitles());
        songPicker.setItems(i);
        songPicker.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> {
            try {
                String songTitle = songPicker.getItems().get((Integer) number2);
                String title = songHandler.pickSong(songTitle);
                latestSong.setText(title);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
