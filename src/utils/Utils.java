package utils;

import java.util.Scanner;

import cards.UnitType;

public class Utils {
    private static final Scanner scanner = new Scanner(System.in);

    public static void clearScreen() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\u001b[H\u001b[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            // ignore
        }
    }

    public static int promptInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt + " ");
                String line = scanner.nextLine();
                int val = Integer.parseInt(line.trim());
                if (val < min || val > max) {
                    System.out.printf("Please enter a number between %d and %d.\n", min, max);
                    return -1;
                } else  AudioPlayer.playSFX("click.wav"); return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
            }
        }
    }
    public static String promptString(String msg) {
        System.out.print(msg);
        AudioPlayer.playSFX("click.wav");
        return scanner.nextLine();
    }


    /** 
     * Pause execution for the given milliseconds.
     * @param ms milliseconds to sleep
     */
    public static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // reset interrupt status
            System.out.println("Delay interrupted!");
        }
    }

    public static String formatEnumName(UnitType type) {
        // replace underscores with spaces and capitalize first letters
        String raw = type.name();               // e.g., "DEATH_KNIGHT"
        String[] words = raw.split("_");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            sb.append(w.charAt(0)).append(w.substring(1).toLowerCase()).append(" ");
        }
        return sb.toString().trim();            // "Death Knight"
    }

    public static UnitType stringToUnitType(String name) {
        if (name == null) return null;
        try {
            return UnitType.valueOf(name.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            return null; // no matching enum
        }
    }

    public static String CenterCell(String content, boolean isShop) {
        int width = 24;
        if (!isShop) width = 19;

        if (content == null) content = "";

        // Trim if too long
        if (content.length() > width) {
            content = content.substring(0, width);
        }

        int spaces = width - content.length();
        int leftPad = spaces / 2;
        int rightPad = spaces - leftPad;

        String left = " ".repeat(leftPad);
        String right = " ".repeat(rightPad);

        return "[" + left + content + right + "]";
    }

    public static void textTyper(String text, int delay){
        for(char c : text.toCharArray()){
            System.out.print(c);
            Utils.delay(delay);
        }
        System.out.flush();
        delay(50);
    }
    // ANSI COLOR CODES
    public static final String RESET = "\u001B[0m";

    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // BRIGHT COLORS
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_PURPLE = "\u001B[95m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_WHITE = "\u001B[97m";

    // Background colors (optional)
    public static final String BG_RED = "\u001B[41m";
    public static final String BG_GREEN = "\u001B[42m";
    public static final String BG_YELLOW = "\u001B[43m";
    public static final String BG_BLUE = "\u001B[44m";
    public static final String BG_PURPLE = "\u001B[45m";
    public static final String BG_CYAN = "\u001B[46m";

    // Helper to color a string
    public static String color(String text, String colorCode) {
        return colorCode + text + RESET;
    }
    
}
