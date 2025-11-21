package ui;

import cards.Shop;
import main.Player;
import main.Game;

public class Display {
    private Game game;
    public Display(Game g) { this.game = g; }

    public void renderShop(Shop shop, Player player, int stage) {
        System.out.println("\n======================");
        System.out.printf("Stage %d | Player: %s | Gold: %d\n", stage, player.getName(), player.getGold());
        System.out.println("======================");
        shop.display();
        player.printBench();
    }
}
