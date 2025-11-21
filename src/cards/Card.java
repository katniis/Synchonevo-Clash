package cards;

import units.Unit;
//import cards.UnitFactory;

public class Card {
    private String name;
    private int cost;
    private String description;
    private UnitType type;
    private int star;

    public Card(String name, int cost, String description, UnitType type, int star) {
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.type = type;
        this.star = star;
    }

    public Unit summonUnit() {
        return UnitFactory.createUnit(type, star);
    }

    public String getName() { return name; }
    public int getCost() { return cost; }
    public UnitType getType() { return type; }
    public int getStar() { return star; }

    @Override
    public String toString() {
        return String.format("%s (★%d) - Cost:%dg - %s", name, star, cost, description);
    }
}
