package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;

public class Shift<K extends Keeper> extends AbstractAction<K> {

    public Shift(){
        super();
    }

    @Override
    public void execute(float deltaTime) {
        if(!isDone() && getActor() != null) {
            getActor().getBackpack().shift();
            setDone(true);
        } else if(!isDone() && getActor() == null) {
            setDone(true);
        }
    }
}
