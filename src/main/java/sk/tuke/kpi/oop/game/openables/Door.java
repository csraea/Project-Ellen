package sk.tuke.kpi.oop.game.openables;


import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

public class Door extends AbstractActor implements Openable, Usable<Actor> {

    private final byte sixteen = 16;
    private Animation animation;
    private boolean isOpened;
    public static final Topic<Door> DOOR_OPENED = Topic.create("door isOpened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    public enum Orientation {HORIZONTAL, VERTICAL}


    private Orientation orientation;

    public Door(String name, Orientation orientation){
        super(name);
        animation = (orientation == Orientation.VERTICAL) ? new Animation("sprites/vdoor.png", sixteen, sixteen*2, 0.2f) : new Animation("sprites/hdoor.png", 32, 16, 0.2f);
        this.orientation = orientation;
        setAnimation(animation);
        animation.stop();
        isOpened = false;
    }

    @Override
    public void useWith(Actor actor) {
        if(isOpened) {
            close();
        } else {
            open();
        }
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    @Override
    public boolean isOpen() {
        return isOpened;
    }

    @Override
    public void open() {
        animation.setPlayMode(Animation.PlayMode.ONCE_REVERSED);
        animation.play();
        animation.stop();
        isOpened = true;
        int x = this.getPosX() / sixteen;
        int y = this.getPosY() / sixteen;
        getScene().getMap().getTile(x,y).setType(MapTile.Type.CLEAR);
        openHelp(x, y);
        getScene().getMessageBus().publish(DOOR_OPENED, this);
    }

    @Override
    public void close() {
        animation.setPlayMode(Animation.PlayMode.ONCE);
        animation.play();
        animation.stop();
        isOpened = false;
        getScene().getMap().getTile(this.getPosX()/sixteen,this.getPosY()/sixteen).setType(MapTile.Type.WALL);
        closeHelp();
        getScene().getMessageBus().publish(DOOR_CLOSED, this);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        getScene().getMessageBus().publish(DOOR_CLOSED, this);
        getScene().getMap().getTile(this.getPosX()/sixteen,this.getPosY()/sixteen).setType(MapTile.Type.WALL);
        closeHelp();
    }

    private void openHelp(int x, int y) {
        if (orientation == Orientation.VERTICAL) {
            getScene().getMap().getTile(x,y+1).setType(MapTile.Type.CLEAR);
        } else {
            getScene().getMap().getTile(x+1, y).setType(MapTile.Type.CLEAR);
        }
    }

    private void closeHelp() {
        if (orientation == Orientation.VERTICAL) {
            getScene().getMap().getTile(this.getPosX() / sixteen, this.getPosY() / sixteen + 1).setType(MapTile.Type.WALL);
        } else {
            getScene().getMap().getTile(this.getPosX() / sixteen+1,this.getPosY() / sixteen).setType(MapTile.Type.WALL);
        }
    }
}
