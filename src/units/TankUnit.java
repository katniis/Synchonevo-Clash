package units;
import boss.*;
import utils.*;

public class TankUnit extends Unit {
    private final int baseHp;
    private final int baseAtk;

    public TankUnit(String name, int hp, int atk, int speed, double critRate, double critDamage) {
        super(name, "TANK", hp, atk, speed, critRate, critDamage);
        this.baseHp = hp;
        this.baseAtk = atk;
    }

    @Override
    protected int baseHp() { return baseHp; }

    @Override
    protected int baseAttack() { return baseAtk; }

    @Override
    public String attack(Boss boss) {
        int dmg = computeDamage();
        boss.bossTakeDamage(dmg);
        switch(getName()){
            case "Heavy Swordman":
                AudioPlayer.playSFX("heavyswordman_sfx.wav");
                return String.format("%s crashes down with a heavy strike %s for %s damage.", getName(), boss.getBossName(), Utils.color(String.valueOf(dmg), Utils.RED));
            case "Elder Dwarf":
                AudioPlayer.playSFX("elderdwarf_sfx.wav");
                return String.format("%s smashes the enemy with his hammer %s for %s damage.", getName(), boss.getBossName(), Utils.color(String.valueOf(dmg), Utils.RED));
            case "Wyvern":
                AudioPlayer.playSFX("wyvern_sfx.wav");
                return String.format("%s breathes a scorching flame %s for %s damage.", getName(), boss.getBossName(), Utils.color(String.valueOf(dmg), Utils.RED));
            default:
                return super.attack(boss);
        }
    }
}
