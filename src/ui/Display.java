package ui;

import java.util.ArrayList;
import java.util.List;
import cards.*;
import main.*;
import units.*;
import utils.Utils;
import boss.*;

public class Display {
    
    
    private Game game;
    public Display(Game g) { this.game = g; }
    private Unit[] placeholder = new Unit[9];

    public void renderShop(Shop shop, Player player, int stage) {
        System.out.println("\n======================");
        System.out.printf("Stage %d | Player: %s | Gold: %d\n", stage, player.getName(), player.getGold());
        System.out.println("======================");
        shop.display();
    }

    public void mainUI(Shop shop, Player player,Unit unit, Boss boss, int stage){
        //renderShop(shop, player, stage);
        //printUnits(player);
        //printBench(player);
        List<Unit> units = player.getDeck();
        int width = 80;
        // Top border
        System.out.println("┌" + "─".repeat(width) + "┐");

        // Title
        String title = "Synchonevo Clash";
        System.out.println("│" + centerText(title, width) + "│");

        // Separator
        System.out.println("└" + "─".repeat(width) + "┘");

        // Empty lines before boss
        for (int i = 0; i < 2; i++) System.out.println(" ".repeat(width));

        // Boss line
        System.out.println(centerText("[" /*+ boss.getBossName()*/ + "]", width) );

        // Empty lines after boss
        for (int i = 0; i < 2; i++) System.out.println(" ".repeat(width));

        System.out.println("\t[ " + namePlaceholder(placeholder[0]) + " ]"
                   + "\t[ " + namePlaceholder(placeholder[1]) + " ]"
                   + "\t[ " + namePlaceholder(placeholder[2]) + " ]");

        for (int i = 0; i < 2; i++) System.out.println(" ".repeat(width));

        System.out.println("\t[ " + namePlaceholder(placeholder[3]) + " ]"
                        + "\t[ " + namePlaceholder(placeholder[4]) + " ]"
                        + "\t[ " + namePlaceholder(placeholder[5]) + " ]");

        for (int i = 0; i < 2; i++) System.out.println(" ".repeat(width) );

        System.out.println("\t[ " + namePlaceholder(placeholder[6]) + " ]"
                        + "\t[ " + namePlaceholder(placeholder[7]) + " ]"
                        + "\t[ " + namePlaceholder(placeholder[8]) + " ]");

        for (int i = 0; i < 2; i++) System.out.println( " ".repeat(width));
        
        // Store Section
        System.out.println("┌" + "─".repeat(width) + "┐");
        shop.display();

        // Bench section
        System.out.println("\n├" + "─".repeat(width) + "┤");
        printBench(player);
        
        // Player section
        System.out.println("├" + "─".repeat(width) + "┤");
        System.out.println(" " + player.getName());

        // Bottom border
        System.out.println("└" + "─".repeat(width) + "┘");

    }


    public String namePlaceholder(Unit unit){
        if(unit != null) {
            return unit.displayName();
        }else{
            return "             ";
        }
    }

    public void printUnits(Player player) {
        System.out.println("Main Board Units:");
        List<Unit> units = player.getDeck();
        int unitsPerRow = 3;
        for (int i = 0; i < units.size(); i++) {
            Unit u = units.get(i);

            String name = u.getName();
            System.out.print("[" + name + "]\t");

            // Newline after every `unitsPerRow`
            if ((i + 1) % unitsPerRow == 0) {
                System.out.println();
            }
        }

        // Print a newline if the last row is incomplete
        if (units.size() % unitsPerRow != 0) {
            System.out.println();
        }
}


    public void printBench(Player player) {
        List<Card> bench = player.getBench();
        int count = player.getBenchCount();  // only slots with cards

        System.out.println(" Bench:");

        for (int i = 0; i < count; i++) {
            String name = (bench.get(i) != null) 
                ? Utils.formatEnumName(bench.get(i).getType()) 
                : "";
            System.out.printf("\t%d: %s\t", i + 1, name);

            // After every 5 items, print a new line
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }

        // Optional: print newline if last row wasn't complete
        if (count % 5 != 0) {
            System.out.println();
        }
    }


    // Helper to center text
    private String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text + " ".repeat(Math.max(0, width - text.length() - padding));
    }

    // Helper to pad text to fixed width
    private String padText(String text, int width) {
        return String.format("%-" + width + "s", text);
    }
}


