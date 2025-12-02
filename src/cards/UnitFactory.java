package cards;

import units.*;

public class UnitFactory {
    // create a Unit instance by UnitType and star
    public static Unit createUnit(UnitType type, int star) {
        Unit u;
        switch (type) {
            // Warrior
            case DWARF:
                u = new WarriorUnit("Dwarf", 120, 34, 6, 0.05, 1.5);
                break;
            case FIGHTER:
                u = new WarriorUnit("Fighter", 130, 39, 7, 0.06, 1.5);
                break;

            // Tank
            case HEAVY_SWORDMAN:
                u = new TankUnit("Heavy Swordman", 170, 21, 5, 0.03, 1.3);
                break;
            case ELDER_DWARF:
                u = new TankUnit("Elder Dwarf", 160, 23, 6, 0.04, 1.4);
                break;
            case WYVERN:
                u = new TankUnit("Wyvern", 190, 27, 7, 0.05, 1.5);
                break;

            // Mage
            case MAGE:
                u = new MageUnit("Mage", 80, 45, 7, 0.10, 1.7);
                break;
            case WARLOCK:
                u = new MageUnit("Warlock", 85, 49, 6, 0.09, 1.6);
                break;
            case WYRMLINGS:
                u = new MageUnit("Wyrmlings", 90, 55, 8, 0.09, 1.6);
                break;

            // Archer
            case ELF:
                u = new ArcherUnit("Elf", 100, 43, 9, 0.12, 1.6);
                break;
            case HUNTER:
                u = new ArcherUnit("Hunter", 110, 50, 10, 0.13, 1.7);
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
