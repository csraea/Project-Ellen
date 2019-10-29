package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

public class Reactor extends AbstractActor implements Switchable, Repairable {

    private static final int REACTOR_OFF = 0;
    private static final int REACTOR_ON = 1;
    private static final int REACTOR_HOT = 2;
    private static final int REACTOR_BROKEN = 3;

    private int state;
    private int temperature;
    private int damage;
    private Animation animation;
    private boolean isOn;

    private EnergyConsumer energyConsumer;

    public Reactor() {
        state = REACTOR_OFF;
        temperature = 0;
        isOn = false;
        damage = 0;
        animation = new Animation("sprites/reactor.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
        energyConsumer = null;
    }

    public int getState() {
        return state;
    }

    public int getDamage() {
        return damage;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void increaseTemperature(int increment) {

        if (increment <= 0 || !isOn()) {
            return;
        }

        int currentTemperature = getTemperature();
        int currentDamage = getDamage();

        if (currentDamage == 100 || currentTemperature == 6000) {
            return;
        }

        float coefficient = 1;

        if (currentDamage >= 33 && currentDamage <= 66) {
            coefficient = 1.5f;
        } else if (currentDamage > 66) {
            coefficient = 2f;
        }

        //rounding up
        double roundedTemp = increment * coefficient;
        if (roundedTemp % 1 != 0) {
            roundedTemp -= roundedTemp % 1;
            roundedTemp++;
        }

        double temp = (currentTemperature + roundedTemp - 2000) / 40;
        int newDamage = currentDamage;
        if (temp > 0) {
            newDamage = (int) (temp - temp % 1);
        }
        int newTemperature = (int) (currentTemperature + roundedTemp);

        if (newDamage > 100) {
            damage = 100;
            temperature = 6000;
        } else if (newTemperature > 6000) {
            temperature = 6000;
            damage = 100;
        } else {
            temperature = newTemperature;
            damage = newDamage;
        }

        updateAnimation();
    }

    public void decreaseTemperature(int decrement) {

        if(decrement <= 0 || !isOn()) {
            return;
        }

        if(getTemperature() != 0) {

            int realDecrement = decrement;
            if(getDamage() >= 50){
                realDecrement *= 0.5;
            } else if(damage == 100) {
                realDecrement = 0;
            }

            temperature -= realDecrement;
            if(getTemperature() < 0){
                temperature = 0;
            }

            updateAnimation();

        }
    }

    private void updateAnimation() {
        int currentTemp = getTemperature();
        int currentDamage = getDamage();
        float frameDuration = 0.1f;

        if(isOn()) {
            if(currentDamage >= 90 && currentDamage < 100) {
                 frameDuration = 0.05f;
            } else if(currentDamage >= 80) {
                frameDuration = 0.07f;
            } else if(currentDamage >= 70) {
                frameDuration = 0.08f;
            } else if(currentDamage >= 50) {
                frameDuration = 0.09f;
            }
        }

        if (currentDamage == 100 && state != REACTOR_BROKEN && isOn()) {
            state = REACTOR_BROKEN;
            this.isOn = false;
            if(energyConsumer != null) energyConsumer.setPowered(false);
            animation = new Animation("sprites/reactor_broken.png", 80, 80, 0.17f, Animation.PlayMode.LOOP_PINGPONG);
            setAnimation(animation);
        } else if (currentTemp >= 4000 && state != REACTOR_HOT && isOn()) {
            state = REACTOR_HOT;
            this.animation  = new Animation("sprites/reactor_hot.png", 80, 80, frameDuration, Animation.PlayMode.LOOP_PINGPONG);
            setAnimation(animation);
            if(this.energyConsumer != null) energyConsumer.setPowered(true);
        } else if (currentTemp < 4000 && state != REACTOR_ON && isOn()) {
            state = REACTOR_ON;
            animation  = new Animation("sprites/reactor_on.png", 80, 80, frameDuration, Animation.PlayMode.LOOP_PINGPONG);
            setAnimation(animation);
            if(this.energyConsumer != null) energyConsumer.setPowered(true);
        } else if (!isOn() && state != REACTOR_BROKEN) {
            animation = new Animation("sprites/reactor.png", 80, 80, frameDuration, Animation.PlayMode.LOOP_PINGPONG);
            setAnimation(animation);
            state = REACTOR_OFF;
            if(this.energyConsumer != null) energyConsumer.setPowered(false);
        }
    }

    @Override
    public boolean repair() {
        int damage = getDamage();
        if(damage > 0 && damage < 100) {
            damage -= 50;
            setDamage((damage >= 0) ? damage : 0);
            //  place here a function to decrease temperature according to the level of damage
            updateAnimation();
            return true;
        }
        return false;
    }

    public boolean extinguish() {
        if( state == REACTOR_BROKEN ) {
            decreaseTemperature(4000); //??? or change the temperature directly?
            animation = new Animation("sprites/reactor_extinguished.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
            setAnimation(animation);
            return true;
        }
        return false;
    }

    public boolean isOn() {
        return this.isOn;
    }

    public void turnOn() {
        if(this.state != REACTOR_BROKEN && this.state != REACTOR_ON) {
            this.isOn = true;
            if(this.energyConsumer != null) energyConsumer.setPowered(true);
            updateAnimation();
        }
    }

    public void turnOff() {
        if(this.state != REACTOR_BROKEN && this.state != REACTOR_OFF) {
            this.isOn = false;
            this.state = REACTOR_OFF;
            if(this.energyConsumer != null) energyConsumer.setPowered(false);
            updateAnimation();
        }
    }

    public void addDevice(EnergyConsumer energyConsumer) {
        if(isOn()) {
            this.energyConsumer = energyConsumer;
            if(isOn() && state != REACTOR_BROKEN && state != REACTOR_OFF)
                energyConsumer.setPowered(true);
        }
    }

    public void removeDevice(EnergyConsumer energyConsumer) {
        energyConsumer.setPowered(false);
        this.energyConsumer = null;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }
}

