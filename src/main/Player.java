package main;

import java.util.*;

import cards.Card;
//import units.Unit;

public class Player {
    private String name;
    private int gold;

    // arrays for deck and bench
    private List<Card> deck = new ArrayList<>();
    private List<Card> bench = new ArrayList<>();
    private int deckSize = 6;       // Maximum units can be placed in the board
    private int benchSize = 10;     // Maxiumum units player can hold
    private int deckCount = 0;
    private int benchCount = 0;

    public Player(String name) {
        this.name = name;
        this.gold = 10;
    }

    public boolean addToDeck(Card card) {
        if (deckCount >= deckSize) return false;
        deck.add(deckCount, card);
        deckCount++;
        return true;
    }

    public boolean addToBench(Card card) {
        if (benchCount >= benchSize) return false;
        bench.add(benchCount, card);
        benchCount++;
        return true;
    }

    public Card removeFromBench(int index) {
        if (index < 0 || index >= benchCount) return null;
        Card removed = bench.get(index);
        for (int i = index; i < benchCount - 1; i++) bench.set(i, bench.get(i+1));
        bench.set(benchCount, null);
        benchCount--;
        return removed;
    }

    public List<Card> getBench() { return bench; }
    public int getBenchCount() { return benchCount; }

    public int getGold() { return gold; }
    public void addGold(int amount) { gold += amount; }
    public void spendGold(int amount) { gold -= amount; }

    public String getName() { return name; }

    // debugging
    public void printBench() {
        System.out.println(name + "'s Bench:");
        for (int i = 0; i < benchSize; i++) {
            System.out.printf("%d: %s\n", i+1, (i < benchCount && bench.get(i) != null) ? bench.get(i) : "");
        }
    }
}