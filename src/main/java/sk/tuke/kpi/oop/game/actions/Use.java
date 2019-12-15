package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.items.Usable;

public class Use<A extends Actor> extends AbstractAction<A>{

    private Usable<A> itemToUse;

    public Use(Usable<A> item){
        this.itemToUse = item;
    }

    public Disposable scheduleForIntersectingWith(Actor mediatingActor) {
        return act(mediatingActor);
    }

    private Disposable act(Actor mediatingActor) {
        Scene currentScene = mediatingActor.getScene();
        if (currentScene == null) return null;
        Class<A> usingActorClass = itemToUse.getUsingActorClass();
        for (Actor q : currentScene) {
            if (mediatingActor.intersects(q) && usingActorClass.isInstance(q)) {
                return this.scheduleFor(usingActorClass.cast(q));
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
