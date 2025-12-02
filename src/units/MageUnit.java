package units;
import java.util.Random;

import boss.*;
import utils.*;
public class MageUnit extends Unit {
    private final int baseHp;
    private final int baseAtk;
    private Random rand = new Random();

    public MageUnit(String name, int hp, int atk, int speed, double critRate, double critDamage) {
        super(name, "MAGE", hp, atk, speed, critRate, critDamage);
        this.baseHp = hp;
        this.baseAtk = atk;
    }

    @Override
    protected int baseHp() { return baseHp; }

    @Override
    protected int baseAttack() { return baseAtk; }

    @Override
    public String attack(Boss boss) {
        int dmg = rand.nextInt((int) 1.2 * (computeDamage() / 2), computeDamage());
        boss.bossTakeDamage(dmg);
        switch(getName()){
            case "Mage":
                AudioPlayer.playSFX("mage_sfx.wav");
                return String.format("%s casts a fiery bolt %s for %s damage.", getName(), boss.getBossName(), Utils.color(String.valueOf(dmg), Utils.RED));
            case "Warlock":
                AudioPlayer.playSFX("warlock_sfx.wav");
                return String.format("%s unleashes dark energy %s for %s damage.", getName(), boss.getBossName(), Utils.color(String.valueOf(dmg), Utils.RED));
            case "Wyrmlings":
                AudioPlayer.playSFX("wyrmlings_sfx.wav");
                return String.format("%s summons a swarm of magical dragons %s for %s damage.", getName(), boss.getBossName(), Utils.color(String.valueOf(dmg), Utils.RED));
            default:
                return super.attack(boss);
        }
    }
}
