package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.characters.Prototype.CloneableActor;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

import java.util.Objects;

public class Ripley extends CloneableActor implements Movable, Keeper, Armed, Alive{

    private Firearm gun;
    private Health health;
    private int energy;
    private int remainingAmmo;
    private Backpack backpack;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("RIPLEY_DIED", Ripley.class);
    private float hunger;
    private Disposable movController;
    private Disposable colController;
    private Disposable shController;

    public Ripley() {
        super("ellen");
        setAnimation(new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
        gun = new Gun(35);
        health = new Health(100);
        remainingAmmo = 50;
        getAnimation().stop();
        backpack = new Backpack("Ripley's backpack", 10);
        hunger = 50;
    }

    @Override
    public int getSpeed() {
        return 2;
    }


    public void startedMoving(Direction direction) {
        getAnimation().setRotation(direction.getAngle());
        getAnimation().play();
    }

    @Override
    public void stoppedMoving() {
        getAnimation().stop();

    }

    public void setEnergy(int energy) {
        if (energy > 0) {
            this.energy = energy;
        } else {
            this.energy = 0;
            Animation dyingAnimation = new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE);
            setAnimation(dyingAnimation);
            dyingAnimation.play();
            getScene().getMessageBus().publish(RIPLEY_DIED, this);
        }
    }

    public int getEnergy() {
        return energy;
    }


    public int getRemainingAmmo() {
        return remainingAmmo;
    }

    public void setRemainingAmmo(int remainingAmmo) {
        this.remainingAmmo = remainingAmmo;
    }

    public void showRipleyState(){
        Scene scene = getScene();
        int windowHeight = Objects.requireNonNull(scene).getGame().getWindowSetup().getHeight();
        int topOffset = GameApplication.STATUS_LINE_OFFSET;
        int yTextPos = windowHeight - topOffset;
        scene.getGame().getOverlay().drawText("Energy: "+health.getValue(), 100, yTextPos);
        scene.getGame().getOverlay().drawText("Hunger: "+Math.ceil(hunger*10)/10f, 250, yTextPos);
        scene.getGame().getOverlay().drawText("Ammo: "+getFirearm().getAmmo(), 400, yTextPos);
        scene.getGame().getOverlay().drawText("Max ammo: "+getFirearm().getMaxAmmo(), 550, yTextPos);
        scene.getGame().getOverlay().drawText("X: " + this.getPosX(), 570, yTextPos-20);
        scene.getGame().getOverlay().drawText("Y: " + this.getPosY(), 670, yTextPos-20);
        scene.getGame().pushActorContainer(getBackpack());
    }


    @Override
    public Backpack getBackpack() {
        return backpack;
    }

    @Override
    public Firearm getFirearm() {
        return gun;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        gun = weapon;
    }

    @Override
    public Health getHealth() {
        return health;
    }

    private void die(){

        Animation dyingAnimation = new Animation("sprites/player_die.png", 32, 32, 0.1f);
        dyingAnimation.setPlayMode(Animation.PlayMode.ONCE);
        setAnimation(dyingAnimation);


        shController.dispose();
        movController.dispose();
        colController.dispose();

        getScene().getMessageBus().publish(RIPLEY_DIED, this);
        new ActionSequence<Actor>(
            new Wait<Actor>(2f),
            new Invoke<Actor>(() -> getScene().getGame().stop())
        ).scheduleOn(getScene());

    }

    private void updateHunger(){
        hunger += 0.025f;
        if (hunger > 100){
            getHealth().drain((int)hunger-100);
        }
    }

    public boolean eat(){
        if (hunger == 0) return false;
        if (hunger< 10){ hunger = 0; return true; }
        else{ hunger -= 10; return true; }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        this.getHealth().onExhaustion(this::die);
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> scene.getGame().getOverlay().drawText("Ripley Died! ", 100, 100).showFor(2)) ;
        MovableController movableController = new MovableController(this);
        this.movController = scene.getInput().registerListener(movableController);

        KeeperController collectorController = new KeeperController(this);
        this.colController = scene.getInput().registerListener(collectorController);

        ShooterController shooterController = new ShooterController(this);
        this.shController = scene.getInput().registerListener(shooterController);
        super.addedToScene(scene);

        new Loop<Actor>(
            new ActionSequence<Actor>(
                new Invoke<Actor>(this::updateHunger),
                new Wait<>(1f)
            )
        ).scheduleOn(this.getScene());

        new Loop<Actor>(
            new ActionSequence<Actor>(
                new Invoke<Actor>(this::alienIntersecting),
                new Wait<>(1f)
            )
        ).scheduleOn(this.getScene());
    }

    private void alienIntersecting(){
        for (Actor actor : getScene().getActors()) {
            if (actor.intersects(this) && actor instanceof Enemy){
                this.getHealth().drain(30);
            }

        }
    }

    public void kill(){
        getHealth().exhaust();
    }
}

