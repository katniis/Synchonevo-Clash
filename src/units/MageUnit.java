package units;
import boss.*;
public class MageUnit extends Unit {
    private final int baseHp;
    private final int baseAtk;

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
        // flavor text
        int dmg = computeDamage();
        boss.bossTakeDamage(dmg);
        return String.format("%s casts a spell on %s for %d damage.", displayName(), boss.getBossName(), dmg);
    }
}
