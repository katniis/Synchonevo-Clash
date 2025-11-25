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
    public Display(Game g) { this.game = g; }

    public void mainUI(Shop shop, Player player, Unit unit, Boss boss, int stage) {

        Unit[] p = player.getBoard(); // shorthand
        int width = 80;

        System.out.println("┌" + "─".repeat(width) + "┐");
        System.out.println("│" + center("Synchonevo Clash", width) + "│");
        System.out.println("├" + "─".repeat(width) + "┤");

        // boss
        System.out.println(center("[ BOSS ]", width));
        for(int i=0;i<2;i++)
            System.out.println(" ".repeat(width));

        // 3x3 Board Hardcoded
        System.out.println("\t[ " + name(p[0]) + " ]\t[ " + name(p[1]) + " ]\t[ " + name(p[2]) + " ]\t ");
        System.out.println(" ".repeat(width));
        System.out.println("\t[ " + name(p[3]) + " ]\t[ " + name(p[4]) + " ]\t[ " + name(p[5]) + " ]\t ");
        System.out.println(" ".repeat(width));
        System.out.println("\t[ " + name(p[6]) + " ]\t[ " + name(p[7]) + " ]\t[ " + name(p[8]) + " ]\t ");
        System.out.println(" ".repeat(width));

        System.out.println("┌" + "─".repeat(width) + "┐");
        shop.display(player);

        System.out.println("\n├" + "─".repeat(width) + "┤");
        printBench(player);

        System.out.println("\n├" + "─".repeat(width) + "┤");
        System.out.println("│" + player.getName() + " ".repeat(width - player.getName().length()) + "│");
        System.out.println("└" + "─".repeat(width) + "┘");
    }


    private String name(Unit u) {
        return (u == null ? "             " : u.displayName());
    }

    private String center(String text, int width) {
        int pad = (width - text.length()) / 2;
        return " ".repeat(pad) + text + " ".repeat(width - text.length() - pad);
    }

    public void printBench(Player player) {
        List<Card> bench = player.getBench();
        int count = player.getBenchCount();  // only slots with cards

        System.out.println(" Bench:");

        for (int i = 0; i < count; i++) {
            int star = bench.get(i).getStar();
            String name = (bench.get(i) != null) 
                ? Utils.formatEnumName(bench.get(i).getType()) 
                : "";
            System.out.printf("\t%d: %s [*%d]\t", i + 1, name, star);

            // After every 3 items, print a new line
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }

        // Optional: print newline if last row wasn't complete
        if (count % 3 != 0) {
            System.out.println();
        }
    }
}
