package com.miiz.song;

public class Song {


    private String title;
    private String author;
    private String url;
    private Genre genre;
    private long id;

    public Song(String title, String author, String url, Genre genre, long id) {
        this.title = title;
        this.author = author;
        this.url = url;
        this.genre = genre;
        this.id = id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                ", genre=" + genre +
                ", id=" + id +
                '}';
    }
}