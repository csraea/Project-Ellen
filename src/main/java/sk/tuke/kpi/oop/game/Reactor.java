package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable {

    private static final byte REACTOR_OFF = 0;
    private static final byte REACTOR_BROKEN = 1;
    private static final byte REACTOR_EXTINGUISHED = 2;

    private final Animation A_reactorOn = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private final Animation A_reactorOff = new Animation("sprites/reactor.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private final Animation A_reactorHot = new Animation("sprites/reactor_hot.png", 80, 80, 0.06f, Animation.PlayMode.LOOP_PINGPONG);
    private final Animation A_reactorBroken = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private final Animation A_reactorExtinguished = new Animation("sprites/reactor_extinguished.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);

    private byte state;
    private int temperature;
    private int damage;
    private boolean isOn;

    private Set<EnergyConsumer> devices;

    public Reactor() {
        state = REACTOR_OFF;
        temperature = 0;
        isOn = false;
        damage = 0;
        setAnimation(A_reactorOff);
        devices = new HashSet<>();
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
        if (increment <= 0 || !isOn)  return;

        float coefficient = ((damage >= 33 && damage <= 66) ? 1.5f : ((damage > 66) ? 2f : 1f));

        double newIncrement = increment * coefficient;
        newIncrement = Math.ceil(newIncrement);

        double newDamage = ((float)(temperature + newIncrement) - 2000f) / 40f;
        newDamage = (newDamage > 0) ? (int) (newDamage - newDamage % 1) : damage;

        int newTemperature = (int) (temperature + newIncrement);

        damage = (newDamage > 100 || newTemperature > 6000) ? 100 : (int) newDamage;
        temperature = newTemperature;

        updateDevicesPowering();
        updateAnimation();
    }

    public void decreaseTemperature(int decrement) {
        if(decrement <= 0 || !isOn()) {
            return;
        }

        if(getTemperature() != 0) {

            int realDecrement = decrement;
            if(getDamage() >= 50)   realDecrement *= 0.5;
            else if(damage == 100)  realDecrement = 0;

            temperature -= realDecrement;
            if(temperature < 0) temperature = 0;

            updateAnimation();
        }
    }

    private void updateAnimation() {
        if(isOn) {
            if(temperature <= 4000) {
                setAnimation(A_reactorOn);
            } else if(temperature < 6000) {
                setAnimation(A_reactorHot);
            } else {
                setAnimation(A_reactorBroken);
                state = REACTOR_BROKEN;
                isOn = false;
                updateDevicesPowering(false);
            }
        }
    }

    @Override
    public boolean repair() {
        if(damage > 0 && damage < 100) {
            damage = ((damage - 50) < 0) ? 0 : damage - 50;
            temperature = (int) Math.ceil((damage / 0.025f) + 2000);
            updateAnimation();
            return true;
        }
        return false;
    }

    public boolean extinguish() {
        if(state == REACTOR_BROKEN) {
            decreaseTemperature(4000);
            if(isOn) isOn = false;
            state = REACTOR_EXTINGUISHED;
            setAnimation(A_reactorExtinguished);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOn() {
        return this.isOn;
    }

    @Override
    public void turnOn() {
        if(state == REACTOR_BROKEN || isOn) return;
        this.isOn = true;
        updateDevicesPowering(true);
        updateAnimation();
    }

    @Override
    public void turnOff() {
        if(state == REACTOR_BROKEN || !isOn) return;
        this.isOn = false;
        updateDevicesPowering(false);
        setAnimation(A_reactorOff);
    }

    public void addDevice(EnergyConsumer energyConsumer) {
        if(isOn()) {
            if(energyConsumer != null) devices.add(energyConsumer);
            if(isOn && state != REACTOR_BROKEN && energyConsumer != null) energyConsumer.setPowered(true);
        }
    }

    public void removeDevice(EnergyConsumer energyConsumer) {
        if(energyConsumer != null) {
            devices.remove(energyConsumer);
            energyConsumer.setPowered(false);
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }


    private void updateDevicesPowering() {
        if(temperature >= 6000 || state == REACTOR_BROKEN || !isOn) updateDevicesPowering(false);
        else updateDevicesPowering(true);
    }

    private void updateDevicesPowering(boolean state) {
        if(devices != null && !devices.isEmpty()) {
            for (EnergyConsumer e : devices) {
                e.setPowered(state);
            }
        }
    }
}




