package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.ActorContainer;

public interface Keeper<A extends Actor> extends Actor {

    ActorContainer<A> getContainer();
}
