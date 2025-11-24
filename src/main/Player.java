package main;

import java.util.*;

import cards.Card;
import units.Unit;
import utils.Utils;

public class Player {
    private String name;
    private int gold;

    // arrays for deck and bench
    private List<Unit> deck = new ArrayList<>();    // Entity
    private List<Card> bench = new ArrayList<>();   // Template
    private int deckSize = 6;       // Maximum units can be placed in the board
    private int benchSize = 9;     // Maxiumum units player can hold
    private int deckCount = 0;
    private int benchCount = 0;
    private int position = -1;  //default


    public Player(String name) {
        this.name = name;
        this.gold = 15;
    }

    public boolean addToDeck(Unit unit) {
        if (deckCount >= deckSize) return false;
        deck.add(deckCount, unit);
        ++deckCount;
        return true;
    }

    public boolean addToBench(Card card) {
        if (benchCount >= benchSize) return false;
        bench.add(benchCount, card);
        ++benchCount;
        return true;
    }

    public Card removeFromBench(int index) {
        if (index < 0 || index >= benchCount) return null;
        Card removed = bench.remove(index); // automatically shifts elements
        benchCount--;
        return removed;
    }


    public boolean deployUnit(int index, int position){
        
        // Check if bench index is valid
        if(index < 0 || index >= benchCount) return false;
        
        // Check if main board (deck) has space
        if (deckCount >= deckSize) return false;
        
        // get card from bench
        Card card = bench.get(index);
        Unit unit = card.summonUnit();

        // add unit on the deck
        addToDeck(unit);

        // remove the card from the bench
        removeFromBench(index);
        System.out.println(unit.displayName() + " deployed to main board!");
        return true;
    }

    public List<Card> getBench() { return bench; }
    public List<Unit> getDeck() { return deck; }
    public int getBenchCount() { return benchCount; }

    public int getGold() { return gold; }
    public void addGold(int amount) { gold += amount; }
    public void spendGold(int amount) { gold -= amount; }

    public String getName() { return name; }

    public int getDeckSize() { return deckSize; }
    public int getBenchSize() { return benchSize; }
    
    public void setPosition() { return; }
    public int getPosition() {return position; }
}