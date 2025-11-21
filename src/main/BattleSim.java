package main;

import units.Unit;
import java.util.*;

public class BattleSim {
    private Player player;
    private Unit boss;
    private Deque<String> log = new ArrayDeque<>();

    public BattleSim(Player player, Unit boss) {
        this.player = player;
        this.boss = boss;
    }

    // Very simplified: bench -> summon all as temporary units for this demo
    public boolean runBattle() {
        // Create unit list from player's bench cards by summoning units (demo only)
        List<Unit> allies = new ArrayList<>();
        // In a full game you'd use board positions; here we simulate with bench units
        try {
            for (int i = 0; i < player.getBenchCount(); i++) {
                // reflectively summon unit from card if present
                // For safety in this demo we skip nulls
            }
        } catch (Exception e) { }

        // simple fight: 3 allies with sample stats vs boss
        //Unit a1 = new WarriorUnit("DemoWarrior", 100, 15, 8, 0.05, 1.5);
        //Unit a2 = new MageUnit("DemoMage", 60, 18, 7, 0.10, 1.7);
        //Unit a3 = new ArcherUnit("DemoArcher", 70, 12, 9, 0.12, 1.6);
        //allies.add(a1); allies.add(a2); allies.add(a3);

        List<Unit> actors = new ArrayList<>();
        actors.addAll(allies);
        actors.add(boss);

        while (true) {
            actors.removeIf(u -> u == null || !u.isAlive());
            if (boss == null || !boss.isAlive()) {
                System.out.println("Boss defeated!");
                return true;
            }
            boolean anyAlive = false;
            for (Unit u : actors) if (u != boss && u.isAlive()) anyAlive = true;
            if (!anyAlive) {
                System.out.println("All allies dead.");
                return false;
            }

            // sort by speed desc, tie by star desc
            actors.sort((u1,u2) -> {
                if (u2.getSpeed() != u1.getSpeed()) return Integer.compare(u2.getSpeed(), u1.getSpeed());
                return Integer.compare(u2.getStar(), u1.getStar());
            });

            for (Unit actor : new ArrayList<>(actors)) {
                if (!actor.isAlive()) continue;
                if (actor == boss) {
                    // boss attacks a random ally
                    List<Unit> aliveAllies = new ArrayList<>();
                    for (Unit a : actors) if (a != boss && a.isAlive()) aliveAllies.add(a);
                    if (aliveAllies.isEmpty()) break;
                    Unit target = aliveAllies.get(new Random().nextInt(aliveAllies.size()));
                    String line = boss.attack(target);
                    System.out.println(line);
                } else {
                    String line = actor.attack(boss);
                    System.out.println(line);
                }
                if (!boss.isAlive()) break;
            }

            // loop continues until someone dies
        }
    }
}
