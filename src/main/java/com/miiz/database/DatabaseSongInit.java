package com.miiz.database;

import com.miiz.song.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to feed a selection of starter songs to the database if none are found.
 */
public class DatabaseSongInit {
    public static void initSongs(Database database) {
        List<Song> songs = new ArrayList<>();
        // CLASSICAL
        songs.add(new Song("You're in the Dead Poets Society", "helenelinor", "https://www.youtube.com/watch?v=h0ZiJbux4QI", 1));
        songs.add(new Song("DARK ACADEMIA PLAYLIST for melancholic writers (classical)", "helenelinor", "https://www.youtube.com/watch?v=UrxlRXKii10", 1));
        songs.add(new Song("you're a hopeless romantic but in the 19th century", "ultravclet", "https://www.youtube.com/watch?v=Y56ODjb8ZMo", 1));

        // JAZZ
        songs.add(new Song("Miles Davis Meets Thelonious Monk", "Jazz Essential", "https://www.youtube.com/watch?v=O7zaUceGCmY", 2));
        songs.add(new Song("Afternoon Lounge Jazz - Relaxing Jazz Music for Work & Study", "Musictag", "https://www.youtube.com/watch?v=IRyJe-0Uie0", 2));
        songs.add(new Song("Motown Jazz - Smooth Jazz Music & Jazz Instrumental Music for Relaxing and Study | Soft Jazz", "Dr. SaxLove Music Experience", "https://www.youtube.com/watch?v=c9xtkAZSK50", 2));

        // CHILL
        songs.add(new Song("2 AM Coding Session - Lofi Hip Hop Mix [Study & Coding Beats]", "Lofi Ghostie", "https://www.youtube.com/watch?v=BPVu5xaZk-4", 3));
        songs.add(new Song("3:30 AM Coding Session - Lofi Hip Hop Mix [Study & Coding Beats]", "Lofi Ghostie", "https://www.youtube.com/watch?v=8nXqcugV2Y4", 3));
        songs.add(new Song("4 A.M Study Session - [lofi hip hop/chill beats]", "Lofi Girl", "https://www.youtube.com/watch?v=TURbeWK2wwg", 3));

        // CALM
        songs.add(new Song("Live Well", "Palace", "https://www.youtube.com/watch?v=f6noiVByjzM", 4));
        songs.add(new Song("Lavender Kiss", "Juliette & The Licks", "https://www.youtube.com/watch?v=7fKlAJUKduk", 4));
        songs.add(new Song("I Think I Like When It Rains", "WILLIS", "https://www.youtube.com/watch?v=HpYdA-06jTc", 4));

        // FUNKY
        songs.add(new Song("Lipps Inc", "Funkytown", "https://www.youtube.com/watch?v=s36eQwgPNSE", 5));
        songs.add(new Song("Get Lucky (Radio Edit) [feat. Pharrell Williams]", "Daft Punk", "https://www.youtube.com/watch?v=Dm2YD2AbZsE", 5));
        songs.add(new Song("Best of Acid Jazz and Funky Grooves", "AcidJazz", "https://www.youtube.com/watch?v=QK2mtWjtyDU", 5));

        // LOFI
        songs.add(new Song("lonely day - lofi hiphop mix", "Cloudx Music", "https://www.youtube.com/watch?v=eSjSozKL_EA", 6));
        songs.add(new Song("1 A.M Study Session - [lofi hip hop/chill beats]", "Lofi Girl", "https://www.youtube.com/watch?v=lTRiuFIWV54", 6));
        songs.add(new Song("lofi songs for slow days", "the bootleg boy", "https://www.youtube.com/watch?v=AzV77KFsLn4", 6));

        // INSTUMENTAL
        songs.add(new Song("Night at Work | Instrumental Chill Music Mix", "Fluidified", "https://www.youtube.com/watch?v=n9Y2Eb4BaSg", 7));
        songs.add(new Song("Instrumental Pop Songs 2020 | Study Music (2 Hours)", "Mood Melodies", "https://www.youtube.com/watch?v=mX2L_lVSkOY", 7));
        songs.add(new Song("you're inside monet paintings (playlist)", "nobody", "https://www.youtube.com/watch?v=WDtAwXZkdU0", 7));

        // WHITENOISE
        songs.add(new Song("3 HOURS of GENTLE NIGHT RAIN", "The Relaxed Guy", "https://www.youtube.com/watch?v=q76bMs-NwRk", 8));
        songs.add(new Song("Rainy Day at Cozy Coffee Shop with Relaxing Jazz Music, Fireplace and Rain Sounds", "Coffee Shop Vibes", "https://www.youtube.com/watch?v=6WXMivVkiR8", 8));
        songs.add(new Song("Soft Rain & Thunderstorm Sounds with Fireplace - Classic Room Ambience", "New Bliss", "https://www.youtube.com/watch?v=NAsb21ctmhI", 8));


        // Add to database
        songs.forEach(database::addSong);
    }
}
