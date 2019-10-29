package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer{

    private boolean isPowered;

    private final Animation animationOn = new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
    private final Animation animationOff = new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.ONCE);

    public Computer() {
        setAnimation(animationOff);
        this.isPowered = false;
    }

    public int add(int a, int b) {
        return (isPowered) ? (a + b) : 0;
    }

    public float add(float a, float b) {
        return (isPowered) ? (a + b) : 0;
    }

    public int sub(int a, int b) {
        return (isPowered) ? (a - b) : 0;
    }

    public float sub(float a, float b) {
        return (isPowered) ? (a - b) : 0;
    }

    @Override
    public void setPowered(boolean isPowered) {
        this.isPowered = isPowered;
        if(!isPowered) setAnimation((animationOff));
        else setAnimation(animationOn);
    }
}
