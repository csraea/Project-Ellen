package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Destroyable.Barrel;

public class Bullet extends AbstractActor implements Fireable {

    public Bullet(){
        setAnimation(new Animation("sprites/bullet.png", 16, 16));
    }

    @Override
    public int getSpeed() {
        return 5;
    }

    @Override
    public void startedMoving(Direction direction) {
        this.getAnimation().setRotation(direction.getAngle());
    }

    @Override
    public void collidedWithWall() {
        getScene().cancelActions(this);
        getScene().removeActor(this);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::check)
            )
        ).scheduleOn(this.getScene());

    }


    private void check(){
        for (Actor actor : getScene().getActors()) {
            if (actor.intersects(this)){
                if (!(actor instanceof Ripley) && actor instanceof Alive) {
                    ((Alive) actor).getHealth().drain(50);
                    pleh(actor);
                }
                help(actor);
            }
        }
    }

    private void pleh(Actor actor) {
        getScene().removeActor(actor);
        getScene().removeActor(this);
    }

    private void help(Actor actor) {
        if (actor.getClass() == Barrel.class) {
            ((Barrel) actor).destroy();
            getScene().removeActor(this);
            if (((Barrel) actor).getRemainingDurability() == 0) {
                getScene().removeActor(actor);
            }
        }
    }
}
