package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.Fire;
import sk.tuke.kpi.oop.game.items.Destroyable.Barrel;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

public class ShootingAlien extends Alien implements Armed{

    private Animation animation;
    private Firearm firearm;

    public ShootingAlien(){
        super(200, "shooting alien");
        animation = new Animation("sprites/lurker_alien.png", 32, 32, 0.1f);
        setAnimation(animation);
        firearm = new Gun(1000000);
    }

    @Override
    public int getSpeed() {
        return 2;
    }

    @Override
    public Firearm getFirearm() {
        return firearm;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        this.firearm = weapon;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        scene.getMessageBus().subscribe(Barrel.BARREL_DESTROYED, (ShootingAlien) -> new Invoke<Actor>(this::shoot));
    }

    private void shoot(){
        new Loop<>(
            new ActionSequence<Actor>(
                new Invoke<Actor>(() -> new Fire<>().scheduleOn(this.getScene())),
                new Wait<>(1f)
            )
        ).scheduleOn(getScene());
    }

}
