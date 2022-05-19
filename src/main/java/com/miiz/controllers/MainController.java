package com.miiz.controllers;

import com.miiz.App;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Controls the main layout and handles communication between the user and the program
 */
public class MainController {

    /**
     * All the required handlers, which handle the data
     */
    private ToDoListHandler toDoListHandler;
    private NotepadHandler notepadHandler;
    private SongHandler songHandler;
    private WindowGroupHandler windowGroupHandler;


    private final App app;
    public MainController(App app) {
        this.app = app;
        /**
         * Initialising handlers
         */
        this.toDoListHandler = new ToDoListHandler(app.database);
        this.notepadHandler = new NotepadHandler(app.database);
        this.songHandler = new SongHandler(app.database);
        this.windowGroupHandler = new WindowGroupHandler(app.database);

        app.stage.setResizable(true);
    }

    /**
     * Initialises the listeners
     */
    public void initialise() {
        filePicker.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> {
            try {
                fileContent.setText(notepadHandler.getFileText(filePicker.getItems().get((Integer) number2)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        genrePicker.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> {
            try {
                String genre = genrePicker.getItems().get((Integer) number2);
                String title = songHandler.pickSongByGenre(genre);
                latestSong.setText(title);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

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

    /**
     * ToDoList tab
     */

    @FXML
    private TreeView<TodoTree> todoTree;

    @FXML
    private TextField todoTitle;

    /**
     * Redraws the tree whenever called.
     * Gets called whenever the tab is opened and whenever the tree changes.
     */
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


    /**
     * Adds a new value to the tree, either a List or a Line
     */
    public void addValue() {
        if (todoTree.getSelectionModel().getSelectedItem() == null || todoTree.getSelectionModel().getSelectedItem().getValue() instanceof ListLine) {
            toDoListHandler.addList(todoTitle.getText());
            todoTitle.clear();
            todoChanged();
        }
        else if (todoTree.getSelectionModel().getSelectedItem().getValue() instanceof ToDoList) {
            toDoListHandler.addLine((ToDoList) todoTree.getSelectionModel().getSelectedItem().getValue(), todoTitle.getText());
            todoTitle.clear();
            todoChanged();
        }
    }

    /**
     * Deletes a value from the tree, either a List or a Line
     */
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

    /**
     * Deletes a value from the tree, either a List or a Line
     */
    public void editValue() {
        if (todoTree.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if (todoTree.getSelectionModel().getSelectedItem().getValue() instanceof ToDoList) {
            toDoListHandler.editList((ToDoList) todoTree.getSelectionModel().getSelectedItem().getValue(), todoTitle.getText());
            todoTitle.clear();
            todoChanged();
        } else if (todoTree.getSelectionModel().getSelectedItem().getValue() instanceof ListLine) {
            toDoListHandler.editLine((ToDoList) todoTree.getSelectionModel().getSelectedItem().getParent().getValue(), (ListLine) todoTree.getSelectionModel().getSelectedItem().getValue(), todoTitle.getText());
            todoTitle.clear();
            todoChanged();
        }

    }

    /**
     * Notepad tab
     */

    @FXML
    private TextArea fileContent;

    @FXML
    private TextField fileName;

    @FXML
    private ChoiceBox<String> filePicker;

    /**
     * Logs the user out
     * @throws IOException
     */
    public void logout() throws IOException {
        app.database.setUser(null);
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/startScreen.fxml")));
        StartController startController = new StartController(app);
        fxmlLoader.setController(startController);
        Parent pane = fxmlLoader.load();
        app.stage.setScene(new Scene(pane, 600, 400));
    }

    /**
     * Saves the file the user is editing
     * @throws IOException
     */
    public void saveFile() throws IOException {
        notepadHandler.saveFileText(fileContent.getText(), fileName.getText());
        fileContent.clear();
        fileName.clear();
        notesChanged();
    }

    /**
     *  Whenever we open the tab, we want to check for new files
     */
    public void notesChanged() {
        String[] filesList = notepadHandler.getFiles();
        ObservableList<String> o = FXCollections.observableArrayList(filesList);
        filePicker.setItems(o);
    }

    public void deleteFile() {
        notepadHandler.deleteFile(filePicker.getValue());
        notesChanged();
    }

    /**
     * Songs tab
     */

    @FXML
    private ChoiceBox<String> genrePicker;

    @FXML
    private ChoiceBox<String> songPicker;

    @FXML
    private Label latestSong;

    /**
     * Plays a random song
     */
    public void pickRandomSong(){
        String title = songHandler.pickRandomSong();
        latestSong.setText(title);
    }

    /**
     * Updates song on tab change
     */
    public void songsChanged() {
        ObservableList<String> o = FXCollections.observableArrayList(Genre.genres);
        genrePicker.setItems(o);

        ObservableList<String> i = FXCollections.observableArrayList(songHandler.getSongTitles());
        songPicker.setItems(i);

    }

    /**
     * Workspace tab
     */

    @FXML
    private TreeView<WorkSpace> workSpaceTree;

    @FXML
    private TextField insertValueWS;

    /**
     * Redraws tree on tab change
     */
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

    /**
     * Adds a value to the tree
     */
    public void addValueWS() {
        if (workSpaceTree.getSelectionModel().getSelectedItem() == null || workSpaceTree.getSelectionModel().getSelectedItem().getValue() instanceof WindowURL) {
            windowGroupHandler.newGroup(insertValueWS.getText());
            insertValueWS.clear();
            workspaceChanged();
        }
        else if (workSpaceTree.getSelectionModel().getSelectedItem().getValue() instanceof WindowGroup) {
            windowGroupHandler.addGroupUrl((WindowGroup) workSpaceTree.getSelectionModel().getSelectedItem().getValue(), insertValueWS.getText());
            insertValueWS.clear();
            workspaceChanged();
        }

    }

    /**
     * Deletes a value
     */
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

    /**
     * Opens a group
     */
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
