package com.miiz.song;

import com.miiz.database.Database;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;

import static com.miiz.utils.Utils.tryParse;

public class SongHandler {

    private final Database database;
    private final Scanner inputReader;
    private final List<Song> songs;

    public SongHandler(Database database, Scanner inputReader) {
        this.database = database;
        this.inputReader = inputReader;
        this.songs = database.getSongs();
    }

    public void printSongs(List<Song> songsList){
        for (int i = 1; i < songsList.size(); i++) {
            System.out.println(i + ". " + songsList.get(i-1).getTitle() + " " + songsList.get(i-1).getAuthor() + " poolt");
        }
    }

    public void play(Song song){
        // https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
        if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            System.out.println("Teie arvuti ei toeta seda funktsionaalsust kahjuks.");
        }
        try {
            Desktop.getDesktop().browse(new URI(song.getUrl()));
        } catch (URISyntaxException e) {
            System.out.println("Lugu " + song.getTitle() + " pole võimalik esitada");
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

    private void pickSong() {
        printSongs(songs);
        System.out.println("Valige lugu, mida soovite kuulata:");
        String input = inputReader.nextLine().strip();
        if (!tryParse(input)) {
            System.out.println("Vigane sisend.");
            return;
        }
        int choice = Integer.parseInt(input) - 1;
        if (choice < 0 || choice >= songs.size() - 1) {
            System.out.println("Vigane sisend.");
            return;
        }
        play(songs.get(choice));
    }

    private void pickSongByGenre() {
        for (int i = 0; i < Genre.genres.size(); i++) {
            System.out.println(i + 1 + ". " + Genre.genres.get(i));
        }
        System.out.println("Vali žanr:");
        String input = inputReader.nextLine().strip();
        if (!tryParse(input)) {
            System.out.println("Vigane sisend.");
            return;
        }
        int choice = Integer.parseInt(input) - 1;
        if (choice < 0 || choice >= Genre.genres.size() - 1) {
            System.out.println("Vigane sisend.");
            return;
        }
        // TODO: change getsongsbygenre to also have string input as genre name
        // TODO: genre class refactoring
        // TODO: make getSongsByGenre not require +1 - not intuitive
        List<Song> songsByGenre = database.getSongsByGenre(choice + 1);
        int rand = (int) (Math.random() * songsByGenre.size());
        play(songsByGenre.get(rand));
    }

    public void main(){

        while (true) {
        System.out.println("1 - mängi juhuslik lugu");
        System.out.println("2 - vali lugu");
        System.out.println("3 - vali žanr");
        System.out.println("4 - tagasi");
        System.out.println("Sisesta tegevusele vastav number: ");
        System.out.println();

        String input = inputReader.nextLine().strip();
        switch (input) {
            case "1" -> {
                System.out.println("Esitan juhusliku loo");
                int rand = (int) (Math.random() * songs.size());
                System.out.println("Valisin " + songs.get(rand-1).getTitle());
                play(songs.get(rand));
            }

            case "2" -> pickSong();
            case "3" -> pickSongByGenre();
            case "4" -> {
                return;
            }

            default -> System.out.println("Pole valiidne sisend");

        }
        }
    }

}