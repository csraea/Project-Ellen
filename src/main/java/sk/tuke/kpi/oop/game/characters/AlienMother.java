package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class AlienMother extends Alien {

    private Health health;
    Disposable loop;

    public AlienMother(){
        super(500, "alien mother");
        loop = null;
        setAnimation(new Animation("sprites/mother.png", 112, 162, 0.2f));
    }

    @Override
    public void removedFromScene(@NotNull Scene scene) {
        loop.dispose();
        scene.cancelActions(this);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        loop = new Loop<>(
            new ActionSequence<Actor>(
                new Invoke<Actor>(() -> scene.addActor(new ShootingAlien(), this.getPosX()+30, this.getPosY())),
                new Wait<>(2f)
            )
        ).scheduleOn(scene);
    }

}
