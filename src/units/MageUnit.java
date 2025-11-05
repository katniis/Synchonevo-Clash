package units;

public class MageUnit extends Unit {
    public MageUnit(String className, String name, int hp, int attack, String skill) {
        super(className, name, hp, attack, skill);
    }

    @Override
    public void attack(Unit target) {
        System.out.println(name + " " + skill + " " + target.getName() + "!");
        target.setHp(target.getHp() - attack);
    }
}
