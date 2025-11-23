package units;

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
}
