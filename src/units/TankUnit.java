package units;

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
    public String attack(Unit target) {
        int dmg = computeDamage();
        target.takeDamage(dmg);
        return String.format("%s crushes %s for %d damage.", displayName(), target.displayName(), dmg);
    }
}
