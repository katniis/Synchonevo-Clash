package cards;

import java.util.*;
public class Shop {
    private List<Card> slots = new ArrayList<>();
    private int size = 4; // maximum number of cards can be rolled
    private Random rand = new Random();
    private int stage;

    public Shop(int stage) {
        this.stage = stage;
        rollByDefault(stage);
    }

    // roll according to stage chances for stars
    public void roll(int stage) {
        slots.clear();
        for (int i = 0; i < size; i++) {
            UnitType type = randomType();
            int star = chooseStar(stage);
            int cost = 1 + (star - 1); // simple cost mapping
            String desc = "";
            slots.add(UnitFactory.createCard(type, star, cost, desc));
        }
    }

    private void rollByDefault(int stage) {
        slots.clear();
        for (int i = 0; i < size; i++) {
            UnitType type = randomType();
            int star = chooseStar(stage);
            int cost = 1 + (star - 1); // simple cost mapping
            String desc = "";
            slots.add(UnitFactory.createCard(type, star, cost, desc));
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

    public List<Card> getSlots() { return slots; }

    public Card buy(int index, int playerGold) throws Exception {
        if (index < 1 || index > size) throw new Exception("Invalid slot index");
        Card card = slots.get(index - 1);
        if (playerGold < card.getCost()) throw new Exception("Not enough gold");

        // Reset the shop immediately after purchase
        rollByDefault(stage);
        return card;
    }

    public void display() {
        System.out.println(" Shop");
        for (int i = 0; i < size; i++) {
            Card card = slots.get(i);
            if ((i + 1) % 3 == 0){
                System.out.println();
            }
            System.out.printf("\t[%d] %s\t", i + 1, card == null ? " " : card.toString());
        }
    }
}
