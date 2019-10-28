package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor {

    private final Animation animationOn = new Animation("sprites/light_on.png");
    private final Animation animationOff = new Animation("sprites/light_off.png");

    private boolean isPowered;
    private boolean isOn;

    public Light() {
        this.isOn = false;
        this.isPowered = false;
        setAnimation(animationOff);
    }

    public void toggle() {
        if(isOn) {
            setAnimation(animationOff);
            isOn = false;
        } else {
            if(this.isPowered) setAnimation(animationOn);
            isOn = true;
        }

    }

    public void setElectricityFlow(boolean isPowered) {
        this.isPowered = isPowered;
        if(!isPowered) setAnimation(animationOff);
        else if(isPowered && isOn) setAnimation(animationOn);
    }
}
