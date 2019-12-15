package sk.tuke.kpi.oop.game.items.Destroyable;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.characters.Prototype.CloneableActor;

public class Barrel extends CloneableActor {

    private final short sixteen  = 16;
    private final short thirtytwo = 32;
    public static final Topic<Barrel> BARREL_DESTROYED  = Topic.create("barrel detroyed", Barrel.class);
    private int remainingDurability;
    private boolean destroyed;

    public Barrel(){
        super("barrel");
        remainingDurability = 2;
        setAnimation(new Animation("sprites/barrel.png", sixteen, sixteen));
        destroyed = false;
    }

    public int getRemainingDurability() {
        return remainingDurability;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroy(){
        if (remainingDurability == 1){
            setAnimation(new Animation("sprites/large_explosion.png", thirtytwo, thirtytwo, 0.2f, Animation.PlayMode.ONCE));
            getAnimation().play();
            act();
        }
        --remainingDurability;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        getScene().getMap().getTile(this.getPosX()/sixteen,this.getPosY()/sixteen).setType(MapTile.Type.WALL);
    }

    private void act() {
        getScene().getMap().getTile(this.getPosX()/sixteen,this.getPosY()/sixteen).setType(MapTile.Type.CLEAR);
        destroyed = true;
        getScene().getMessageBus().publish(BARREL_DESTROYED, this);
    }
}
