package units;
import boss.*;
import utils.*;

public class ArcherUnit extends Unit {
    private final int baseHp;
    private final int baseAtk;

    public ArcherUnit(String name, int hp, int atk, int speed, double critRate, double critDamage) {
        super(name, "ARCHER", hp, atk, speed, critRate, critDamage);
        this.baseHp = hp;
        this.baseAtk = atk;
    }

    @Override
    protected int baseHp() { return baseHp; }

    @Override
    protected int baseAttack() { return baseAtk; }

    @Override
    public String attack(Boss boss) {
        int dmg = computeDamage() + (int)(getStar() * 1.2);
        boss.bossTakeDamage(dmg);
        switch(getName()){
            case "Elf":
                AudioPlayer.playSFX("elf_sfx.wav");
                return String.format("%s fires an arrows %s for %s damage.", getName(), boss.getBossName(), Utils.color(String.valueOf(dmg), Utils.RED));
            case "Hunter":
                AudioPlayer.playSFX("hunter_sfx.wav");
                return String.format("%s fires a precise shot from his rifle %s for %s damage.", getName(), boss.getBossName(), Utils.color(String.valueOf(dmg), Utils.RED));
            default:
                return super.attack(boss);
        }  
    }
}
