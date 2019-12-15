package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Usable;

public class KeeperController implements KeyboardListener {

    private Keeper actor;

    public KeeperController(Keeper actor) {
        this.actor = actor;
    }

    private void pressU(Input.Key key) {
        if (key == Input.Key.U) {
            Usable<?> u = (Usable<?>) actor.getScene().getActors().stream().filter(actor::intersects).filter(Usable.class::isInstance).findFirst().orElse(null);
            if (u != null) {
                new Use<>(u).scheduleForIntersectingWith(actor);
            }
        }
    }

    private void pressB(Input.Key key) {
        if (key == Input.Key.B && actor.getBackpack().peek() != null && actor.getBackpack().peek() instanceof Usable ) {
            Usable<?> usable = (Usable<?>) actor.getBackpack().peek();
            new Use<>(usable).scheduleForIntersectingWith(actor);
        }
    }

    @Override
    public void keyPressed(Input.Key key) {
        if (key == Input.Key.ENTER) {
            new Take<>().scheduleFor(actor);
        }
        if (key == Input.Key.BACKSPACE) {
            new Drop<Keeper>().scheduleFor(actor);
        }
        if (key == Input.Key.S) {
            new Shift<Keeper>().scheduleFor(actor);
        }
        pressU(key);
        pressB(key);
    }

}
