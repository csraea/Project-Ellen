package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;

public class Drop<A extends Actor> extends AbstractAction<Keeper<A>> {

    public Drop(){
        super();
    }

    @Override
    public void execute(float deltaTime) {

        if (!isDone() && getActor()!= null ) {
            Actor item = getActor().getContainer().peek();
            if(item != null) {
                getActor().getContainer().remove(getActor().getContainer().peek());
                getActor().getScene().addActor(item, getActor().getPosX()+8, getActor().getPosY()+7);

            }
        }
        setDone(true);
    }
}

