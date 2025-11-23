package main;

import cards.Card;
//import units.Unit;

public class Player {
    private String name;
    private int gold;

    // arrays for deck and bench
    private Card[] deck;
    private Card[] bench;
    private int deckCount = 0;
    private int benchCount = 0;

    public Player(String name, int deckSize, int benchSize) {
        this.name = name;
        this.gold = 10;
        this.deck = new Card[deckSize];
        this.bench = new Card[benchSize];
    }

    public boolean addToDeck(Card c) {
        if (deckCount >= deck.length) return false;
        deck[deckCount++] = c;
        return true;
    }

    public boolean addToBench(Card c) {
        if (benchCount >= bench.length) return false;
        bench[benchCount++] = c;
        return true;
    }

    public Card removeFromBench(int index) {
        if (index < 0 || index >= benchCount) return null;
        Card removed = bench[index];
        for (int i = index; i < benchCount - 1; i++) bench[i] = bench[i+1];
        bench[--benchCount] = null;
        return removed;
    }

    public Card[] getBench() { return bench; }
    public int getBenchCount() { return benchCount; }

    public int getGold() { return gold; }
    public void addGold(int amount) { gold += amount; }
    public void spendGold(int amount) { gold -= amount; }

    public String getName() { return name; }

    // debugging
    public void printBench() {
        System.out.println(name + "'s Bench:");
        for (int i = 0; i < bench.length; i++) {
            System.out.printf("%d: %s\n", i+1, (i < benchCount && bench[i] != null) ? bench[i].toString() : "(empty)");
        }
    }
}