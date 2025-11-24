package boss;

public class Boss {
    private String bossName;
    private int bossHP;
    private int bossMaxHP;
    private int bossAtk;

    public Boss(String name, int hp, int atk){
        this.bossName = name;
        this.bossHP = hp;
        this.bossMaxHP = hp;
        this.bossAtk = atk;
    }

    public String getBossName() { return bossName; }
    public int getBossHp() { return bossHP; }
    public int getBossMaxHp() { return bossMaxHP; }

    public boolean bossIsAlive() {
        return bossHP > 0;
    }

    public void bossTakeDamage(int dmg) {
        if (!bossIsAlive()) return;

        bossHP -= dmg;

        if (bossHP <= 0) {
            bossHP = 0;
        }
    }

    public int bossAttack() {
        return bossAtk; // add patterns later 
    }

    @Override
    public String toString() {
        return bossName + " HP: " + bossHP + "/" + bossMaxHP;
    }
}
