package units;

public abstract class Unit {
    protected String className;
    protected String name;
    protected int hp;
    protected int attack;
    protected String skill;
    protected int star;

    public Unit(String className, String name, int hp, int attack, String skill) {
        this.className = className;
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.skill = skill;
        this.star = 1;
    }

    public abstract void attack(Unit target); 
    
    // Getters and Setters
    public String getName() { return name; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

    //for debugging, get status of card
    public String toString() {
        return String.format(
            "[%s] Class: %s | HP: %d | ATK: %d | SKILL: %s" ,className, name, hp, attack, skill);
    }
}
