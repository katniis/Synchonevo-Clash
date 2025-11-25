package units;

import java.util.Random;
import boss.*;

public abstract class Unit {
    protected String name;
    protected String classType;     // WARRIOR, MAGE, ARCHER, TANK
    protected int star = 1;         // by default set star = 1
    protected int maxHp;
    protected int hp;
    protected int attack;
    protected int speed;
    protected double critRate;
    protected double critDamage;
    protected boolean alive = true;
    protected int originalIndex = -1; // for merge preference

    protected static final Random RNG = new Random();

    public Unit(String name, String classType, int hp, int attack, int speed, double critRate, double critDamage) {
        this.name = name;
        this.classType = classType;
        this.maxHp = hp;
        this.hp = hp;
        this.attack = attack;
        this.speed = speed;
        this.critRate = critRate;
        this.critDamage = critDamage;
        this.alive = true;
    }

    // Apply star scaling
    public void setStar(int star) {
        if (star < 1) star = 1;
        if (star > 3) star = 3;
        this.star = star;
        // scale stats per spec: 2★ => +30% HP, +20% ATK; 3★ => +60% HP, +40% ATK
        double hpMultiplier = 1.0;
        double atkMultiplier = 1.0;
        if (star == 2) { hpMultiplier = 1.3; atkMultiplier = 1.2; }
        if (star == 3) { hpMultiplier = 1.6; atkMultiplier = 1.4; }

        this.maxHp = (int)Math.round((double)baseHp() * hpMultiplier);
        this.hp = Math.min(this.hp, this.maxHp);
        this.attack = (int)Math.round((double)baseAttack() * atkMultiplier);
    }

    // base values should be provided by subclass if needed
    protected abstract int baseHp();
    protected abstract int baseAttack();

    public String getName() { return name; }
    public String getClassType() { return classType; }
    public int getStar() { return star; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack; }
    public int getSpeed() { return speed; }
    public boolean isAlive() { return alive && hp > 0; }

    public void healToFull() {
        this.hp = this.maxHp;
        this.alive = true;
    }

    public void takeDamage(int dmg) {
        if (!isAlive()) return;
        this.hp -= dmg;
        if (this.hp <= 0) {
            this.hp = 0;
            this.alive = false;
        }
    }

    // Damage
    public int computeDamage() {
        boolean crit = RNG.nextDouble() < critRate;
        int dmg = attack;
        if (crit) dmg = (int)Math.round(dmg * critDamage);
        return dmg;
    }

    // Default attack: target the boss (single target model) - subclasses may override
    public String attack(Boss boss) {
        if (!isAlive()) return name + " is dead and can't attack.";
        int dmg = computeDamage();
        boss.bossTakeDamage(dmg);
        return String.format("%star attacks %star for %d damage.", displayName(), boss.getBossName(), dmg);
    }

    public String displayName() {
        if (!alive) return "skull";
        return name + " *" + star;
    }

    @Override
    public String toString() {
        return String.format("[%star | Class:%star | ⭐%d | HP:%d/%d | ATK:%d | SPD:%d | CR:%.2f | CD:%.2f | Alive:%b]",
            name, classType, star, hp, maxHp, attack, speed, critRate, critDamage, isAlive());
    }
}
