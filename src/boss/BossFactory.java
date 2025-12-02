package boss;

import java.util.Random;

public class BossFactory {

    private static Random rand = new Random();

    public static Boss getBossByStage(int stage) {
        int speed = rand.nextInt(4, 10);
        int hp = 500 + (stage - 1) * 300; 
        int atk = 50 + (stage - 1) * 7;    

        String name = switch(stage) {
            case 1 -> "Death King";
            case 2 -> "Ice Queen";
            case 3 -> "Balrog";
            case 4 -> "Death Gorgon";
            case 5 -> "Hydra";
            case 6 -> "Zaikan";
            case 7 -> "Hell Maine";
            case 8 -> "Dark Phoenix";
            case 9 -> "Genocider";
            case 10 -> "Berserker";
            case 11 -> "Grudge Demonlord";
            case 12 -> "Aragog";
            case 13 -> "Selupan";
            case 14 -> "Thunder Napin";
            case 15 -> "Crystal Deer";
            case 16 -> "Arrogant Fiend";
            case 17 -> "Hell Warlord";
            case 18 -> "Hell Knight";
            case 19 -> "Hell Wraith";
            case 20 -> "Sanguine Guard";
            case 21 -> "Tectus";
            case 22 -> "Feran Lord";
            case 23 -> "Demon Envoy";
            case 24 -> "Swamp King";
            case 25 -> "Nyx";
            case 26 -> "Void Tyrant";
            case 27 -> "Iron Skull Crusher";
            case 28 -> "Abyssal Warlock";
            case 29 -> "Shadow Colossus";
            case 30 -> "Flame Overlord";
            case 31 -> "Storm Harbinger";
            case 32 -> "Blood Reaper";
            case 33 -> "Venom Serpentra";
            case 34 -> "Frost Revenant";
            case 35 -> "Cursed Minotaur";
            case 36 -> "Doomwatcher";
            case 37 -> "Arcane Chimera";
            case 38 -> "Inferno Monarch";
            case 39 -> "Bone Centurion";
            case 40 -> "Soul Devourer";
            case 41 -> "Blight Behemoth";
            case 42 -> "Nightshade Specter";
            case 43 -> "Thunder Ravager";
            case 44 -> "Obsidian Titan";
            case 45 -> "Gorefiend Marauder";
            case 46 -> "Ancient Wraith Mother";
            case 47 -> "Darksteel Juggernaut";
            case 48 -> "Plague Bringer";
            case 49 -> "Ember Basilisk";
            case 50 -> "Tyrant Cerberion";
            default -> "Unknown Boss";
        };

        return new Boss(name, hp, atk, speed);
    }
}
