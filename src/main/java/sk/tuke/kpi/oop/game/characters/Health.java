package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {

    private int initHealth;
    private int maxHealth;
    private List<ExhaustionEffect> effects;
    private boolean called;

    public Health(int initHealth, int maxHealth){
        this.initHealth = initHealth;
        this.maxHealth = maxHealth;
        effects = new ArrayList<>();
        called = false;
    }

    public Health(int health){
        this(health, health);
    }

    public int getValue() {
        return initHealth;
    }

    public void refill(int amount){
        if (initHealth + amount > maxHealth) initHealth = maxHealth;
        else initHealth += amount;
    }

    public void restore(){
        initHealth = maxHealth;
    }

    public void drain(int amount){
        if (initHealth - amount <= 0){
            exhaust();
        }else initHealth -= amount;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void exhaust(){
        initHealth = 0;
        if (!called){
            effects.forEach(ExhaustionEffect::apply);
            called = true;
        }

    }

    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }

    public void onExhaustion(ExhaustionEffect effect){
        effects.add(effect);
    }


}
