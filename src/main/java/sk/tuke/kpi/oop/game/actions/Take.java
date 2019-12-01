package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;

public class Take<A extends Actor> extends AbstractAction<Keeper<A>>{

    private Class<A> takeableActorsClass;

    public Take(Class<A> takeableActorsClass){
        this.takeableActorsClass = takeableActorsClass;
    }

    public void  execute(float deltaTime){
        if(!isDone()) {
            if (getActor() != null) {
                findIntersectingActor();
            }
            setDone(true);
        }
    }

    private void findIntersectingActor(){
        for (Actor a : getActor().getScene().getActors()) {
            if (takeableActorsClass.isInstance(a) && a.intersects(getActor())) {
                try {
                    getActor().getContainer().add(takeableActorsClass.cast(a));
                    getActor().getScene().removeActor(a);

                    break;
                } catch (IllegalStateException exception) {
                    getActor().getScene().getOverlay().drawText(exception.getMessage(), 0, 0).showFor(2);
                }
            }
        }
    }

}
