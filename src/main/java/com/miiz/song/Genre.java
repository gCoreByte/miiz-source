package com.miiz.song;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Genre data class
 */
public class Genre {

    static List<String> genres = new ArrayList<>(Arrays.asList(
            "CLASSICAL",
            "JAZZ",
            "CHILL",
            "CALM",
            "FUNKY",
            "LOFI",
            "INSTRUMENTAL",
            "WHITENOISE"
    ));

    // TODO: unintuitive, rewrite
    public static String getGenre(List<String> genres, int pick){
        return genres.get(pick - 1);
    }

}
