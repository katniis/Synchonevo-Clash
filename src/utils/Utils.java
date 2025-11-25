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
                } else return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
            }
        }
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

}
