package ui;

import cards.Shop;
import main.Player;
import main.Game;
import units.Unit;

public class Display {
    private Game game;
    public Display(Game g) { this.game = g; }

    public void renderShop(Shop shop, Player player, int stage) {
        System.out.println("\n======================");
        System.out.printf("Stage %d | Player: %s | Gold: %d | BoardLimit: %d\n", stage, player.getName(), player.getGold(), game.getBoardLimit());
        System.out.println("======================");
        shop.display();
        player.printBench();
        renderBoard(game.getBoard(), game.getBoss());
    }

    private void renderBoard(Unit[][] board, units.Unit boss) {
        System.out.println("--- BOARD ---");
        for (int r=0;r<3;r++) {
            for (int c=0;c<3;c++) {
                Unit u = board[r][c];
                String repr = (u==null) ? "[   ]" : String.format("[%s]", pad(u.displayName(),6));
                System.out.print(repr + " ");
            }
            if (r==1) System.out.print("   BOSS: " + (boss==null?"(none)":boss.displayName()));
            System.out.println();
        }
    }

    private String pad(String s, int w) {
        if (s.length() >= w) return s.substring(0,w);
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < w) sb.append(' ');
        return sb.toString();
    }
}
