package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;

public interface Openable extends Actor {

    boolean isOpen();
    void open();
    void close();
}
