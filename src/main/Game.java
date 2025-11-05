package main;

import cards.UnitFactory;
import cards.UnitType;
import units.Unit;

public class Game {
    public void start(){
        for(UnitType type : UnitType.values()){
            Unit u = UnitFactory.createUnit(type);
            System.out.println(u);
        }
    }
}
