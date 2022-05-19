package com.miiz.controllers;

import com.miiz.App;
import com.miiz.auth.UserAuth;
import com.miiz.group.WindowGroup;
import com.miiz.group.WindowGroupHandler;
import com.miiz.group.WindowURL;
import com.miiz.group.WorkSpace;
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
    private WindowGroupHandler windowGroupHandler;


    private final App app;
    public MainController(App app) {
        this.app = app;
        this.toDoListHandler = new ToDoListHandler(app.database);
        this.notepadHandler = new NotepadHandler(app.database);
        this.songHandler = new SongHandler(app.database);
        this.windowGroupHandler = new WindowGroupHandler(app.database);

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
            todoChanged();
        }
        else if (todoTree.getSelectionModel().getSelectedItem().getValue() instanceof ToDoList) {
            toDoListHandler.addLine((ToDoList) todoTree.getSelectionModel().getSelectedItem().getValue(), todoTitle.getText());
            todoChanged();
        }
    }

    public void deleteValue() {
        if (todoTree.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if (todoTree.getSelectionModel().getSelectedItem().getValue() instanceof ToDoList) {
            toDoListHandler.deleteList((ToDoList) todoTree.getSelectionModel().getSelectedItem().getValue());
            todoChanged();
        } else if (todoTree.getSelectionModel().getSelectedItem().getValue() instanceof ListLine) {
            toDoListHandler.deleteLine((ToDoList) todoTree.getSelectionModel().getSelectedItem().getParent().getValue(), (ListLine) todoTree.getSelectionModel().getSelectedItem().getValue());
            todoChanged();
        }

    }

    public void editValue() {
        if (todoTree.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if (todoTree.getSelectionModel().getSelectedItem().getValue() instanceof ToDoList) {
            toDoListHandler.editList((ToDoList) todoTree.getSelectionModel().getSelectedItem().getValue(), todoTitle.getText());
            todoChanged();
        } else if (todoTree.getSelectionModel().getSelectedItem().getValue() instanceof ListLine) {
            toDoListHandler.editLine((ToDoList) todoTree.getSelectionModel().getSelectedItem().getParent().getValue(), (ListLine) todoTree.getSelectionModel().getSelectedItem().getValue(), todoTitle.getText());
            todoChanged();
        }

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

    // workspace

    @FXML
    private TreeView<WorkSpace> workSpaceTree;

    @FXML
    private TextField insertValueWS;

    public void workspaceChanged() {
        // TREE TIME
        TreeItem<WorkSpace> rootNode = new TreeItem<>();
        rootNode.setExpanded(true);

        List<WindowGroup> lists = windowGroupHandler.getLists();
        for (WindowGroup list : lists) {
            TreeItem<WorkSpace> workSpaceTreeItem = new TreeItem<>(list);
            for (WindowURL url : list.getUrls()) {
                TreeItem<WorkSpace> urlItem = new TreeItem<>(url);
                workSpaceTreeItem.getChildren().add(urlItem);
            }
            rootNode.getChildren().add(workSpaceTreeItem);
        }
        workSpaceTree.setRoot(rootNode);
    }

    public void addValueWS() {
        if (workSpaceTree.getSelectionModel().getSelectedItem() == null) {
            windowGroupHandler.newGroup(insertValueWS.getText());
            workspaceChanged();
        }
        else if (workSpaceTree.getSelectionModel().getSelectedItem().getValue() instanceof WindowGroup) {
            windowGroupHandler.addGroupUrl((WindowGroup) workSpaceTree.getSelectionModel().getSelectedItem().getValue(), insertValueWS.getText());
            workspaceChanged();
        }

    }

    public void deleteValueWS() {
        if (workSpaceTree.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if (workSpaceTree.getSelectionModel().getSelectedItem().getValue() instanceof WindowGroup) {
            windowGroupHandler.deleteGroup((WindowGroup) workSpaceTree.getSelectionModel().getSelectedItem().getValue());
            workspaceChanged();
        } else if (workSpaceTree.getSelectionModel().getSelectedItem().getValue() instanceof WindowURL) {
            windowGroupHandler.deleteGroupUrl((WindowGroup) workSpaceTree.getSelectionModel().getSelectedItem().getParent().getValue(), (WindowURL) workSpaceTree.getSelectionModel().getSelectedItem().getValue());
            workspaceChanged();
        }
    }

    public void openValueWS() {
        if (workSpaceTree.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if (workSpaceTree.getSelectionModel().getSelectedItem().getValue() instanceof WindowGroup) {
            try {
                windowGroupHandler.openGroup((WindowGroup) workSpaceTree.getSelectionModel().getSelectedItem().getValue());
            } catch (UnsupportedOperationException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Ei ole toetatud");
                alert.setContentText("Teie arvuti ei toeta seda funktsionaalsust.");
                alert.showAndWait();
            }
        }
    }

}
