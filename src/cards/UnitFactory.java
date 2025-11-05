package cards;

import units.*;

public class UnitFactory {

    public static Unit createUnit(UnitType type) {
        switch (type) {
            //Warrior Cards
            case DWARF:
                return new WarriorUnit("Warrior","Dwarf", 40, 8, "swings his axe");
            case FIGHTER:
                return new WarriorUnit("Warrior","Fighter", 45, 10, "charges and strikes");
            case HEAVY_SWORDMAN:
                return new WarriorUnit("Warrior","Heavy Swordman", 55, 12, "slashes with his blade");
            //Archer Cards
            case ELF:
                return new ArcherUnit("Archer", "Elf", 28, 9, "fires a piercing arrow");
            case HUNTER:
                return new ArcherUnit("Archer","Hunter", 35, 11, "shoots twin arrows");
            //Mage Cards
            case MAGE:
                return new MageUnit("Mage","Mage", 25, 10, "casts a fireball");
            case WARLOCK:
                return new MageUnit("Mage","Warlock", 30, 14, "unleashes dark energy");
            case WYRMLINGS:
                return new MageUnit("Mage","Wyrmlings", 30, 13, "breathes fire");
            //Tank Cards
            case ELDER_DWARF:
                return new TankUnit("Tank","Elder Dwarf", 60, 12, "smashes with his hammer");
            case WYVERN:
                return new TankUnit("Tank","Wyvern", 50, 16, "dives and crushes the enemy");
            default:
                throw new IllegalArgumentException("Unknown unit type: " + type);
        }
    }
}
