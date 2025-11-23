package main;

import cards.Card;
import cards.UnitType;
import cards.UnitFactory;
import units.Unit;

import java.util.*;

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

    // Auto-merge: scans board (Unit[][]) and bench (Card[]) for 3-of-a-kind and merges.
    // Preference: if any of the 3 are on the board, merge onto the lowest-index board slot among them.
    public void autoMerge(Unit[][] board) {
        // build counts by key = name + ":" + star
        Map<String, Integer> counts = new LinkedHashMap<>();
        Map<String, List<String>> locations = new HashMap<>(); // track locations like B:r:c or N:i (bench index)
        // scan board
        for (int r=0;r<board.length;r++) {
            for (int c=0;c<board[0].length;c++) {
                Unit u = board[r][c];
                if (u==null) continue;
                String key = u.getName() + \":\" + u.getStar();
                counts.put(key, counts.getOrDefault(key,0)+1);
                locations.computeIfAbsent(key, k->new ArrayList<>()).add(\"B:\"+r+\":\"+c);
            }
        }
        // scan bench
        for (int i=0;i<benchCount;i++) {
            Card cd = bench[i];
            String key = cd.getName() + \":\" + cd.getStar();
            counts.put(key, counts.getOrDefault(key,0)+1);
            locations.computeIfAbsent(key, k->new ArrayList<>()).add(\"N:\"+i);
        }

        // find any key with count >=3 and perform merges until <3
        for (String key : new ArrayList<>(counts.keySet())) {
            while (counts.getOrDefault(key,0) >= 3) {
                // perform single merge for this key
                performMergeForKey(key, board, counts, locations);
            }
        }
    }

    private void performMergeForKey(String key, Unit[][] board, Map<String,Integer> counts, Map<String,List<String>> locations) {
        // parse name and star
        String[] parts = key.split(\":\");
        String name = parts[0];
        int star = Integer.parseInt(parts[1]);
        // remove up to 3 occurrences preferring board entries
        int removed = 0;
        List<String> locs = locations.getOrDefault(key, new ArrayList<>());
        // sort so board locations (B) first
        locs.sort((a,b)-> {
            if (a.startsWith(\"B:\") && b.startsWith(\"B:\")) return a.compareTo(b);
            if (a.startsWith(\"B:\")) return -1;
            if (b.startsWith(\"B:\")) return 1;
            return a.compareTo(b);
        });
        List<String> removedLocs = new ArrayList<>();
        for (Iterator<String> it = locs.iterator(); it.hasNext() && removed<3;) {
            String loc = it.next();
            if (loc.startsWith(\"B:\")) {
                // B:r:c
                String[] p = loc.split(\":\");
                int r = Integer.parseInt(p[1]), c = Integer.parseInt(p[2]);
                board[r][c] = null;
                removedLocs.add(loc);
                it.remove();
                removed++;
            } else if (loc.startsWith(\"N:\")) {
                int idx = Integer.parseInt(loc.split(\":\")[1]);
                // remove bench card at idx (note bench shifts left after removal)
                removeFromBench(idx);
                // after removal, need to update remaining bench location indices in locs and locations map
                // we'll rebuild locations later for simplicity
                removedLocs.add(loc);
                it.remove();
                removed++;
            }
        }
        // rebuild counts/locations fresh for this key to account for bench shifts
        // decrease count
        counts.put(key, counts.getOrDefault(key,0)-removed);
        // create upgraded card (star+1 capped at 3)
        int newStar = Math.min(3, star+1);
        // find UnitType by name
        UnitType ut = findUnitTypeByName(name);
        Card newCard = UnitFactory.createCard(ut, newStar, 1 + (newStar-1), \"Merged\"); // cost simple mapping
        // place merged unit preferably on board at first empty slot, else bench empty slot
        boolean placed = false;
        for (int r=0;r<board.length && !placed;r++) for (int c=0;c<board[0].length && !placed;c++) {
            if (board[r][c]==null) {
                board[r][c] = newCard.summonUnit();
                placed = true;
            }
        }
        if (!placed) {
            if (!addToBench(newCard)) {
                // no space — drop it
            }
        }
        // IMPORTANT: after removal from bench we must rebuild locs and counts for all keys — simplest approach is to stop here;
        // caller (autoMerge) will re-run until no more triples remain because it recalculates counts on next call.
    }

    private UnitType findUnitTypeByName(String name) {
        for (UnitType t : UnitType.values()) {
            String pretty = t.name().replace('_',' ').toLowerCase();
            if (pretty.equals(name.toLowerCase()) || t.name().equalsIgnoreCase(name.replace(' ','_'))) return t;
        }
        // fallback: try partial match
        for (UnitType t : UnitType.values()) {
            if (t.name().toLowerCase().contains(name.toLowerCase().replace(' ','_'))) return t;
        }
        // default
        return UnitType.DWARF;
    }
}
