package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor {

    private final Animation onAnimation = new Animation("sprites/fan.png", 32, 32, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
    private final Animation offAnimation = new Animation("sprites/fan.png", 32, 32, 0.2f, Animation.PlayMode.ONCE);

    private boolean isOn;
    private Reactor reactor;

    public Cooler(Reactor reactor) {
        this.isOn = false;
        this.reactor = reactor;
        setAnimation(offAnimation);
    }

    public Reactor getReactor() {
        return reactor;
    }

    public boolean isOn() {
        return this.isOn;
    }

    public void turnOn() {
        this.isOn = true;
        setAnimation(onAnimation);
    }

    public void turnOff() {
        this.isOn = false;
        setAnimation(offAnimation);
    }

    public void coolReactor() {
        if(isOn() && reactor != null) {
            reactor.decreaseTemperature(1);
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}
