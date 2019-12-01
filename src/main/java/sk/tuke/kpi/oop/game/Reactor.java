package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable, EnergyConsumer {


    private Animation a_reactorOn = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private Animation a_reactorOff = new Animation("sprites/reactor.png");
    private Animation a_reactorHot = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
    private Animation a_reactorBroken = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private Animation a_reactorExtinguished = new Animation("sprites/reactor_extinguished.png");

    private boolean isOn;
    private int temperature;
    private int damage;
    private Animation animation;
    private Set<EnergyConsumer> devices;

    public Reactor() {
        temperature = 0;
        damage = 0;
        isOn = false;
        devices = new HashSet<>();

        animation = a_reactorOff;
        setAnimation(animation);
    }

    private void updateAnimation() {
        if (isOn) {
            if (damage >= 100) {
                animation = a_reactorBroken;
                turnOff();
            }
            if (temperature >= 4000 && temperature < 6000) animation = a_reactorHot;
            if (temperature < 4000) animation = a_reactorOn;
        }
        else if (damage < 100) animation = a_reactorOff;

        setAnimation(animation);
    }

    @Override
    public boolean repair() {
        if (damage > 0 && damage < 100) {
            damage = ((damage - 50) < 0) ? 0 : damage - 50;
            temperature = (int) Math.ceil((damage / 0.025f) + 2000);
            updateAnimation();
            return true;
        }
        return false;
    }


    @Override
    public void setPowered(boolean isPowered) {
        if (isPowered) turnOn();
        else turnOff();
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        scene.scheduleAction(new PerpetualReactorHeating(1), this);
    }

    public boolean extinguish() {
        if (animation == a_reactorBroken) {
            temperature -= 4000;
            animation = a_reactorExtinguished;
            setAnimation(animation);
            return true;
        }
        return false;
    }


    public void turnOff() {
        isOn = false;
        if (devices != null) {
            for (EnergyConsumer energyConsumer : devices){
                energyConsumer.setPowered(false);
            }
        }
        updateAnimation();
    }

    public void increaseTemperature(int increment) {
        if (increment < 0 || !isOn) return;

        if (damage >= 33 && damage <= 66) temperature += Math.round(increment * 1.5f);
        else if (damage > 66) temperature += increment * 2;
        else temperature += increment;
        if (temperature >= 2000) damage = ((int) Math.floor((temperature - 2000) * 0.025f));

        if (temperature >= 6000) {
            damage = 100;
            for (EnergyConsumer energyConsumer : devices){
                energyConsumer.setPowered(false);
            }
        }

        updateAnimation();
    }

    public boolean repair(Usable<Actor> hammer) {
        if (damage <= 0) return false;
        hammer.useWith(this);
        damage = ((damage - 50) < 0) ? 0 : damage - 50;
        temperature -= 2000;

        updateAnimation();
        return true;
    }

    public void decreaseTemperature(int decrement) {
        if (decrement < 0 || temperature < 0 || !isOn) return;
        if (damage < 100) {
            if (damage >= 50) temperature -= Math.round(decrement * 0.5f);
            else temperature -= decrement;

            updateAnimation();
        }
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

    public void addDevice(EnergyConsumer energyConsumer) {
        devices.add(energyConsumer);
        if (isOn() && isOn) energyConsumer.setPowered(true);
        else energyConsumer.setPowered(false);
    }


    public void turnOn() {
        isOn = true;
        if (devices != null) {
            for (EnergyConsumer energyConsumer : devices){
                if (isOn())
                    energyConsumer.setPowered(true);
            }
        }
        updateAnimation();
    }

    public boolean isOn() {
        return isOn;
    }


    public void removeDevice(EnergyConsumer energyConsumer) {
        if (energyConsumer != null) {
            energyConsumer.setPowered(false);
            devices.remove(energyConsumer);
        }
    }
}
