package boss;

public class Boss {
    private String bossName;
    private int bossHP;
    private int bossAtk;

    public Boss(String name, int hp, int atk){
        this.bossName = name;
        this.bossHP = hp;
        this.bossAtk = atk;
    }

    public String getBossName() { return bossName; }
    public int getBossHp(){ return bossHP; }

    public boolean bossIsAlive(){
        if(bossHP <= 0){
            return false;
        }
        return true;
    }

    public boolean bossTakeDamage(int dmg){
        if(bossIsAlive()){
            bossHP -= dmg;
            return true;
        }
        return false;
    }

    // Boss attack with random pattern
    public int bossAttack(){
        return bossAtk;
    }
}
