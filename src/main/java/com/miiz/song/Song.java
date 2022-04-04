package com.miiz.song;

import java.util.List;

public class Song {

    private Genre genre;
    private String title;
    private String author;
    private long id;
    private String url;

    public Song(Genre genre, String title, String author, long id, String url) {
        this.genre = genre;
        this.title = title;
        this.author = author;
        this.id = id;
        this.url = url;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void printSongs(List<Song> songsList){
        for (int i = 1; i < songsList.size(); i++) {
            System.out.println(i + ". " + songsList.get(i-1).getTitle() + " " + songsList.get(i-1).getAuthor() + " poolt");
        }
    }

    @Override
    public String toString() {
        return "Song{" +
                "genre=" + genre +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", id=" + id +
                ", url='" + url + '\'' +
                '}';
    }
}
