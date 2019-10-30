package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class TimeBomb extends AbstractActor {

    private float deltaTime;
    private boolean isActivated;

    public TimeBomb(float deltaTime) {
        this.deltaTime = deltaTime;
        this.isActivated = false;
        Animation A_bombOff = new Animation("sprites/bomb.png");
        setAnimation(A_bombOff);
    }

    public void activate() {
        if(!isActivated) {
            Animation A_bombActivated = new Animation("sprites/bomb_activated.png", 16, 16, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
            setAnimation(A_bombActivated);
            this.isActivated = true;
            new ActionSequence<>(
                new Wait<>(10),
                new Invoke<>(this::EXPLODE),
                new Wait<>(0.675f),
                new Invoke<>(this::disappear)
            ).scheduleFor(this);
        }
    }

    public boolean isActivated() {
        return this.isActivated;
    }

    private boolean disappear(){
        getScene().removeActor(this);
        return true;
    }

    private void EXPLODE(){
        Animation A_explosion = new Animation("sprites/small_explosion.png", 16, 16, 0.0575f, Animation.PlayMode.ONCE);
        setAnimation(A_explosion);
    }
}