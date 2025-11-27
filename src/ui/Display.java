package ui;

import cards.Card;
import main.Game;
import main.Player;
import units.Unit;
import utils.Utils;

import java.util.List;
import boss.Boss;
import cards.Shop;

public class Display {

    private Game game;
    private int width = 80;
    public Display() {}
    public Display(Game g) { this.game = g; }

    public void titleScreen(){
        System.out.println("┌" + "─".repeat(width) + "┐");
        System.out.println("│" + center("S Y N C H O N E V O   C L A S H", width) + "│");
        System.out.println("├" + "─".repeat(width) + "┤");
        System.out.println();

        System.out.println(center("New Game", width));
        System.out.println(center("Continue", width));
        System.out.println(center("How to Play", width));
        System.out.println(center("Credit", width));
        System.out.println(center("Exit", width));
        System.out.println();

        System.out.println("┌" + "─".repeat(width) + "┐");
        System.out.println(center("A turn-based merge & summon strategy auto-battler", width));
        System.out.println("├" + "─".repeat(width) + "┤");
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

    private String name(Unit u) {
        return (u == null ? "               " : u.displayName());
    }

    public String center(String text, int width) {
        int pad = (width - text.length()) / 2;
        return " ".repeat(pad) + text + " ".repeat(width - text.length() - pad);
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

        System.out.println("┌" + "─".repeat(width) + "┐");
        System.out.println("│" + center("Synchonevo Clash", width) + "│");
        System.out.println("├" + "─".repeat(width) + "┤");

        // Boss
        System.out.println(center(boss.getBossName(), width));
        System.out.println(center("HP: " + boss.getBossHp() + "/" + boss.getBossMaxHp(), width));
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

        Utils.delay(1500);
    }

    private String cell(Unit u) {
        if (u == null) return "   ";               // empty → no brackets
        return Utils.CenterCell(name(u), false);          // unit → keep bracket
    }   


}
