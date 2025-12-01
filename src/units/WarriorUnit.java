package units;
import boss.*;
import utils.*;

public class WarriorUnit extends Unit {
    private final int baseHp;
    private final int baseAtk;

    public WarriorUnit(String name, int hp, int atk, int speed, double critRate, double critDamage) {
        super(name, "WARRIOR", hp, atk, speed, critRate, critDamage);
        this.baseHp = hp;
        this.baseAtk = atk;
    }

    @Override
    protected int baseHp() { return baseHp; }

    @Override
    protected int baseAttack() { return baseAtk; }

    @Override
    public String attack(Boss boss) {
        // flavor text
        int dmg = computeDamage();
        boss.bossTakeDamage(dmg);
        switch(getName()){
            case "Dwarf":
                AudioPlayer.playSFX("dwarf_sfx.wav");
                return String.format("%s swings his axe with brute force %s for %s damage.", getName(), boss.getBossName(), Utils.color(String.valueOf(dmg), Utils.RED));
            case "Fighter":
                AudioPlayer.playSFX("fighter_sfx.wav");
                return String.format("%s slashes swiftly with his sword %s for %s damage.", getName(), boss.getBossName(), Utils.color(String.valueOf(dmg), Utils.RED));
            default:
                return super.attack(boss);
        }
    }
}
