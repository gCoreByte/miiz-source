package com.miiz.notepad;

import com.miiz.database.Database;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class NotepadHandler {
    private final Database database;

    public NotepadHandler(Database database) {
        File file = new File("notepad" + "/" + database.getUser().getUsername() + "/");
        file.mkdirs();
        this.database = database;
    }

    public String getFileText(String filename) throws IOException {
        return Files.readString(Path.of(filePathGen(filename)), StandardCharsets.UTF_8);
    }

    private String filePathGen(String filename) {
        return "notepad" + "/" + database.getUser().getUsername() + "/" + filename;
    }

    public void saveFileText(String text, String filename) throws IOException {
        FileWriter fileWriter = new FileWriter(filePathGen(filename), StandardCharsets.UTF_8);
        fileWriter.write(text);
        fileWriter.close();
    }

    // quick and dirty
    public String[] getFiles() {
        File folder = new File( "notepad" + "/" + database.getUser().getUsername() + "/");
        File[] filesDir = folder.listFiles();
        if (filesDir == null) {
            return new String[0];
        }
        String[] strings = new String[filesDir.length];
        for (int i = 0; i < filesDir.length; i++) {
            strings[i] = filesDir[i].getName();
        }
        return strings;
    }

    public void deleteFile(String filename) {
        File file = new File(filePathGen(filename));
        file.delete();
    }
}
