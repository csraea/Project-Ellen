package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.items.Usable;

public class Use<A extends Actor> extends AbstractAction<A>{

    //private boolean done;
    private Usable<A> itemToUse;

    public Use(Usable<A> item){
        this.itemToUse = item;
    }

    public Disposable scheduleForIntersectingWith(Actor mediatingActor) {
        Scene currentScene = mediatingActor.getScene();
        if (currentScene == null) return null;
        Class<A> usingActorClass = itemToUse.getUsingActorClass();
        for (Actor actor : currentScene) {
            if (mediatingActor.intersects(actor) && usingActorClass.isInstance(actor)) {
                return this.scheduleFor(usingActorClass.cast(actor));
            }
        }
        return null;
    }

    @Override
    public void execute(float deltaTime) {
        if (!isDone()) {
            itemToUse.useWith(getActor());
            this.setDone(true);
        }
    }

}
