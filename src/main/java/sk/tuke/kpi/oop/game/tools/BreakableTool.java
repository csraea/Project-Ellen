package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

public abstract class BreakableTool extends AbstractActor {

    private int remainingUses;
    private Scene scene;

    public BreakableTool(int remainingUses) {
        this.remainingUses = remainingUses;
        this.scene = getScene();
    }

    public void use() {
        if(remainingUses > 0) remainingUses--;
        if(remainingUses == 0){
            scene.removeActor(this);
        }
    }

    public int getRemainingUses() {
        return remainingUses;
    }
}
