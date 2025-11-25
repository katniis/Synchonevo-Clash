package main;

import cards.*;
import units.*;
import boss.*;
import ui.Display;
import utils.Utils;

import java.util.*;

public class Game {
    private Shop shop;
    private Player player;
    private Display display;
    private Boss boss;
    private int stage = 1;
    private String battleLog = "";  // current attack log
    private boolean gameover = false;  

    public Game() {
        shop = new Shop(stage);
        player = new Player("Player");
        display = new Display(this);
    }

    public int getStage() { return stage; }

    /** Spawn boss for current stage */
    private void spawnBoss() {
        boss = BossFactory.getBossByStage(stage); 
    }

    /** Main game loop */
    public void start() {
        Utils.clearScreen();
        System.out.println("Welcome to Synchonevo Clash (CLI)");

        while (!gameover) {
            Utils.delay(500);
            Utils.clearScreen();
            display.mainUI(shop, player, null, boss, stage);
            System.out.println(" Choose: [1] Buy   [2] Roll   [3] Deploy   [4] Battle   [0] Exit");
            int choice = Utils.promptInt(" " + player.getName() + ": ", 0, 4);
            
            try {
                if (choice == 1) {
                    int slot = Utils.promptInt(" Buy which slot [1-4]:", 1, 4);

                    // check IF the player can even afford the card
                    Card card = shop.peek(slot);  
                    if (card == null) {
                        System.out.println(" Invalid slot!");
                        continue;
                    }

                    if (player.getGold() < card.getCost()) {
                        System.out.println(" Not enough gold!");
                        continue; // Do NOT roll or buy
                    }

                    // Player CAN afford - now perform actual buy
                    card = shop.buy(slot, player.getGold()); // now allowed to roll

                    if (player.addToBench(card)) {
                        player.spendGold(card.getCost());
                        System.out.println(" " + card.getName() + " added to bench!");
                    } else {
                        System.out.println(" Bench full! Card discarded.");
                    }
                } else if (choice == 2) {
                    int cost = 2;
                    if (player.getGold() < cost) {
                        System.out.println(" Not enough gold to reroll.");
                    } else {
                        player.spendGold(cost);
                        shop.roll(stage);
                    }

                } else if (choice == 3) {
                    System.out.println(" [1] Deploy   [2] Move   [3] Swap");
                    int t = Utils.promptInt(" Select action:", 1, 3);

                    if (t == 1) {
                        int b = Utils.promptInt(" Bench index:", 1, player.getBenchCount());
                        int p = Utils.promptInt(" Board position [1-9]:", 1, 9);
                        if (!player.deploy(b - 1, p)) System.out.println(" Deploy failed!");
                    } else if (t == 2) {
                        int f = Utils.promptInt(" Move FROM [1-9]:", 1, 9);
                        int to = Utils.promptInt(" Move TO [1-9]:", 1, 9);
                        if (!player.move(f, to)) System.out.println(" Move failed!");
                    }else if (t == 3) {
                        System.out.println(" Swap Options: ");
                        System.out.println(" 1. From BOARD");
                        System.out.println(" 2. From BENCH");
                        int origin = Utils.promptInt(" Choose origin:", 1, 2);

                        if (origin == 1) {  
                            // =============================
                            // FROM BOARD
                            // =============================
                            int fromBoard = Utils.promptInt(" Select board slot [1-9]:", 1, 9);

                            System.out.println(" Move to:");
                            System.out.println(" 1. Board (Swap)");
                            System.out.println(" 2. Bench (Place Back)");
                            int target = Utils.promptInt(" Choose:", 1, 2);

                            if (target == 1) {
                                int toBoard = Utils.promptInt(" Swap with board slot [1-9]:", 1, 9);
                                if (!player.swapBoardToBoard(fromBoard, toBoard)) {
                                    System.out.println(" Swap failed!");
                                }
                            } else {
                                if (!player.moveBoardToBench(fromBoard)) {
                                    System.out.println(" Move failed! (Bench full?)");
                                }
                            }

                        } else {
                            // =============================
                            // FROM BENCH
                            // =============================
                            int fromBench = Utils.promptInt(" Select bench slot:", 1, 9);

                            System.out.println(" Move to:");
                            System.out.println(" 1. Board (Place / Swap)");
                            System.out.println(" 2. Bench (Swap)");
                            int target = Utils.promptInt(" Choose:", 1, 2);

                            if (target == 1) {
                                int toBoard = Utils.promptInt(" Select board slot [1-9]:", 1, 9);
                                if (!player.moveBenchToBoard(fromBench, toBoard)) {
                                    System.out.println(" Move failed!");
                                }
                            } else {
                                int toBench = Utils.promptInt(" Swap with bench slot:", 1, 9);
                                if (!player.swapBenchToBench(fromBench, toBench)) {
                                    System.out.println(" Swap failed!");
                                }
                            }
                        }
                    }
                } else if (choice == 4) {
                    if (boss == null || !boss.bossIsAlive()) spawnBoss();
                    startBattle();
                } else {
                    System.out.println(" Goodbye.");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /** Battle system with turn-by-turn log */
    public void startBattle() {
        Unit[] board = player.getBoard();
        System.out.println("Battle Started! Boss: " + boss.getBossName());

        while (boss.bossIsAlive() && Arrays.stream(board).anyMatch(u -> u != null && u.isAlive())) {
            // Determine attack order by speed
            List<Unit> unitsInOrder = new ArrayList<>();
            for (Unit u : board) if (u != null && u.isAlive()) unitsInOrder.add(u);
            unitsInOrder.sort((a, b) -> b.getSpeed() - a.getSpeed());

            boolean bossFirst = boss.getBossSpeed() > (unitsInOrder.isEmpty() ? 0 : unitsInOrder.get(0).getSpeed());

            if (bossFirst) {
                // Boss attacks first
                battleLog = bossAttackLog(board);
                displayBattle(board);
                Utils.delay(800);

                for (Unit u : unitsInOrder) {
                    battleLog = u.attack(boss);
                    displayBattle(board);
                    Utils.delay(800);
                }

            } else {
                // Units attack first
                for (Unit u : unitsInOrder) {
                    battleLog = u.attack(boss);
                    displayBattle(board);
                    Utils.delay(800);
                }

                battleLog = bossAttackLog(board);
                displayBattle(board);
                Utils.delay(800);
            }
        }

        // Battle outcome
        if (!boss.bossIsAlive()) {
            battleLog = "You defeated the boss!";
            displayBattle(board);
            System.out.println();
            player.addGold(5 + (stage * 2));
            Utils.delay(1000);
            stage++;
        } else {
            battleLog = "Your units have been defeated!";
            Utils.clearScreen();
            System.out.println("Game Over");
            gameover = true;
            Utils.delay(1000);
        }
    }

    /** Display board + boss + battle log */
    private void displayBattle(Unit[] board) {
        Utils.clearScreen();
        display.displayBattleUI(boss, board, battleLog);
    }


    /** Boss attack with patterns targeting alive units/rows */
    private String bossAttackLog(Unit[] board) {
        Random rng = new Random();
        int pattern = rng.nextInt(4); // 0: single, 1: front, 2: middle, 3: back
        int dmg = boss.bossAttack();
        StringBuilder log = new StringBuilder(boss.getBossName() + " attacks: ");

        switch (pattern) {
            case 0: // single random alive unit
                List<Unit> alive = new ArrayList<>();
                for (Unit u : board) if (u != null && u.isAlive()) alive.add(u);
                if (!alive.isEmpty()) {
                    Unit target = alive.get(rng.nextInt(alive.size()));
                    target.takeDamage(dmg);
                    log.append(target.displayName())
                       .append(" for ").append(dmg)
                       .append(" damage. HP: [").append(target.getHp()).append("|").append(target.getMaxHp() + "]");
                } else {
                    log.append("No targets alive.");
                }
                break;

            case 1: case 2: case 3: // rows
                int[] rowStart = {0, 0, 3, 6}; // maps pattern to row start index
                int start = rowStart[pattern];
                int end = start + 3;
                List<Unit> rowUnits = new ArrayList<>();
                for (int i = start; i < end; i++) if (board[i] != null && board[i].isAlive()) rowUnits.add(board[i]);

                if (!rowUnits.isEmpty()) {
                    log.append(rowName(pattern)).append(": ");
                    for (Unit u : rowUnits) {
                        u.takeDamage(dmg);
                        log.append(u.displayName()).append(" HP: ").append(u.getHp()).append("; ");
                    }
                } else {
                    // Row empty, attack random alive unit
                    List<Unit> aliveUnits = new ArrayList<>();
                    for (Unit u : board) if (u != null && u.isAlive()) aliveUnits.add(u);
                    if (!aliveUnits.isEmpty()) {
                        Unit target = aliveUnits.get(rng.nextInt(aliveUnits.size()));
                        target.takeDamage(dmg);
                        log.append(target.displayName())
                           .append(" for ").append(dmg)
                           .append(" damage. HP: [").append(target.getHp()).append("|").append(target.getMaxHp() + "]");
                    } else {
                        log.append("No targets alive.");
                    }
                }
                break;
        }

        return log.toString();
    }

    private String rowName(int pattern) {
        switch (pattern) {
            case 1: return "Front row";
            case 2: return "Middle row";
            case 3: return "Back row";
            default: return "Unknown row";
        }
    }
}
