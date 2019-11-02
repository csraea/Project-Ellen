package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer {

    private final Animation animationOn = new Animation("sprites/light_on.png");
    private final Animation animationOff = new Animation("sprites/light_off.png");

    private boolean isPowered;
    private boolean isOn;

    public Light() {
        this.isOn = false;
        this.isPowered = false;
        setAnimation(animationOff);
    }

    @Override
    public boolean isOn() {
        return this.isOn;
    }

    @Override
    public void turnOn() {
        this.isOn = true;
        if(isPowered) setAnimation(animationOn);
    }

    @Override
    public void turnOff() {
        this.isOn = false;
        setAnimation(animationOff);
    }

    public void toggle() {
        if(isOn) {
            setAnimation(animationOff);
            isOn = false;
        } else {
            if(isPowered) setAnimation(animationOn);
            isOn = true;
        }
    }

     @Override
     public void setPowered(boolean isPowered) {
        this.isPowered = isPowered;
        if(!isPowered) setAnimation(animationOff);
        else if(isOn) setAnimation(animationOn);
    }
}
