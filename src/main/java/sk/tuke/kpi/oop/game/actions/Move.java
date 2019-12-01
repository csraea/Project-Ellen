package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

import java.util.Objects;

public class Move<A extends Movable> implements Action<A> {

    private A actor;
    private Direction direction;
    private boolean isDone;
    private boolean firstExecution;
    private float duration;


    public Move(@NotNull Direction direction, float duration) {
        this.duration = duration;
        this.direction = direction;
        isDone = false;
        firstExecution = true;
    }

    public Move(@NotNull Direction direction) {
        this(direction, 0);
    }
    @Nullable
    @Override
    public A getActor() {
        return actor;
    }

    @Override
    public void setActor(A movable) {
        this.actor =  movable;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void execute(float deltaTime) {
        if (!isDone()) {
            if (firstExecution) {
                actor.startedMoving(direction);
                firstExecution = false;
            }
            duration -= deltaTime;
            if (duration > 0) {
                actor.setPosition(actor.getPosX() + direction.dx * actor.getSpeed(), actor.getPosY() + direction.dy * actor.getSpeed());
                if (Objects.requireNonNull(actor.getScene()).getMap().intersectsWithWall(actor)) {
                    actor.setPosition(actor.getPosX() - direction.dx * actor.getSpeed(), actor.getPosY() - direction.dy * actor.getSpeed());
                    actor.collidedWithWall();
                }
            } else {
                stop();
            }
        }
    }

    @Override
    public void reset() {

        actor.stoppedMoving();
        isDone = false;
        firstExecution = false;

        duration=0;
    }

    public void stop(){
        if (actor != null) {
            actor.stoppedMoving();
            isDone = true;
            firstExecution = false;
        }
    }
}
