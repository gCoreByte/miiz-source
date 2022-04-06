package com.miiz.utils;

public class Utils {

    /**
     * Helper function to check if a string can be parsed to an int
     * @param input - input string to check
     * @return true if it can be turned into an int, otherwise false
     */
    public static boolean tryParse(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Keep the separators the same length everywhere in the code easily.
     */
    public static void divider(){
        System.out.println("----------------------------------");
    }
}
