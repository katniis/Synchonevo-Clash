package main;

import cards.*;
import units.*;
import boss.*;
import ui.Display;
import utils.Utils;

public class Game {
    private Shop shop;
    private Player player;
    private Display display;
    private Boss boss;
    private Unit unit;
    private int stage = 1;
    

    public Game() {
        shop = new Shop(stage);
        player = new Player("Player");
        display = new Display(this);
        //spawnBoss();
    }

    public int getStage(){ return stage; }

    /* 
    private void spawnBoss() {
        int hp = 500 + (stage * 200);
        int atk = 20 + (stage * 5);
        boss = new TankUnit("Boss", hp, atk, 6, 0.05, 1.3);
    }
    */

    public void start() {
        Utils.clearScreen();
        System.out.println("Welcome to Synchonevo Clash (CLI)");
        //Utils.delay(1000);
        while (true) {
            Utils.delay(1500);
            Utils.clearScreen();
            display.mainUI(shop, player, unit, boss, stage);
            int choice = Utils.promptInt("Choose: 1-Buy 2-Roll 3-Deploy 4-Battle 0-Exit", 0, 4);
            try {
                if (choice == 1) {
                    int slot = Utils.promptInt("Buy which slot (1-5):", 1, 5);
                    Card card = shop.buy(slot, player.getGold());

                    if(player.addToBench(card)){
                        player.spendGold(card.getCost());
                        System.out.println(card.getName() + " added to bench!");

                    }else{
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
                    System.out.println("1. Deploy");
                    System.out.println("2. Move");
                    System.out.println("3. Swap");
                    int t = Utils.promptInt("Select action:", 1, 3);

                    if (t == 1) {
                        int b = Utils.promptInt("Bench index:", 1, player.getBenchCount());
                        int p = Utils.promptInt("Board position (1–9):", 1, 9);
                        if (!player.deploy(b - 1, p)) System.out.println("Deploy failed!");
                    }
                    else if (t == 2) {
                        int f = Utils.promptInt("Move FROM (1–9):", 1, 9);
                        int to = Utils.promptInt("Move TO (1–9):", 1, 9);
                        if (!player.move(f, to)) System.out.println("Move failed!");
                    }
                    else if (t == 3) {
                        int a = Utils.promptInt("Swap A (1–9):", 1, 9);
                        int b2 = Utils.promptInt("Swap B (1–9):", 1, 9);
                        if (!player.swap(a, b2)) System.out.println("Swap failed!");
                    }
                } else if (choice == 4) {
                    // Battle Here
                } else {
                    System.out.println("Goodbye.");
                    break;
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    public void startBattle(){
        
    }
}
