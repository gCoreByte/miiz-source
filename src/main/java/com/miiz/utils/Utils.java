package com.miiz.utils;

public class Utils {
    public static boolean tryParse(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
