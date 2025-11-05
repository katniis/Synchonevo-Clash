package units;

public class TankUnit extends Unit {
    public TankUnit(String className, String name, int hp, int attack, String skill) {
        super(className, name, hp, attack, skill);
    }

    @Override
    public void attack(Unit target) {
        System.out.println(name + " " + skill + " " + target.getName() + "!");
        target.setHp(target.getHp() - attack);
    }
}
