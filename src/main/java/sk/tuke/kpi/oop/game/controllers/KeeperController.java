package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;

public class KeeperController implements KeyboardListener {

    private Keeper<Collectible> actor;

    public KeeperController(Keeper<Collectible> actor) {
        this.actor = actor;
    }

    @Override
    public void keyPressed(Input.Key key) {
        if (key == Input.Key.ENTER) {
            new Take<>(Collectible.class).scheduleFor(actor);
        }
        if (key == Input.Key.BACKSPACE) {
            new Drop<Collectible>().scheduleFor(actor);
        }
        if (key == Input.Key.S) {
            new Shift().scheduleFor(actor);
        }
        pressB(key);
        pressU(key);
    }

    private void pressU(Input.Key key) {
        if (key == Input.Key.U) {
            Usable<?> u = (Usable<?>) actor.getScene().getActors().stream().filter(actor::intersects).filter(Usable.class::isInstance).findFirst().orElse(null);
            if (u != null) {
                new Use<>(u).scheduleOnIntersectingWith(actor);
            }
        }
    }

    public void pressB(Input.Key key) {
        if (key == Input.Key.B && actor.getBackpack().peek() != null && actor.getBackpack().peek() instanceof Usable ) {

            Usable<?> usable = (Usable<?>) actor.getBackpack().peek();
            new Use<>(usable).scheduleOnIntersectingWith(actor);
        }
    }

}
