package cards;

import units.*;

public class UnitFactory {
    // create a Unit instance by UnitType and star
    public static Unit createUnit(UnitType type, int star) {
        Unit u;
        switch (type) {
            case DWARF:
                u = new WarriorUnit("Dwarf", 80, 14, 8, 0.05, 1.5);
                break;
            case ELDER_DWARF:
                u = new TankUnit("Elder Dwarf", 120, 10, 5, 0.03, 1.4);
                break;
            case MAGE:
                u = new MageUnit("Mage", 60, 18, 7, 0.10, 1.7);
                break;
            case WARLOCK:
                u = new MageUnit("Warlock", 70, 20, 6, 0.08, 1.6);
                break;
            case ELF:
                u = new ArcherUnit("ELF", 65, 12, 9, 0.12, 1.6);
                break;
            case HUNTER:
                u = new ArcherUnit("Hunter", 75, 15, 10, 0.13, 1.7);
                break;
            case FIGHTER:
                u = new WarriorUnit("Fighter", 90, 16, 9, 0.07, 1.5);
                break;
            case HEAVY_SWORDMAN:
                u = new TankUnit("Heavy Swordman", 140, 12, 4, 0.02, 1.3);
                break;
            case WYRMLINGS:
                u = new MageUnit("Wyrmlings", 70, 22, 8, 0.08, 1.6);
                break;
            case WYVERN:
                u = new TankUnit("Wyvern", 160, 28, 7, 0.05, 1.5);
                break;
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
        u.setStar(star);
        return u;
    }

    // create a Card wrapper for shop
    public static Card createCard(UnitType type, int star, int cost, String description) {
        return new Card(type.name(), cost, description, type, star);
    }
}
