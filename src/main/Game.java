package main;

import cards.*;
import units.*;
import ui.Display;
import utils.Utils;

import java.util.*;

public class Game {
    private Shop shop;
    private Player player;
    private Display display;
    private int stage = 1;
    private Boss boss;

    // board layout 3x3
    private Unit[][] board = new Unit[3][3];
    private int boardLimit = 2; // placement allowed

    // boss list (from user)
    private static final String[] BOSS_NAMES = new String[] {
        "Death King","Ice Queen","Balrog","Death Gorgon","Hydra","Zaikan","Hell Maine","Dark Phoenix","Genocider","Berserker",
        "Grudge Demonlord","Aragog","Selupan","Thunder Napin","Crystal Deer","Arrogant Fiend","Hell Warlord","Hell Knight","Hell Wraith","Sanguine Guard",
        "Tectus","Feran Lord","Demon Envoy","Swamp King","Nyx","Void Tyrant","Iron Skull Crusher","Abyssal Warlock","Shadow Colossus","Flame Overlord",
        "Storm Harbinger","Blood Reaper","Venom Serpentra","Frost Revenant","Cursed Minotaur","Doomwatcher","Arcane Chimera","Inferno Monarch","Bone Centurion","Soul Devourer",
        "Blight Behemoth","Nightshade Specter","Thunder Ravager","Obsidian Titan","Gorefiend Marauder","Ancient Wraith Mother","Darksteel Juggernaut","Plague Bringer","Ember Basilisk","Tyrant Cerberion"
    };

    public Game() {
        shop = new Shop(5);
        player = new Player("Player", 50, 6);
        display = new Display(this);
        spawnBossForStage();
    }

    private void spawnBossForStage() {
        int idx = (stage - 1) % BOSS_NAMES.length;
        String name = BOSS_NAMES[idx];
        int baseHp = 1000 + idx * 100; // unique base
        int baseAtk = 30 + (idx % 5) * 5;
        boss = new Boss(name, baseHp, baseAtk, 6);
        boss.scaleForStage(stage);
    }

    public void start() {
        Utils.clearScreen();
        System.out.println("Welcome to Synchonevo Clash (CLI)");
        mainLoop:
        while (true) {
            display.renderShop(shop, player, stage);
            int choice = Utils.promptInt("Choose: 1-Buy 2-Roll(2g) 3-Place 4-Swap 5-Battle 0-Exit", 0, 5);
            try {
                if (choice == 1) {
                    int slot = Utils.promptInt("Buy which slot (1-5):", 1, 5);
                    Card c = shop.buy(slot, player.getGold());
                    player.spendGold(c.getCost());
                    // bought card always goes to bench first
                    if (!player.addToBench(c)) {
                        System.out.println("Bench full! Card discarded.");
                    } else {
                        // auto-merge after adding
                        player.autoMerge(board);
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
                    int benchIdx = Utils.promptInt("Bench slot to place (1-" + player.getBench().length + "):", 1, player.getBench().length) - 1;
                    if (benchIdx >= player.getBenchCount()) { System.out.println("No card in that bench slot."); continue; }
                    int row = Utils.promptInt("Place row (0-2):", 0, 2);
                    int col = Utils.promptInt("Place col (0-2):", 0, 2);
                    placeFromBenchToBoard(benchIdx, row, col);
                    // after placement try auto-merge
                    player.autoMerge(board);
                } else if (choice == 4) {
                    System.out.println("Swap between board and bench slots.");
                    int br = Utils.promptInt("board row (0-2):", 0, 2);
                    int bc = Utils.promptInt("board col (0-2):", 0, 2);
                    int bi = Utils.promptInt("bench slot (1-" + player.getBench().length + "):", 1, player.getBench().length) - 1;
                    swapBoardBench(br, bc, bi);
                } else if (choice == 5) {
                    boolean win = runBattle();
                    if (win) {
                        System.out.println("You won! Proceeding to next stage.");
                        player.addGold(5);
                        healAllUnits();
                        stage++;
                        if (stage % 5 == 0 && boardLimit < 6) boardLimit++;
                        spawnBossForStage();
                        shop.roll(stage);
                    } else {
                        System.out.println("You lost. Game over.");
                        break mainLoop;
                    }
                } else {
                    System.out.println("Goodbye.");
                    break mainLoop;
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    // place card from bench to board; if occupied, swap (bench receives previous unit as a card)
    private void placeFromBenchToBoard(int benchIdx, int row, int col) {
        Card card = player.getBench()[benchIdx];
        if (card == null) { System.out.println("No card in that slot."); return; }
        int placed = countPlacedUnits();
        if (board[row][col] == null && placed >= boardLimit) { System.out.println("Board placement limit reached."); return; }

        Unit summoned = card.summonUnit();
        if (board[row][col] == null) {
            board[row][col] = summoned;
            player.removeFromBench(benchIdx);
            System.out.println("Placed " + summoned.displayName() + " at (" + row + "," + col + ")");
        } else {
            // swap: convert previous unit to a Card and put into the bench slot we removed from
            Unit prev = board[row][col];
            UnitType ut = mapUnitNameToUnitType(prev.getName());
            Card prevCard = UnitFactory.createCard(ut, prev.getStar(), 1 + (prev.getStar()-1), "Swapped");
            // replace board with summoned, and put prevCard into the bench slot (bench shifts so we insert at benchIdx)
            board[row][col] = summoned;
            player.removeFromBench(benchIdx); // remove the card we used to summon
            // try to insert prevCard into bench at same index (shift right)
            boolean inserted = insertBenchAt(prevCard, benchIdx);
            if (!inserted) {
                // try any bench slot
                inserted = player.addToBench(prevCard);
            }
            if (!inserted) {
                System.out.println("Bench full, swapped unit discarded.");
            } else {
                System.out.println("Swapped and placed " + summoned.displayName() + " at (" + row + "," + col + ")");
            }
        }
    }

    private boolean insertBenchAt(Card c, int idx) {
        // shift right from idx to make space if possible
        Card[] bench = player.getBench();
        int count = player.getBenchCount();
        if (count >= bench.length) return false;
        for (int i = count; i > idx; i--) bench[i] = bench[i-1];
        bench[idx] = c;
        // increase player benchCount via reflection? we don't have access; instead call addToBench then reorder - simpler: call addToBench and if not at end, swap
        // Simpler approach: place at end (since Player doesn't expose insertion). We'll fallback to that.
        return false;
    }

    private UnitType mapUnitNameToUnitType(String name) {
        for (UnitType t : UnitType.values()) {
            String pretty = t.name().replace('_',' ').toLowerCase();
            if (pretty.equals(name.toLowerCase()) || t.name().equalsIgnoreCase(name.replace(' ','_'))) return t;
        }
        return UnitType.DWARF;
    }

    private int countPlacedUnits() { int c=0; for (int r=0;r<3;r++) for (int col=0;col<3;col++) if (board[r][col]!=null && board[r][col].isAlive()) c++; return c; }

    private void swapBoard(int r1,int c1,int r2,int c2){
        Unit a = board[r1][c1];
        Unit b = board[r2][c2];
        board[r1][c1]=b; board[r2][c2]=a;
        System.out.println("Swapped positions ("+r1+","+c1+") and ("+r2+","+c2+")");
    }

    private void swapBoardBench(int br, int bc, int benchIndex) {
        if (benchIndex < 0 || benchIndex >= player.getBench().length) { System.out.println("Invalid bench index"); return; }
        Card benchCard = player.getBench()[benchIndex];
        Unit boardUnit = board[br][bc];
        if (benchCard == null && boardUnit == null) { System.out.println("Nothing to swap."); return; }

        // If bench has a card and board empty -> place card
        if (boardUnit == null && benchCard != null) {
            board[br][bc] = benchCard.summonUnit();
            player.removeFromBench(benchIndex);
            System.out.println("Moved card from bench to board.");
            return;
        }

        // If bench empty and board has unit -> convert unit to card and place into bench slot
        if (benchCard == null && boardUnit != null) {
            UnitType ut = mapUnitNameToUnitType(boardUnit.getName());
            Card prevCard = UnitFactory.createCard(ut, boardUnit.getStar(), 1 + (boardUnit.getStar()-1), "Swapped");
            if (player.addToBench(prevCard)) {
                board[br][bc] = null;
                System.out.println("Moved unit from board to bench.");
            } else {
                System.out.println("Bench full, cannot move unit.");
            }
            return;
        }

        // Both exist -> swap: bench card -> board, board unit -> bench (replace bench slot)
        if (benchCard != null && boardUnit != null) {
            UnitType ut = mapUnitNameToUnitType(boardUnit.getName());
            Card prevCard = UnitFactory.createCard(ut, boardUnit.getStar(), 1 + (boardUnit.getStar()-1), "Swapped");
            board[br][bc] = benchCard.summonUnit();
            // replace bench slot with prevCard
            Card[] bench = player.getBench();
            bench[benchIndex] = prevCard;
            System.out.println("Swapped bench card with board unit.");
        }
    }

    // heal on win
    private void healAllUnits() {
        for (int r=0;r<3;r++) for (int c=0;c<3;c++) if (board[r][c]!=null) board[r][c].healToFull();
    }

    // main battle loop: player units attack boss, boss attacks random row
    private boolean runBattle() {
        List<Unit> actors = new ArrayList<>();
        for (int r=0;r<3;r++) for (int c=0;c<3;c++) if (board[r][c]!=null && board[r][c].isAlive()) actors.add(board[r][c]);
        if (actors.isEmpty()) { System.out.println("No units placed. You automatically lose."); return false; }

        applySynergies();
        actors.add(boss);

        int turn=1;
        while (true) {
            actors.removeIf(u->u==null || !u.isAlive());
            if (!boss.isAlive()) return true;
            boolean anyPlayerAlive=false; for (Unit u:actors) if (u!=boss && u.isAlive()) anyPlayerAlive=true;
            if (!anyPlayerAlive) return false;

            actors.sort((u1,u2)->{
                if (u2.getSpeed()!=u1.getSpeed()) return Integer.compare(u2.getSpeed(), u1.getSpeed());
                if (u2.getStar()!=u1.getStar()) return Integer.compare(u2.getStar(), u1.getStar());
                return Integer.compare(new Random().nextInt(100), new Random().nextInt(100));
            });

            System.out.println("--- Turn " + turn + " ---");
            for (Unit actor : new ArrayList<>(actors)) {
                if (!actor.isAlive()) continue;
                if (actor == boss) {
                    int row = boss.randomRowAttack();
                    System.out.println(boss.displayName() + " uses a row attack on row " + row + "!");
                    for (int c=0;c<3;c++) {
                        Unit target = board[row][c];
                        if (target!=null && target.isAlive()) {
                            String res = boss.attack(target);
                            System.out.println(res);
                        }
                    }
                } else {
                    String res = actor.attack(boss);
                    System.out.println(res);
                }
                if (!boss.isAlive()) break;
            }

            turn++;
        }
    }

    private void applySynergies() {
        Map<String,Integer> counts = new HashMap<>();
        for (int r=0;r<3;r++) for (int c=0;c<3;c++) {
            Unit u = board[r][c];
            if (u!=null && u.isAlive()) counts.put(u.getClassType(), counts.getOrDefault(u.getClassType(),0)+1);
        }
        if (counts.getOrDefault("WARRIOR",0) >= 2) modifyAttackPercent("WARRIOR", counts.get("WARRIOR"));
        if (counts.getOrDefault("MAGE",0) >= 2) modifyAttackPercent("MAGE", counts.get("MAGE"));
        if (counts.getOrDefault("ARCHER",0) >= 2) modifyCritPercent("ARCHER", counts.get("ARCHER"));
        if (counts.getOrDefault("TANK",0) >= 2) modifyHpPercent("TANK", counts.get("TANK"));
    }

    private void modifyAttackPercent(String classType, int count) {
        double bonus = 0;
        if (count==2) bonus = 0.10; if (count>=3) bonus = 0.20;
        for (int r=0;r<3;r++) for (int c=0;c<3;c++) {
            Unit u = board[r][c];
            if (u!=null && u.isAlive() && u.getClassType().equals(classType)) {
                u.attack = (int)Math.round(u.getAttack() * (1.0 + bonus));
            }
        }
    }

    private void modifyCritPercent(String classType, int count) {
        double bonus = 0;
        if (count==2) bonus=0.10; if (count>=3) bonus=0.25;
        for (int r=0;r<3;r++) for (int c=0;c<3;c++) {
            Unit u = board[r][c];
            if (u!=null && u.isAlive() && u.getClassType().equals(classType)) {
                u.critRate = Math.min(0.99, u.critRate + bonus);
            }
        }
    }

    private void modifyHpPercent(String classType, int count) {
        double bonus = 0;
        if (count==2) bonus=0.15; if (count>=3) bonus=0.30;
        for (int r=0;r<3;r++) for (int c=0;c<3;c++) {
            Unit u = board[r][c];
            if (u!=null && u.isAlive() && u.getClassType().equals(classType)) {
                int newMax = (int)Math.round(u.getMaxHp() * (1.0 + bonus));
                u.maxHp = newMax;
                u.hp = Math.min(u.hp, u.maxHp);
            }
        }
    }

    public Unit[][] getBoard() { return board; }
    public Player getPlayer() { return player; }
    public Boss getBoss() { return boss; }
    public int getStage() { return stage; }
    public int getBoardLimit() { return boardLimit; }
}
