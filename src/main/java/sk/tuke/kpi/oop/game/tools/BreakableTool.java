package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

import java.util.Objects;

public abstract class BreakableTool<A extends Actor> extends AbstractActor {

    private int remainingUses;

    public BreakableTool(int remainingUses) {
        this.remainingUses = remainingUses;
    }

    public void useWith(Actor actor) {
        if (remainingUses > 0) {
            remainingUses--;
            if (remainingUses == 0) {
                Objects.requireNonNull(getScene()).removeActor(actor);
            }
        }
    }

    public int getRemainingUses() {
        return remainingUses;
    }
}
