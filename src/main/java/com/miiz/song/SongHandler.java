package com.miiz.song;

import com.miiz.database.Database;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;

public class SongHandler {

    private final Database database;
    private final Scanner inputReader;

    public SongHandler(Database database, Scanner inputReader) {
        this.database = database;
        this.inputReader = inputReader;
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

    public void main(){

        List<Song> songs = database.getSongs();

        System.out.println("Vali tegevus:");
        System.out.println();
        System.out.println("1 - mängi juhuslik lugu");
        System.out.println("2 - vali lugu");
        System.out.println("3 - vali žanr");
        System.out.println("4 - tagasi");
        System.out.println("Sisesta tegevusele vastav number: ");
        System.out.println();

        String input = inputReader.nextLine().strip();

        while (true){
            switch (input) {
                case "1" -> {
                    System.out.println("Esitan juhusliku loo");
                    int rand = (int) (Math.random() * songs.size());
                    System.out.println("Valisin " + songs.get(rand-1).getTitle());
                    play(songs.get(rand));
                }

                case "2" -> {
                    System.out.println("Vali lugu, mille soovid esitada:");
                    printSongs(songs);
                    int pick = Integer.parseInt(inputReader.nextLine().strip());
                    play(songs.get(pick -1));
                }

                case "3" -> {
                    System.out.println("Vali žanr:");
                    for (int i = 1; i < Genre.genres.size() + 1; i++) {
                        System.out.println(i + ". " + Genre.genres.get(i-1));
                    }
                    int pick = Integer.parseInt(inputReader.nextLine().strip());
                    List<Song> songsByGenre = database.getSongsByGenre(pick -1 );
                    int rand = (int) (Math.random() * songsByGenre.size());
                    play(songsByGenre.get(rand));
                }
                case "4" -> {
                    return;
                }

                default -> System.out.println("Pole valiidne sisend");

            }
        }
    }

}