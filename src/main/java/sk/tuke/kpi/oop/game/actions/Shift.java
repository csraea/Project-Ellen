package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;

public class Shift extends AbstractAction<Keeper<?>>{

    public Shift(){
        super();
    }

    @Override
    public void execute(float deltaTime) {
        if(!isDone()) {
            if (getActor() != null) {
                getActor().getContainer().shift();
            }
            setDone(true);
        }
    }
}
