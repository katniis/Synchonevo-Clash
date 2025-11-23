package boss;

public class Boss {
    private String bossName;
    private int bossBaseHp;
    private int bossBaseAtk;

    public Boss(String name, int hp, int atk){
        this.bossName = name;
        this.bossBaseHp = hp;
        this.bossBaseAtk = atk;
    }

    public String getBossName() { return bossName; }
    public int getBossHp(){ return bossBaseHp; }

    public boolean bossIsAlive(){
        if(bossBaseHp <= 0){
            return false;
        }
        return true;
    }

    // Boss attack with random pattern
    public int bossAttack(){
        return bossBaseAtk;
    }
}
