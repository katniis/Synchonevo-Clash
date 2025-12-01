package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cards.Card;
import cards.UnitFactory;
import cards.UnitType;
import units.Unit;
import utils.*;

public class Player {

    private String name;
    private int gold;

    // Bench = Cards waiting
    private List<Card> bench = new ArrayList<>();
    private final int benchSize = 9;
    private final int deckMax = 6;
    
    // Board = actual Units placed on 3×3 grid
    private Unit[] board = new Unit[9];  // INDEX 0–8

    public Player(String name) {
        this.name = name;
        this.gold = 1000;
    }

    // ===================== Bench Handling =====================
    public boolean addToBench(Card card) {
        if (bench.size() >= benchSize) return false;
        bench.add(card);

        autoMerge();  // merge after adding
        return true;
    }

    public Card removeFromBench(int index) {
        if (index < 0 || index >= bench.size()) return null;
        return bench.remove(index);
    }

    public List<Card> getBench() { return bench; }
    public int getBenchCount() { return bench.size(); }
    public boolean hasUnit() {
        for (Unit u : board){
            if(u != null) return true;
        }
        return false;
    }

    // ===================== Board Handling =====================
    public Unit[] getBoard() { return board; }

    /** Deploy from bench slot → board position (1–9) */
    public boolean deploy(int benchIndex, int pos) {
        pos -= 1;
        if (benchIndex < 0 || benchIndex >= bench.size()) return false;
        if (pos < 0 || pos >= 9) return false;
        if (board[pos] != null) return false; // must be empty

        if (getBoardUnitCount() >= deckMax) return false; // enforce 6-unit cap

        Card card = bench.get(benchIndex);
        Unit summoned = card.summonUnit();
        board[pos] = summoned;
        bench.remove(benchIndex);

        autoMerge();  // merge after deployment
        return true;
    }

    public boolean sellUnit(int index){
        if(index < 0 || index >= board.length) return false;
        if(board[index - 1] == null) return false;
        int cost = (int)Math.round(1 + ((board[index - 1].getStar() - 1) * 1.5));
        int refund = Math.round((float) cost / 2);
        board[index - 1] = null;

        addGold(refund);
        return true;
    }

    public boolean sellCard(int indexInput){
        int index = indexInput - 1; 
        
        if (index < 0 || index >= bench.size()) return false;

        Card c = bench.get(index);
        if (c == null) return false;

        int refund = Math.round(c.getCost() / 2f);

        bench.remove(index);     
        addGold(refund);

        return true;
    }

    /** Move a unit from one board tile to another */
    public boolean move(int fromPos, int toPos) {
        fromPos -= 1;
        toPos -= 1;

        if (fromPos < 0 || fromPos >= 9) return false;
        if (toPos < 0 || toPos >= 9) return false;
        if (board[fromPos] == null || board[toPos] != null) return false;

        board[toPos] = board[fromPos];
        board[fromPos] = null;
        return true;
    }

    /** Swaps */
    public boolean swapBoardToBoard(int a, int b) {
    a--; b--;
    if (a < 0 || a >= 9 || b < 0 || b >= 9) return false;
    Unit tmp = board[a];
    board[a] = board[b];
    board[b] = tmp;
    return true;
    }

    public boolean moveBoardToBench(int pos) {
        pos--;
        if (pos < 0 || pos >= 9) return false;
        Unit u = board[pos];
        if (u == null) return false;

        if (bench.size() >= benchSize) return false;

        UnitType type = Utils.stringToUnitType(u.getName());
        int cost = (int)Math.round(1 + ((u.getStar() - 1) * 1.5));
        if (type != null) {
            Card card = UnitFactory.createCard(type, u.getStar(), cost, "");
            bench.add(card);
        } else {
            System.out.println("Error: Could not convert unit name to UnitType.");
        }
        board[pos] = null;
        return true;
    }

    public boolean moveBenchToBoard(int bSlot, int boardPos) {
        bSlot--; 
        boardPos--;

        if (boardPos < 0 || boardPos >= 9) return false;
        if (bSlot < 0 || bSlot >= bench.size()) return false;

        Card card = bench.get(bSlot);
        if (card == null) return false;

        // if board empty → check cap
        if (board[boardPos] == null) {
            if (getBoardUnitCount() >= deckMax) return false; // can't place more than 6 units
            board[boardPos] = card.summonUnit();
            bench.remove(bSlot);
            return true;
        }

        // else swap with existing board unit
        Unit tempUnit = board[boardPos];
        board[boardPos] = card.summonUnit();

        UnitType type = Utils.stringToUnitType(tempUnit.getName());
        int cost = (int)Math.round(1 + ((tempUnit.getStar() - 1) * 1.5));

        if (type != null) {
            Card tempCard = UnitFactory.createCard(type, tempUnit.getStar(), cost, "");
            bench.set(bSlot, tempCard);
        } else {
            System.out.println("Error: Could not convert unit name to UnitType.");
            return false;
        }
        return true;
    }

    public boolean swapBenchToBench(int a, int b) {
        a--; b--;
        if (a < 0 || a >= bench.size() || b < 0 || b >= bench.size()) return false;

        Card tmp = bench.get(a);
        bench.set(a, bench.get(b));
        bench.set(b, tmp);
        return true;
    }

    private int getBoardUnitCount() {
        int count = 0;
        for (Unit u : board) if (u != null) count++;
        return count;
    }

    // Auto Merge Logic
    private void autoMerge() {
        boolean merged;
        do {
            merged = false;

            Map<String, List<Integer>> locations = new HashMap<>();

            // Board units
            for (int i = 0; i < 9; i++) {
                Unit u = board[i];
                if (u != null) {
                    String key = u.getName() + "#" + u.getStar();
                    locations.computeIfAbsent(key, k -> new ArrayList<>()).add(i);
                }
            }

            // Bench cards
            for (int i = 0; i < bench.size(); i++) {
                Card c = bench.get(i);
                String key = c.getName() + "#" + c.getStar();
                locations.computeIfAbsent(key, k -> new ArrayList<>()).add(9 + i);
            }

            // Scan for merges
            for (Map.Entry<String, List<Integer>> entry : locations.entrySet()) {
                List<Integer> idxs = entry.getValue();
                if (idxs.size() >= 3) {
                    String[] parts = entry.getKey().split("#");
                    String name = parts[0];                                     
                    int star = Integer.parseInt(parts[1]);
                    int newStar = star + 1;

                    // Determine if merge has any board unit
                    int boardPos = -1;
                    int benchPos = -1;
                    int removedFromBench = 0;

                    for (int k = 0; k < 3; k++) {
                        int idx = idxs.get(k);
                        if (idx < 9) { // board
                            if (boardPos == -1) boardPos = idx;
                            board[idx] = null;
                        } else { // bench
                            if (benchPos == -1) benchPos = idx - 9;
                            bench.remove(idx - 9 - removedFromBench);
                            removedFromBench++;
                        }
                    }

                    UnitType type = getUnitTypeByName(name);
                    if (type != null) {
                        Unit upgraded = UnitFactory.createUnit(type, newStar);

                        if (boardPos >= 0) {
                            board[boardPos] = upgraded; // board merge
                        } else if (benchPos >= 0) {
                            // all from bench → put upgraded in first bench slot of merged cards
                            Card upgradedCard = UnitFactory.createCard(type, newStar, (int)((newStar - 1) * 1.5), "");
                            bench.add(benchPos, upgradedCard);
                        }
                    }

                    merged = true;
                    break;
                }
            }
        } while (merged);
    }

    private int findEmptyBoardSlot() {
        for (int i = 0; i < 9; i++) if (board[i] == null) return i;
        return -1;
    }

    private UnitType getUnitTypeByName(String name) {
    for (UnitType t : UnitType.values()) {
        // Replace spaces with underscores and compare ignoring case
        if (t.name().equalsIgnoreCase(name.replace(" ", "_"))) {
            return t;
        }
    }
    return null;
}


    // Getters & Setters
    public String getName() { return name; }
    public int getGold() { return gold; }
    public void spendGold(int x) { gold -= x; }
    public void addGold(int x) { gold += x; }
}
