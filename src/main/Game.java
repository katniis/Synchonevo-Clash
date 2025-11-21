package main;

import cards.*;
import units.*;
import ui.Display;
import utils.Utils;

public class Game {
    private Shop shop;
    private Player player;
    private Display display;
    private int stage = 1;
    private Unit boss;

    public Game() {
        shop = new Shop(5);
        player = new Player("Player", 50, 6);
        display = new Display(this);
        spawnBoss();
    }

    private void spawnBoss() {
        int hp = 500 + (stage * 200);
        int atk = 20 + (stage * 5);
        boss = new TankUnit("Boss", hp, atk, 6, 0.05, 1.3);
    }

    public void start() {
        Utils.clearScreen();
        System.out.println("Welcome to Synchonevo Clash (CLI)");
        while (true) {
            display.renderShop(shop, player, stage);
            int choice = Utils.promptInt("Choose: 1-Buy 2-Roll 3-Deploy 4-Battle 0-Exit", 0, 4);
            try {
                if (choice == 1) {
                    int slot = Utils.promptInt("Buy which slot (1-5):", 1, 5);
                    Card c = shop.buy(slot, player.getGold());
                    player.spendGold(c.getCost());
                    // bought card always goes to bench first
                    if (!player.addToBench(c)) {
                        System.out.println("Bench full! Card discarded.");
                    }
                } else if (choice == 2) {
                    int cost = 2;
                    if (player.getGold() < cost) {
                        System.out.println("Not enough gold to reroll.");
                    } else {
                        player.spendGold(cost);
                        shop.roll(stage);
                    }
                } else if (choice == 3) {
                    player.printBench();
                    System.out.println("Deploy from bench to board not implemented in this demo.");
                } else if (choice == 4) {
                    // simple battle simulation vs boss
                    BattleSim sim = new BattleSim(player, boss);
                    boolean win = sim.runBattle();
                    if (win) {
                        System.out.println("You won! Proceeding to next stage.");
                        player.addGold(5);
                        // heal all units if any (bench units are cards; real units would be on board)
                        stage++;
                        spawnBoss();
                        shop.roll(stage);
                    } else {
                        System.out.println("You lost. Game over.");
                        break;
                    }
                } else {
                    System.out.println("Goodbye.");
                    break;
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}
