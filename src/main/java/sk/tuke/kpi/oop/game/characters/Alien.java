package sk.tuke.kpi.oop.game.characters;


import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.characters.Prototype.CloneableActor;

public class Alien extends CloneableActor implements Movable, Alive, Enemy{

    private Health health;

    public Alien(){
        this(100, "alien");
    }

    public Alien(int initHealth, String name){
        super(name);
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f));
        health = new Health(initHealth);
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    @Override
    public Health getHealth() {
        return health;
    }

    private void die(){
        this.getScene().removeActor(this);
    }

    public int zivot(){
        return getHealth().getValue();
    }
}
