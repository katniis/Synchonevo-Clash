package main;

import java.util.ArrayList;
import java.util.List;

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
        this.gold = 3;
    }

    // ===================== Bench Handling =====================
    public boolean addToBench(Card card) {
        if (bench.size() >= benchSize) return false;
        bench.add(card);

        autoMergeBench();  // merge after adding
        return true;
    }

    public Card removeFromBench(int index) {
        if (index < 0 || index >= bench.size()) return null;
        return bench.remove(index);
    }

    public List<Card> getBench() { return bench; }
    public int getBenchCount() { return bench.size(); }

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

        autoMergeBoard();  // merge after deployment
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
        int cost = (int)((u.getStar() - 1) * 1.5);
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
        int cost = (int)((tempUnit.getStar() - 1) * 1.5);

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
    private void autoMergeBench() {
        boolean merged;
        do {
            merged = false;
            outer: for (int i = 0; i < bench.size(); i++) {
                Card c1 = bench.get(i);
                int count = 1;
                List<Integer> indices = new ArrayList<>();
                indices.add(i);
                // check for same name & star
                for (int j = i + 1; j < bench.size(); j++) {
                    Card c2 = bench.get(j);
                    if (c1.getName().equals(c2.getName()) && c1.getStar() == c2.getStar()) {
                        count++;
                        indices.add(j);
                        if (count == 3) break;
                    }
                }
                if (count >= 3) {
                    // remove 3 cards
                    // remove from back to front to avoid index shift
                    for (int k = indices.size() - 1; k >= 0; k--) {
                        bench.remove((int)indices.get(k));
                    }
                    // add upgraded card
                    Card upgraded = new Card(c1.getName(), c1.getCost(), "", c1.getType(), c1.getStar() + 1);
                    bench.add(upgraded);
                    merged = true;
                    break outer; // restart scan
                }
            }
        } while (merged);
    }

    private void autoMergeBoard() {
        boolean merged;
        do {
            merged = false;
            // check each unit on board
            outer: for (int i = 0; i < 9; i++) {
                Unit u1 = board[i];
                if (u1 == null) continue;

                int count = 1;
                List<Integer> indices = new ArrayList<>();
                indices.add(i);

                // check other units
                for (int j = i + 1; j < 9; j++) {
                    Unit u2 = board[j];
                    if (u2 != null && u1.getName().equals(u2.getName()) && u1.getStar() == u2.getStar()) {
                        count++;
                        indices.add(j);
                        if (count == 3) break;
                    }
                }

                // also check bench for same cards
                for (int j = 0; j < bench.size(); j++) {
                    Card c = bench.get(j);
                    if (u1.getName().equals(c.getName()) && u1.getStar() == c.getStar()) {
                        count++;
                        indices.add(9 + j); // mark bench index offset
                        if (count == 3) break;
                    }
                }

                if (count >= 3) {
                    // remove units/cards involved
                    int removed = 0;
                    for (int idx : indices) {
                        if (idx < 9) {
                            board[idx] = null;
                        } else {
                            bench.remove(idx - 9 - removed);
                            removed++;
                        }
                    }
                    // summon upgraded unit at first board slot of merged units
                    int boardPos = indices.get(0) < 9 ? indices.get(0) : findEmptyBoardSlot();
                    if (boardPos >= 0) {
                        Unit upgraded = UnitFactory.createUnit(getUnitTypeByName(u1.getName()), u1.getStar() + 1);
                        board[boardPos] = upgraded;
                    }
                    merged = true;
                    break outer;
                }
            }
        } while (merged);
    }

    private int findEmptyBoardSlot() {
        for (int i = 0; i < 9; i++) if (board[i] == null) return i;
        return -1;
    }

    private cards.UnitType getUnitTypeByName(String name) {
        for (cards.UnitType t : cards.UnitType.values()) {
            if (name.equals(t.name()) || name.equalsIgnoreCase(t.name())) return t;
        }
        return null;
    }

    // Getters & Setters
    public String getName() { return name; }
    public int getGold() { return gold; }
    public void spendGold(int x) { gold -= x; }
    public void addGold(int x) { gold += x; }
}
