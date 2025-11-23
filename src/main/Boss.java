package main;

import units.TankUnit;
import units.Unit;

public class Boss extends TankUnit {
    private String bossName;
    private int baseHp;
    private int baseAtk;

    public Boss(String name, int baseHp, int baseAtk, int speed) {
        super(name, baseHp, baseAtk, speed, 0.05, 1.3);
        this.bossName = name;
        this.baseHp = baseHp;
        this.baseAtk = baseAtk;
    }

    // For choice A: fixed stats per boss (no stage scaling)
    public void scaleForStage(int stage) {
        int hp = baseHp;
        int atk = baseAtk;
        this.maxHp = hp;
        this.hp = hp;
        this.attack = atk;
    }

    // boss attacks a whole row (front/mid/back) - here we return the chosen row index 0..2
    public int randomRowAttack() {
        return (int)(Math.random() * 3);
    }
}
