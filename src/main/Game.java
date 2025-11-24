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
                    // implement put in positon
                    // implement change position on deck
                    // impleement put back units on deck to bench
                    System.out.println("deplyement");
                    int index = Utils.promptInt("pick number", 1, 9);
                    int position = Utils.promptInt("position", 1, 9);
                    player.deployUnit(index - 1, position);
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
