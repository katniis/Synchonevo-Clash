package utils;

import java.util.Scanner;

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
}
