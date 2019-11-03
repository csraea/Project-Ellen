package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;
import sk.tuke.kpi.oop.game.tools.Usable;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, EnergyConsumer, Repairable {

    private int                 temperature;
    private int                 damage;
    private boolean             isAlive;
    private Set<EnergyConsumer> devices;
    private Animation           animation;
    private Animation           reactorOff;
    private Animation           reactor_extinguished;
    private Animation           normalAnimation;
    private Animation           hotAnimation;
    private Animation           brokenAnimation;

    public Reactor() {
        temperature = 0;
        damage = 0;
        isAlive = false;
        devices = new HashSet<>();
        normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        reactor_extinguished = new Animation("sprites/reactor_extinguished.png");
        reactorOff = new Animation("sprites/reactor.png");

        animation = reactorOff;
        setAnimation(animation);
    }

    @Override
    public boolean repair() {
        if (damage > 0) {
            damage -= 50;
            if (damage < 0)
                damage = 0;
            temperature = (int) Math.ceil((damage / 0.025f) + 2000);
            updateAnimation();
            return true;
        }
        return false;
    }

    @Override
    public void setPowered(boolean isPowered) {
        if (isPowered)
            turnOn();
        else
            turnOff();
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        //new PerpetualReactorHeating(1).scheduleFor(this);
        scene.scheduleAction(new PerpetualReactorHeating(1), this);
    }

    public boolean extinguish() {
        if (animation == brokenAnimation) {
            temperature -= 4000;
            animation = reactor_extinguished;
            setAnimation(animation);
            return true;
        }
        return false;
    }

    public void addDevice(EnergyConsumer energyConsumer) {
        devices.add(energyConsumer);
        if (isOn() && isAlive)
            energyConsumer.setPowered(true);
        else
            energyConsumer.setPowered(false);
    }

    public void removeDevice(EnergyConsumer energyConsumer) {
        if (energyConsumer != null) {
            energyConsumer.setPowered(false);
            devices.remove(energyConsumer);
        }
    }

    public boolean isOn() {
        return isAlive;
    }

    public void turnOn() {
        isAlive = true;
        if (devices != null) {
            for (EnergyConsumer energyConsumer : devices){
                if (isOn())
                    energyConsumer.setPowered(true);
            }
        }
        updateAnimation();
    }

    public void turnOff() {
        isAlive = false;
        if (devices != null) {
            for (EnergyConsumer energyConsumer : devices){
                energyConsumer.setPowered(false);
            }
        }
        updateAnimation();
    }

    public void increaseTemperature(int increment) {
        if (increment < 0 || !isAlive)
            return;
        //if (damage < 100) {
        if (damage >= 33 && damage <= 66)
            temperature += Math.round(increment * 1.5f);
        else if (damage > 66)
            temperature += increment * 2;
        else
            temperature += increment;
        if (temperature >= 2000)
            damage = (int) Math.floor((temperature - 2000) * 0.025f);
        if (temperature >= 6000) {
            damage = 100;
            for (EnergyConsumer energyConsumer : devices){
                energyConsumer.setPowered(false);
            }
        }

        updateAnimation();
        //}
    }

    public boolean repair(Usable<Actor> hammer) {
        if (damage > 0) {
            hammer.useWith(this);
            damage -= 50;
            if (damage < 0)
                damage = 0;
            temperature -= 2000;

            updateAnimation();

            return true;
        }
        return false;
    }

    public void decreaseTemperature(int decrement) {
        if (decrement < 0 || temperature < 0 || !isAlive)
            return;
        if (damage < 100) {
            if (damage >= 50)
                temperature -= Math.round(decrement * 0.5f);
            else
                temperature -= decrement;

            updateAnimation();
        }
    }

    private void updateAnimation() {
        if (isAlive) {
            if (damage >= 100) {
                animation = brokenAnimation;
                turnOff();
            }
            if (temperature >= 4000 && temperature < 6000)
                animation = hotAnimation;
            if (temperature < 4000)
                animation = normalAnimation;

            //changeFrameDuration();
        }
        else if (damage < 100)
            animation = reactorOff;

        setAnimation(animation);
    }

    public int getTemperature() {
        return temperature;
    }

    public int getDamage() {
        return damage;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    /*public void changeFrameDuration() {
        if (temperature >= 0 && temperature <= 1000)
            animation.setFrameDuration(0.1f);
        else if (temperature > 1000 && temperature <= 2000)
            animation.setFrameDuration(0.09f);
        else if (temperature > 2000 && temperature <= 3000)
            animation.setFrameDuration(0.07f);
        else if (temperature > 3000 && temperature <= 4000)
            animation.setFrameDuration(0.06f);
        else if (temperature > 4000 && temperature <= 5000)
            animation.setFrameDuration(0.05f);
        else if (temperature > 5000 && temperature < 6000)
            animation.setFrameDuration(0.03f);
    }

     */
}
