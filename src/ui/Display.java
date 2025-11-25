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

    public Display(Game g) { this.game = g; }

    public void mainUI(Shop shop, Player player, Unit unit, Boss boss, int stage) {
        Utils.clearScreen();

        Unit[] p = player.getBoard(); // shorthand

        System.out.println("┌" + "─".repeat(width) + "┐");
        System.out.println("│" + center("Synchonevo Clash", width) + "│");
        System.out.println("├" + "─".repeat(width) + "┤");

        // Boss
        if (boss != null) {
            System.out.println(center(boss.getBossName(), width));
            System.out.println(center("HP: " + boss.getBossHp() + "/" + boss.getBossMaxHp(), width));
        } else {
            System.out.println(center("Stage: " + String.valueOf(stage), width));
            System.out.println(center("[Boss]", width));
            System.out.println(center("", width));
        }

        System.out.println(" ".repeat(width));

        // 3x3 Board Hardcoded
        System.out.println("\t[ " + name(p[0]) + " ]\t[ " + name(p[1]) + " ]\t[ " + name(p[2]) + " ]");
        System.out.println("\t[ " + name(p[3]) + " ]\t[ " + name(p[4]) + " ]\t[ " + name(p[5]) + " ]");
        System.out.println("\t[ " + name(p[6]) + " ]\t[ " + name(p[7]) + " ]\t[ " + name(p[8]) + " ]");

        
        System.out.println("\n┌" + "─".repeat(width) + "┐");
        shop.display(player);

        System.out.println("\n├" + "─".repeat(width) + "┤");
        printBench(player);

        System.out.println("\n├" + "─".repeat(width) + "┤");
        
    }

    private String name(Unit u) {
        return (u == null ? "               " : u.displayName());
    }

    private String center(String text, int width) {
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
        System.out.println("\t[ " + name(board[0]) + " ]\t[ " + name(board[1]) + " ]\t[ " + name(board[2]) + " ]");
        System.out.println();

        System.out.println("\t[ " + name(board[3]) + " ]\t[ " + name(board[4]) + " ]\t[ " + name(board[5]) + " ]");
        System.out.println();

        System.out.println("\t[ " + name(board[6]) + " ]\t[ " + name(board[7]) + " ]\t[ " + name(board[8]) + " ]");
        System.out.println();

        
        System.out.println("┌" + "─".repeat(width) + "┐");
        System.out.println(" Battle Log");
        System.out.println(" " + log);
        System.out.println("└" + "─".repeat(width) + "┘");

        Utils.delay(1500);
    }
}
