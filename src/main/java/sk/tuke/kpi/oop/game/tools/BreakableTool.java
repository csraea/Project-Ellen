package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

public abstract class BreakableTool<A extends Actor> extends AbstractActor implements Usable {

    private int remainingUses;

    public BreakableTool(int remainingUses) {
        this.remainingUses = remainingUses;
    }

    @Override
    public void useWith(Actor actor) {
        if (actor != null && remainingUses > 0) {
            remainingUses--;
            if (remainingUses == 0) {
                getScene().removeActor(this);
            }
        }
    }

    public int getRemainingUses() {
        return remainingUses;
    }
}
