package boss;

public class BossFactory {

    public static Boss getBossByStage(int stage) {
        switch (stage) {

            case 1: return new Boss("Death King", 6000, 120);
            case 2: return new Boss("Ice Queen", 5500, 110);
            case 3: return new Boss("Balrog", 7000, 150);
            case 4: return new Boss("Death Gorgon", 5800, 130);
            case 5: return new Boss("Hydra", 6500, 140);

            case 6: return new Boss("Zaikan", 7200, 160);
            case 7: return new Boss("Hell Maine", 7600, 165);
            case 8: return new Boss("Dark Phoenix", 6200, 130);
            case 9: return new Boss("Genocider", 6900, 150);
            case 10: return new Boss("Berserker", 6800, 155);

            case 11: return new Boss("Grudge Demonlord", 7400, 160);
            case 12: return new Boss("Aragog", 5700, 115);
            case 13: return new Boss("Selupan", 6600, 135);
            case 14: return new Boss("Thunder Napin", 6000, 120);
            case 15: return new Boss("Crystal Deer", 6300, 125);

            case 16: return new Boss("Arrogant Fiend", 6800, 145);
            case 17: return new Boss("Hell Warlord", 7700, 170);
            case 18: return new Boss("Hell Knight", 7500, 168);
            case 19: return new Boss("Hell Wraith", 5800, 135);
            case 20: return new Boss("Sanguine Guard", 7000, 145);

            case 21: return new Boss("Tectus", 8000, 175);
            case 22: return new Boss("Feran Lord", 6900, 140);
            case 23: return new Boss("Demon Envoy", 7100, 150);
            case 24: return new Boss("Swamp King", 6500, 130);
            case 25: return new Boss("Nyx", 5400, 125);

            case 26: return new Boss("Void Tyrant", 8200, 180);
            case 27: return new Boss("Iron Skull Crusher", 7300, 155);
            case 28: return new Boss("Abyssal Warlock", 6000, 170);
            case 29: return new Boss("Shadow Colossus", 8400, 150);
            case 30: return new Boss("Flame Overlord", 7200, 165);

            case 31: return new Boss("Storm Harbinger", 6400, 125);
            case 32: return new Boss("Blood Reaper", 6800, 160);
            case 33: return new Boss("Venom Serpentra", 5900, 135);
            case 34: return new Boss("Frost Revenant", 6050, 130);
            case 35: return new Boss("Cursed Minotaur", 7600, 170);

            case 36: return new Boss("Doomwatcher", 8000, 140);
            case 37: return new Boss("Arcane Chimera", 6200, 165);
            case 38: return new Boss("Inferno Monarch", 7700, 185);
            case 39: return new Boss("Bone Centurion", 7300, 150);
            case 40: return new Boss("Soul Devourer", 6900, 160);

            case 41: return new Boss("Blight Behemoth", 8200, 155);
            case 42: return new Boss("Nightshade Specter", 5600, 140);
            case 43: return new Boss("Thunder Ravager", 6300, 125);
            case 44: return new Boss("Obsidian Titan", 9000, 145);
            case 45: return new Boss("Gorefiend Marauder", 7100, 160);

            case 46: return new Boss("Ancient Wraith Mother", 6700, 150);
            case 47: return new Boss("Darksteel Juggernaut", 8800, 170);
            case 48: return new Boss("Plague Bringer", 6200, 130);
            case 49: return new Boss("Ember Basilisk", 5900, 125);
            case 50: return new Boss("Tyrant Cerberion", 8500, 180);

            default:
                // After last boss, loop back OR return strongest boss
                return new Boss("Tyrant Cerberion", 8500, 180);
        }
    }
}