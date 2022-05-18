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
        this.database = database;
    }

    public String getFileText(String filename) throws IOException {
        return Files.readString(Path.of(filename), StandardCharsets.UTF_8);
    }

    public void saveFileText(String text, String filename) throws IOException {
        FileWriter fileWriter = new FileWriter(filename);
        fileWriter.write(text);
        fileWriter.close();
    }

    // quick and dirty
    public List<String> getFiles() {
        File folder = new File("/" + database.getUser().getUsername());
        List<String> files = new ArrayList<>();
        File[] filesDir = folder.listFiles();
        for (File file : filesDir) {
            files.add(file.getName());
        }
        return files;
    }
}
