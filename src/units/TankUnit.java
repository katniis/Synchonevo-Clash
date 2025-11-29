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
        return String.format("%s smashes %s for %s damage.", getName(), boss.getBossName(), Utils.color(String.valueOf(dmg), Utils.RED));
    }
}
