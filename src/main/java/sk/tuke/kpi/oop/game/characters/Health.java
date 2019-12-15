package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {

    private int initialHealth;
    private int maximalHealth;
    private List<ExhaustionEffect> effects;
    private boolean isCalled;

    public Health(int initHealth, int maxHealth){
        this.initialHealth = initHealth;
        this.maximalHealth = maxHealth;
        effects = new ArrayList<>();
        isCalled = false;
    }

    public Health(int health){
        this(health, health);
    }

    public int getValue() {
        return initialHealth;
    }

    public void restore(){
        initialHealth = maximalHealth;
    }

    public void drain(int amount){
        if (initialHealth - amount <= 0){
            exhaust();
        } else initialHealth -= amount;
    }

    public int getMaximalHealth() {
        return maximalHealth;
    }

    public void exhaust(){
        initialHealth = 0;
        if (!isCalled){
            effects.forEach(ExhaustionEffect::apply);
            isCalled = true;
        }

    }

    public void refill(int amount){
        if (initialHealth + amount > maximalHealth) initialHealth = maximalHealth;
        else initialHealth += amount;
    }

    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }

    public void onExhaustion(ExhaustionEffect effect){
        effects.add(effect);
    }


}
