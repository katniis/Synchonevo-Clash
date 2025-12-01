package cards;

import units.*;

public class UnitFactory {
    // create a Unit instance by UnitType and star
    public static Unit createUnit(UnitType type, int star) {
        Unit u;
        switch (type) {
            // Warrior
            case DWARF:
                u = new WarriorUnit("Dwarf", 240, 68, 6, 0.05, 1.5);
                break;
            case FIGHTER:
                u = new WarriorUnit("Fighter", 260, 77, 7, 0.06, 1.5);
                break;

            // Tank
            case HEAVY_SWORDMAN:
                u = new TankUnit("Heavy Swordman", 340, 41, 5, 0.03, 1.3);
                break;
            case ELDER_DWARF:
                u = new TankUnit("Elder Dwarf", 320, 45, 6, 0.04, 1.4);
                break;
            case WYVERN:
                u = new TankUnit("Wyvern", 380, 54, 7, 0.05, 1.5);
                break;

            // Mage
            case MAGE:
                u = new MageUnit("Mage", 160, 90, 7, 0.10, 1.7);
                break;
            case WARLOCK:
                u = new MageUnit("Warlock", 170, 98, 6, 0.09, 1.6);
                break;
            case WYRMLINGS:
                u = new MageUnit("Wyrmlings", 180, 110, 8, 0.09, 1.6);
                break;

            // Archer
            case ELF:
                u = new ArcherUnit("Elf", 200, 86, 9, 0.12, 1.6);
                break;
            case HUNTER:
                u = new ArcherUnit("Hunter", 220, 99, 10, 0.13, 1.7);
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
