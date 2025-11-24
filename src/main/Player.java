package main;

import java.util.ArrayList;
import java.util.List;

import cards.Card;
import units.Unit;

public class Player {

    private String name;
    private int gold;

    // Bench = Cards waiting
    private List<Card> bench = new ArrayList<>();
    private int benchSize = 9;

    // Board = actual Units placed on 3×3 grid
    private Unit[] board = new Unit[9];  // INDEX 0–8

    public Player(String name) {
        this.name = name;
        this.gold = 15;
    }

    // Bench Handling
    public boolean addToBench(Card card) {
        if (bench.size() >= benchSize) return false;
        bench.add(card);
        return true;
    }

    public Card removeFromBench(int index) {
        if (index < 0 || index >= bench.size()) return null;
        return bench.remove(index);
    }

    public List<Card> getBench() { return bench; }
    public int getBenchCount() { return bench.size(); }


    // Board Handling (3×3)
    public Unit[] getBoard() { return board; }

    /** Deploy from bench slot → board position (1–9) */
    public boolean deploy(int benchIndex, int pos) {
        pos -= 1;
        if (benchIndex < 0 || benchIndex >= bench.size()) return false;
        if (pos < 0 || pos >= 9) return false;
        if (board[pos] != null) return false; // must be empty

        Card card = bench.get(benchIndex);
        Unit summoned = card.summonUnit();
        board[pos] = summoned;
        bench.remove(benchIndex);

        return true;
    }

    /** Move a unit from one board tile to another */
    public boolean move(int fromPos, int toPos) {
        fromPos -= 1;
        toPos -= 1;

        if (fromPos < 0 || fromPos >= 9) return false;
        if (toPos < 0 || toPos >= 9) return false;

        if (board[fromPos] == null) return false;
        if (board[toPos] != null) return false;

        board[toPos] = board[fromPos];
        board[fromPos] = null;
        return true;
    }

    /** Swap two unit positions */
    public boolean swap(int posA, int posB) {
        posA -= 1;
        posB -= 1;

        if (posA < 0 || posA >= 9) return false;
        if (posB < 0 || posB >= 9) return false;

        Unit temp = board[posA];
        board[posA] = board[posB];
        board[posB] = temp;
        return true;
    }

    public String getName() { return name; }
    public int getGold() { return gold; }
    public void spendGold(int x) { gold -= x; }
    public void addGold(int x) { gold += x; }
}
