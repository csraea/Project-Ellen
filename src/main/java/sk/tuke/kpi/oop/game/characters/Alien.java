package sk.tuke.kpi.oop.game.characters;


import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class Alien extends AbstractActor implements Movable, Alive, Enemy, Behaviour<Alien>{

    private Health health;
    private int speed;
    private Behaviour<Alien> behaviour;

    public Alien(){
        this(100, "alien");
    }

    public Alien(int initHealth, String name){
        super(name);
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f));
        health = new Health(initHealth);
        speed = 1;
    }

    public Alien(int initHealth, Behaviour<Alien> behaviour){
        super("alien");
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f));
        health = new Health(initHealth);
        this.behaviour = behaviour;
        speed = 1;
    }

    @Override
    public int getSpeed() {
        return speed;
    }


    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void setUp(Alien actor) {

    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
       // behaviour.setUp(this);
    }
}
