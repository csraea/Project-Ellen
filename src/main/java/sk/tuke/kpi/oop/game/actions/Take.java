package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Take<K extends Keeper> extends AbstractAction<K>{

    private Class<Collectible> takeableClass;

    public Take(Class<Collectible> takeableActorsClass){
        this.takeableClass = takeableActorsClass;
    }

    @Override
    public void execute(float deltaTime){
        if(!isDone()) {
            if (getActor() != null) {
                findIntersectingActor();
            }
            setDone(true);
        }
    }

    private void findIntersectingActor(){
        for (Actor a : getActor().getScene().getActors()) {
            if (takeableClass.isInstance(a) && a.intersects(getActor())) {
                try {
                    getActor().getBackpack().add(takeableClass.cast(a));
                    getActor().getScene().removeActor(a);
                    break;
                } catch (IllegalStateException exception) {
                    getActor().getScene().getOverlay().drawText(exception.getMessage(), 0, 0).showFor(2);
                }
            }
        }
    }
}

/*
package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;


import java.util.List;
import java.util.Objects;


public class Take<K extends Keeper> extends AbstractAction<K> {

    public  Take(){

    }
    @Override
    public void execute(float deltaTime) {
        if(getActor() != null) {
            List<Actor> items = Objects.requireNonNull(getActor().getScene()).getActors();
            for (Actor actors : items) {
                if(getActor().intersects(actors) && actors instanceof Collectible ){
                try {
                    getActor().getBackpack().add((Collectible) actors);
                    getActor().getScene().removeActor(actors);
                } catch (Exception e) {
                    getActor().getScene().getOverlay().drawText("Error",100,100).showFor(2);
                }
                }
                }
        }

        setDone(true);
    }
}
 */
