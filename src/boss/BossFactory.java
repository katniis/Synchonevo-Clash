package boss;

import java.util.Random;

public class BossFactory {

    private static Random rand = new Random();

    public static Boss getBossByStage(int stage) {
        int speed = rand.nextInt(4, 10);
        switch (stage) {
            case 1: return new Boss("Death King", 150, 8, speed);
            case 2: return new Boss("Ice Queen", 300, 6, speed);
            case 3: return new Boss("Balrog",350 , 10, speed);
            case 4: return new Boss("Death Gorgon", 370, 8, speed);
            case 5: return new Boss("Hydra", 500, 10, speed);

            case 6: return new Boss("Zaikan", 650, 7, speed);
            case 7: return new Boss("Hell Maine", 725, 10, speed);
            case 8: return new Boss("Dark Phoenix", 750, 9, speed);
            case 9: return new Boss("Genocider", 800, 8, speed);
            case 10: return new Boss("Berserker", 900, 4, speed);

            case 11: return new Boss("Grudge Demonlord", 925, 5, speed);
            case 12: return new Boss("Aragog", 950, 10, speed);
            case 13: return new Boss("Selupan", 800, 10, speed);
            case 14: return new Boss("Thunder Napin", 975, 9, speed);
            case 15: return new Boss("Crystal Deer", 1000, 7, speed);

            case 16: return new Boss("Arrogant Fiend", 1025, 8, speed);
            case 17: return new Boss("Hell Warlord", 1100, 4, speed);
            case 18: return new Boss("Hell Knight", 1400, 5, speed);     // tanky, slow
            case 19: return new Boss("Hell Wraith", 1100, 9, speed);     // glass cannon
            case 20: return new Boss("Sanguine Guard", 1500, 7, speed);  // wall

            case 21: return new Boss("Tectus", 1700, 4, speed);          // very slow tank
            case 22: return new Boss("Feran Lord", 1300, 10, speed);     // very fast DPS
            case 23: return new Boss("Demon Envoy", 1800, 7, speed);     // balanced boss
            case 24: return new Boss("Swamp King", 2000, 5, speed);      // wall boss
            case 25: return new Boss("Nyx", 1600, 9, speed);             // speed burst

            case 26: return new Boss("Void Tyrant", 2100, 6, speed);
            case 27: return new Boss("Iron Skull Crusher", 2300, 4, speed);
            case 28: return new Boss("Abyssal Warlock", 1900, 10, speed);
            case 29: return new Boss("Shadow Colossus", 2400, 7, speed);
            case 30: return new Boss("Flame Overlord", 2600, 5, speed);

            case 31: return new Boss("Storm Harbinger", 2200, 9, speed);
            case 32: return new Boss("Blood Reaper", 2800, 6, speed);
            case 33: return new Boss("Venom Serpentra", 2500, 8, speed);
            case 34: return new Boss("Frost Revenant", 3000, 5, speed);
            case 35: return new Boss("Cursed Minotaur", 3200, 4, speed);

            case 36: return new Boss("Doomwatcher", 2800, 9, speed);
            case 37: return new Boss("Arcane Chimera", 3400, 6, speed);
            case 38: return new Boss("Inferno Monarch", 3100, 8, speed);
            case 39: return new Boss("Bone Centurion", 3600, 5, speed);
            case 40: return new Boss("Soul Devourer", 3300, 7, speed);

            case 41: return new Boss("Blight Behemoth", 3800, 4, speed);
            case 42: return new Boss("Nightshade Specter", 3400, 10, speed);
            case 43: return new Boss("Thunder Ravager", 3900, 6, speed);
            case 44: return new Boss("Obsidian Titan", 4200, 5, speed);
            case 45: return new Boss("Gorefiend Marauder", 3700, 9, speed);

            case 46: return new Boss("Ancient Wraith Mother", 4400, 6, speed);
            case 47: return new Boss("Darksteel Juggernaut", 4600, 4, speed);
            case 48: return new Boss("Plague Bringer", 4100, 8, speed);
            case 49: return new Boss("Ember Basilisk", 4500, 7, speed);
            case 50: return new Boss("Tyrant Cerberion", 4800, 10, speed);

            default:
                return new Boss("Tyrant Cerberion", 4800, 10, speed);
        }
    }
}
