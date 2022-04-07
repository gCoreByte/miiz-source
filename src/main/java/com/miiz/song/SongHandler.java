package com.miiz.song;

import com.miiz.database.Database;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static com.miiz.utils.Utils.divider;
import static com.miiz.utils.Utils.tryParse;

/**
 * Song user interface class
 * This class handles all user-backend communication for the Song module
 */
public class SongHandler {

    private final Database database;
    private final Scanner inputReader;
    private final List<Song> songs;

    /**
     * constructor
     * @param database - database class to make queries
     * @param inputReader - a shared input scanner to avoid weird bugs
     */
    public SongHandler(Database database, Scanner inputReader) {
        this.database = database;
        this.inputReader = inputReader;
        // initialise the Song list
        this.songs = database.getSongs();
    }

    /**
     * Plays a song if the user's platform supports opening a browser
     * @param song - the song to play
     */
    public void play(Song song){
        // https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
        if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            System.out.println("Teie arvuti kahjuks ei toeta seda funktsionaalsust.");
            divider();
        }
        try {
            Desktop.getDesktop().browse(new URI(song.getUrl()));
        } catch (URISyntaxException e) {
            System.out.println("Lugu " + song.getTitle() + " pole võimalik esitada");
            divider();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

    /**
     * Lets the user pick a song to play
     */
    private void pickSong() {
        System.out.println("Valige lugu, mida soovite kuulata:");
        String input = inputReader.nextLine().strip();
        if (!tryParse(input)) {
            System.out.println("Vigane sisend.");
            divider();
            return;
        }
        int choice = Integer.parseInt(input) - 1;
        if (choice < 0 || choice >= songs.size() - 1) {
            System.out.println("Vigane sisend.");
            divider();
            return;
        }
        play(songs.get(choice));
    }

    /**
     * Lets the user pick a genre from which a song will be played randomly.
     */
    private void pickSongByGenre() {
        for (int i = 0; i < Genre.genres.size(); i++) {
            System.out.println(i + 1 + ". " + Genre.genres.get(i));
        }
        System.out.println("Vali žanr:");
        String input = inputReader.nextLine().strip();
        if (!tryParse(input)) {
            System.out.println("Vigane sisend.");
            divider();
            return;
        }
        int choice = Integer.parseInt(input) - 1;
        if (choice < 0 || choice >= Genre.genres.size() - 1) {
            System.out.println("Vigane sisend.");
            divider();
            return;
        }
        // TODO: change getsongsbygenre to also have string input as genre name
        // TODO: genre class refactoring
        // TODO: make getSongsByGenre not require +1 - not intuitive
        List<Song> songsByGenre = database.getSongsByGenre(choice + 1);
        Random r = new Random();
        int rand = r.nextInt(songsByGenre.size());
        play(songsByGenre.get(rand));
    }

    /**
     * Picks a random song to play.
     */
    private void pickRandomSong() {
        System.out.println("Esitan juhusliku loo");
        Random r = new Random();
        int rand = r.nextInt(songs.size());
        System.out.println("Valisin " + songs.get(rand).getTitle());
        divider();
        play(songs.get(rand));
    }

    /**
     * Main method of SongHandler
     */
    public void main() {
        // no songs found, failed to initialise?
        if (songs.size() == 0) {
            System.out.println("Andmebaasis ei ole ühtegi laulu.");
            System.out.println("Laulude moodul ei tööta.");
            return;
        }

        while (true) {
            divider();
            for (int i = 0; i < songs.size(); i++) {
                System.out.println(i + 1 + ". " + songs.get(i));
            }
            divider();
            System.out.println("0 - Tagasi");
            System.out.println("1 - Mängi juhuslik lugu");
            System.out.println("2 - Vali lugu");
            System.out.println("3 - Vali žanr");
            System.out.println("Sisesta tegevusele vastav number: ");
            System.out.println();

            String input = inputReader.nextLine().strip();
            switch (input) {
                case "1" -> pickRandomSong();
                case "2" -> pickSong();
                case "3" -> pickSongByGenre();
                case "0" -> {
                    // exit out of module
                    return;
                }
                // user input is invalid
                default -> {
                    System.out.println("Vigane sisend.");
                    divider();
                }
            }
        }
    }

}