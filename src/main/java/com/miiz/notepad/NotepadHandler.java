package com.miiz.notepad;

import com.miiz.database.Database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class NotepadHandler {
    private final Database database;

    public NotepadHandler(Database database) {
        File file = new File("notepad" + "/" + database.getUser().getUsername() + "/");
        file.mkdirs();
        this.database = database;
    }

    /**
     * Gets all the text in a file
     * @param filename - file to get
     * @return String - file content
     * @throws IOException
     */
    public String getFileText(String filename) throws IOException {
        return Files.readString(Path.of(filePathGen(filename)), StandardCharsets.UTF_8);
    }

    /**
     * Help function to get the file path
     * @param filename - file to get
     * @return String - path to file
     */
    private String filePathGen(String filename) {
        return "notepad" + "/" + database.getUser().getUsername() + "/" + filename;
    }

    /**
     * Saves the file
     * @param text - file contents
     * @param filename - file to save to
     * @throws IOException
     */
    public void saveFileText(String text, String filename) throws IOException {
        FileWriter fileWriter = new FileWriter(filePathGen(filename), StandardCharsets.UTF_8);
        fileWriter.write(text);
        fileWriter.close();
    }

    /**
     * Gets all files in a directory
     * @return String[] all filenames in dir
     */
    public String[] getFiles() {
        File folder = new File( "notepad" + "/" + database.getUser().getUsername() + "/");
        File[] filesDir = folder.listFiles();
        // no files in dir -> return empty array
        if (filesDir == null) {
            return new String[0];
        }
        String[] strings = new String[filesDir.length];
        for (int i = 0; i < filesDir.length; i++) {
            strings[i] = filesDir[i].getName();
        }
        return strings;
    }

    /**
     * Delete a file
     * @param filename - file to delete
     */
    public void deleteFile(String filename) {
        File file = new File(filePathGen(filename));
        file.delete();
    }
}
