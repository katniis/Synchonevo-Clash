package boss;

public class Boss {
    private String bossName;
    private int bossHP;
    private int bossMaxHP;
    private int bossAtk;
    private int bossSpeed;

    public Boss(String name, int hp, int atk, int speed){
        this.bossName = name;
        this.bossHP = hp;
        this.bossMaxHP = hp;
        this.bossAtk = atk;
        this.bossSpeed = speed;
    }

    public String getBossName() { return bossName; }
    public int getBossHp() { return bossHP; }
    public int getBossMaxHp() { return bossMaxHP; }
    public int getBossSpeed() { return bossSpeed; }

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
        double multiplier = 0.7 + Math.random() * 0.3;
        return (int) (bossAtk * multiplier);  
    }

    @Override
    public String toString() {
        return bossName + " HP: " + bossHP + "/" + bossMaxHP;
    }
}
