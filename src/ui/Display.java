package ui;

import cards.Card;
import main.Game;
import main.Player;
import units.Unit;
import utils.AudioPlayer;
import utils.Utils;
import java.util.List;
import java.util.Random;
import boss.Boss;
import cards.Shop;

public class Display {

    private Game game;
    private int width = 80;
    public Display() {}
    public Display(Game g) { this.game = g; }

    public void launch(){
        AudioPlayer.playSFX("launch.wav");
        String text = "9 Elixer Presents";
        int colorLength = "9 Elixer".length(); // Only this part will be purple

        Random random = new Random();
        char[] display = new char[text.length()];

        int totalDuration = 5000; // 5 seconds
        int steps = text.length();
        int delay = totalDuration / steps;

        // Initially fill with random glitch characters
        for (int i = 0; i < display.length; i++) {
            display[i] = (char) (33 + random.nextInt(94));
        }

        for (int i = 0; i < text.length(); i++) {
            int glitchStep = 5; // number of flickers per character before settling

            for (int g = 0; g < glitchStep; g++) {
                // Keep revealed letters correct
                for (int j = 0; j <= i; j++) {
                    display[j] = text.charAt(j);
                }

                // Unrevealed letters glitch
                for (int j = i + 1; j < display.length; j++) {
                    display[j] = (char) (33 + random.nextInt(94));
                }

                // Build string: color first part, rest normal
                String coloredPart = Utils.color(new String(display, 0, colorLength), Utils.PURPLE);
                String restPart = new String(display, colorLength, text.length() - colorLength);

                System.out.print("\r" + coloredPart + restPart);
                Utils.delay(delay / glitchStep);
            }

            // After flickers, reveal the actual character
            display[i] = text.charAt(i);
        }

        // Final stable print
        String finalColored = Utils.color(new String(display, 0, colorLength), Utils.PURPLE);
        String finalRest = new String(display, colorLength, text.length() - colorLength);
        System.out.println("\r" + finalColored + finalRest);
    }

    public void titleScreen(){
        AudioPlayer.playBG("titlescreen.wav");
        System.out.println("┌" + "─".repeat(width) + "┐");
        System.out.println("│" + center("S Y N C H O N E V O   C L A S H", width) + "│");
        System.out.println("├" + "─".repeat(width) + "┤");
        System.out.println();

        System.out.println(center("[1] Start", width));
        System.out.println(center("[2] How to Play", width));
        System.out.println(center("[3] Credit", width));
        System.out.println(center("[4] Exit", width));
        System.out.println();

        System.out.println("┌" + "─".repeat(width) + "┐");
        System.out.println(center("A turn-based merge & summon strategy auto-battler", width));
        System.out.println("├" + "─".repeat(width) + "┤");
    }

    public void showHowToPlay() {
        Utils.clearScreen();
        System.out.println("\n┌" + "─".repeat(width) + "┐");
        System.out.println(center("HOW TO PLAY", width));
        System.out.println("├" + "─".repeat(width) + "┤");


        System.out.println("1. Recruiting Units");
        System.out.println(" - Spend gold to purchase units from the shop.");
        System.out.println(" - Units appear at random each round.");
        System.out.println(" - You may place units on either the board (for battle) or the bench (storage).");
        System.out.println();

        System.out.println("2. Deploying Units");
        System.out.println(" - Only units placed on the board will fight.");
        System.out.println(" - You have limited board slots. Choose your strongest units.");
        System.out.println(" - Move units between board and bench anytime before battle.");
        System.out.println();

        System.out.println("3. Auto-Merging Units");
        System.out.println(" - Three identical units automatically merge into a stronger unit:");
        System.out.println(" - Merges can occur from:");
        System.out.println("     * Bench + Bench + Bench");
        System.out.println("     * Board + Board + Board");
        System.out.println("     * Board + Bench + Bench");
        System.out.println(" - Upgraded units gain increased stats.");
        System.out.println();

        System.out.println("4. Selling Units");
        System.out.println(" - You may sell a unit from your board or bench.");
        System.out.println(" - Selling returns half of the unit's gold cost.");
        System.out.println();

        System.out.println("5. Starting a Battle");
        System.out.println(" - Once ready, choose the option to begin the battle.");
        System.out.println(" - Units fight automatically based on their stats.");
        System.out.println(" - Bosses become stronger as the game progresses.");
        System.out.println();

        System.out.println("6. Winning and Losing");
        System.out.println(" - Defeat the boss to win the round.");
        System.out.println(" - If all your units die, you lose the round.");
        System.out.println("\n\n" + center("[Enter to Return]", width));

        System.out.println("\n└" + "─".repeat(width) + "┘");
    }

    public void showCredits() {
    Utils.clearScreen();
    System.out.println("┌" + "─".repeat(width) + "┐");
    System.out.println(center("PROJECT CREDITS", width));
    System.out.println("└" + "─".repeat(width) + "┘");
    System.out.println();

    System.out.println(" \t\tLead Programmer:        Justine Catapang");
    System.out.println(" \t\tGame Mechanics:         Elijah Oreste");
    System.out.println(" \t\tTester / Bug Fixer:     Zaireh Macatangay");

    System.out.println("\n\n" + center("[Enter to Return]", width));


    System.out.println();
    System.out.println("┌" + "─".repeat(width) + "┐");
    System.out.println(center("Thank you for playing Synchonevo Clash!", width));
    System.out.println("└" + "─".repeat(width) + "┘");
}

    public void mainUI(Shop shop, Player player, Unit unit, Boss boss, int stage) {
        Utils.clearScreen();
        Unit[] p = player.getBoard(); // shorthand

        System.out.println("┌" + "─".repeat(width) + "┐");
        System.out.println("│" + center("Synchonevo Clash", width) + "│");
        System.out.println("├" + "─".repeat(width) + "┤");

        /*  Boss
        if (boss != null) {
            System.out.println(center(boss.getBossName(), width));
            System.out.println(center("HP: " + boss.getBossHp() + "/" + boss.getBossMaxHp(), width));
        } else {
            System.out.println(center("Stage: " + String.valueOf(stage), width));
            System.out.println(center("[Boss]", width));
            System.out.println(center("", width));
        }
        */
        System.out.println(" ".repeat(width));

        // 3x3 Board 
        System.out.println(
            "\t" + Utils.CenterCell(name(p[0]), false) + "\t"
                + Utils.CenterCell(name(p[1]), false) + "\t"
                + Utils.CenterCell(name(p[2]), false)
        );

        System.out.println(
            "\t" + Utils.CenterCell(name(p[3]), false) + "\t"
                + Utils.CenterCell(name(p[4]), false) + "\t"
                + Utils.CenterCell(name(p[5]), false)
        );

        System.out.println(
            "\t" + Utils.CenterCell(name(p[6]), false) + "\t"
                + Utils.CenterCell(name(p[7]), false) + "\t"
                + Utils.CenterCell(name(p[8]), false)
        );


        
        System.out.println("\n┌" + "─".repeat(width) + "┐");
        shop.display(player);

        System.out.println("\n├" + "─".repeat(width) + "┤");
        printBench(player);

        System.out.println("\n├" + "─".repeat(width) + "┤");
        
    }

    public void printBench(Player player) {
        List<Card> bench = player.getBench();
        int count = player.getBenchCount();

        System.out.println(" Bench:");
        for (int i = 0; i < count; i++) {
            int star = bench.get(i).getStar();
            String name = (bench.get(i) != null) ? bench.get(i).getName() : "";
            System.out.printf("\t%d: %s [★ %d]\t", i + 1, name, star);

            if ((i + 1) % 3 == 0) System.out.println();
        }
        if (count % 3 != 0) System.out.println();
    }

    /** Display board + boss + one attack log for battle */
    public void displayBattleUI(Boss boss, Unit[] board, String log) {
        Utils.clearScreen();
        AudioPlayer.stopBG();
        System.out.println("┌" + "─".repeat(width) + "┐");
        System.out.println("│" + center("Synchonevo Clash", width) + "│");
        System.out.println("├" + "─".repeat(width) + "┤");

        // Boss
        System.out.println(Utils.color(center(boss.getBossName(), width), Utils.BRIGHT_PURPLE));
        System.out.println(Utils.color(center("HP: " + boss.getBossHp() + "/" + boss.getBossMaxHp(), width),Utils.RED));
        System.out.println(" ".repeat(width));

        // Board 3x3
        // 3x3 Board Hardcoded with HP under each unit
        System.out.println("\t" + cell(board[0]) + "\t" + cell(board[1]) + "\t" + cell(board[2]));
        System.out.println();

        System.out.println("\t" + cell(board[3]) + "\t" + cell(board[4]) + "\t" + cell(board[5]));
        System.out.println();

        System.out.println("\t" + cell(board[6]) + "\t" + cell(board[7]) + "\t" + cell(board[8]));
        System.out.println();

        System.out.println("┌" + "─".repeat(width) + "┐");
        System.out.println(" Battle Log");
        System.out.println(" " + log);
        System.out.println("└" + "─".repeat(width) + "┘");

        Utils.delay(2000);
    }

    private String cell(Unit u) {
        if (u == null) return "                ";             // empty → no brackets
        if(!u.isAlive()) return Utils.color(Utils.CenterCell(name(u), false), Utils.RED);   // print red if the unit died
        return Utils.color(Utils.CenterCell(name(u), false), Utils.GREEN);                  // green
    }

    private String name(Unit u) {
        return (u == null ? "               " : u.displayName());
    }

    public String center(String text, int width) {
        int pad = (width - text.length()) / 2;
        return " ".repeat(pad) + text + " ".repeat(width - text.length() - pad);
    }   
}
