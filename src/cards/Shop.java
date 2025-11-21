package cards;

import java.util.*;

public class Shop {
    private Card[] slots;
    private int size;
    private Random rand = new Random();

    public Shop(int size) {
        this.size = size;
        this.slots = new Card[size];
        roll(1);
    }

    // roll according to stage chances for stars
    public void roll(int stage) {
        for (int i = 0; i < size; i++) {
            UnitType t = randomType();
            int star = chooseStar(stage);
            int cost = 1 + (star - 1); // simple cost mapping
            String desc = "";
            slots[i] = UnitFactory.createCard(t, star, cost, desc);
        }
    }

    private UnitType randomType() {
        UnitType[] vals = UnitType.values();
        return vals[rand.nextInt(vals.length)];
    }

    private int chooseStar(int stage) {
        double r = rand.nextDouble();
        if (stage >= 15) {
            if (r < 0.10) return 3;
            if (r < 0.35) return 2;
            return 1;
        } else if (stage >= 10) {
            if (r < 0.05) return 3;
            if (r < 0.25) return 2;
            return 1;
        } else if (stage >= 5) {
            if (r < 0.03) return 3;
            if (r < 0.17) return 2;
            return 1;
        } else {
            if (r < 0.01) return 3;
            if (r < 0.06) return 2;
            return 1;
        }
    }

    public Card[] getSlots() { return slots; }

    public Card buy(int index, int playerGold) throws Exception {
        if (index < 1 || index > slots.length) throw new Exception("Invalid slot index");
        Card c = slots[index - 1];
        if (c == null) throw new Exception("Card already bought");
        if (playerGold < c.getCost()) throw new Exception("Not enough gold");
        slots[index - 1] = null;
        return c;
    }

    public void display() {
        System.out.println("=== SHOP ===");
        for (int i = 0; i < slots.length; i++) {
            System.out.printf("%d. %s\n", i+1, slots[i] == null ? "(empty)" : slots[i].toString());
        }
    }
}
