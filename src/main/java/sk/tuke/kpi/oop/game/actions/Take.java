package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.Objects;

public class Take<P extends Keeper> extends AbstractAction<P>{

    public Take() {

    }

    @Override
    public void execute(float deltaTime){
        if (getActor() != null) {
            findIntersectingActor();
        }
        setDone(true);
    }

    private void findIntersectingActor(){
        for (Actor a : Objects.requireNonNull(getActor().getScene().getActors())) {
            if (getActor().intersects(a) && a instanceof Collectible ) {
                try {
                    getActor().getBackpack().add((Collectible) a);
                    getActor().getScene().removeActor(a);
                    break;
                } catch (IllegalStateException exception) {
                    getActor().getScene().getOverlay().drawText(exception.getMessage(), 0, 0).showFor(2);
                }
            }
        }
    }
}
