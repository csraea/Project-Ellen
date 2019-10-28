package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

public abstract class BreakableTool extends AbstractActor {

    private int remainingUses;

    public BreakableTool(int remainingUses) {
        this.remainingUses = remainingUses;
    }

    public void use() {
        if(remainingUses > 0) remainingUses--;
        if(remainingUses == 0){
            Scene scene = this.getScene();
            scene.removeActor(this);
        }
    }

    public int getRemainingUses() {
        return remainingUses;
    }
}
