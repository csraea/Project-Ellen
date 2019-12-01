package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.oop.game.weapons.Firearm;

public interface Armed extends Actor {

    void setFirearm(Firearm weapon);
    Firearm getFirearm();

}
