package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.items.GreenButton;
import sk.tuke.kpi.oop.game.items.Usable;


public class Teleport extends AbstractActor implements Usable<Actor> {

    private Teleport destinationTeleport;
    private boolean teleportAccess;
    private boolean wasOutside;

    public Teleport() {
        this(null);
    }

    public Teleport(Teleport teleport) {
        Animation animation = new Animation("sprites/lift.png", 48, 48);
        setAnimation(animation);
        destinationTeleport = null;
        setDestination(teleport);
        teleportAccess = false;
        wasOutside = false;
    }

    public Teleport getDestination() {
        return destinationTeleport;
    }

    public void setDestination(Teleport teleport) {
        if(this.equals(teleport) && destinationTeleport == null) return;
        else if(this.equals(teleport) && destinationTeleport != null) return;
        else destinationTeleport = teleport;
    }

    public void teleportPlayer(Player player) {
        if(player != null) player.setPosition(getPosX() + getWidth() / 2 - player.getWidth()/2, getPosY() + getHeight() / 2 - player.getHeight()/2);
        if(player != null && destinationTeleport != null && teleportAccess) {
            //  пришлось дописать, чтобы арена не выебывалась (ряд снизу)
            player.setPosition(destinationTeleport.getPosX() + destinationTeleport.getWidth() / 2 - player.getWidth()/2, destinationTeleport.getPosY() + destinationTeleport.getHeight() / 2 - player.getHeight()/2);

            destinationTeleport.wasOutside = false;
            destinationTeleport.teleportAccess = false;


            this.wasOutside = false;
            this.teleportAccess = false;

        }
    }

    private Player getPlayer() {
        return (Player) getScene().getFirstActorByName("Player");
    }

    private boolean isOutside() {
        Actor player = getPlayer();
        if(player != null) return !this.intersects(player);
        return false;
    }

    private void wasPlayerOutside() {
        Player player = getPlayer();
        if(player != null) {
            int qY = player.getPosY() + player.getHeight() / 2;
            int qX = player.getPosX() + player.getWidth() / 2;
            if ((qY < this.getPosY() || qY > (this.getPosY() + this.getHeight())) || (qX < this.getPosX() && qX > (this.getPosX() + this.getWidth()))) {
                wasOutside = true;
            }
        }
    }

    private void isPlayerInside(){
        Player player = getPlayer();
        if(player != null && !teleportAccess && wasOutside) {
            int qY = player.getPosY() + player.getHeight() / 2;
            int qX = player.getPosX() + player.getWidth() / 2;
            if ((qY >= this.getPosY() && qY <= (this.getPosY() + this.getHeight())) && (qX >= this.getPosX() && qX <= (this.getPosX() + this.getWidth()))) {
                teleportAccess = true;
                teleportPlayer(player);
            }
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        this.wasOutside = isOutside();
        new Loop<Teleport>(new Invoke<>(this::wasPlayerOutside)).scheduleFor(this);
        new Loop<Teleport>(new Invoke<>(this::isPlayerInside)).scheduleFor(this);
    }

    @Override
    public void useWith(Actor actor) {
        if (getScene()!=null) {
            Reactor reactor = getScene().getFirstActorByType(Reactor.class);
            if (actor.getClass() == GreenButton.class && reactor!=null && reactor.isOn()) {
                new ActionSequence<Actor>(
                    new Invoke<Actor>(this::end),
                    new Wait<>(3),
                    new Invoke<Actor>(() -> getScene().getGame().stop())
                ).scheduleOn(getScene());
            }
        }
    }

    private void end(){

        getScene().getGame().getOverlay().drawText("You Won!",
            getScene().getGame().getWindowSetup().getWidth()/2,
            getScene().getGame().getWindowSetup().getHeight()/2).showFor(3f);
    }
    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }


}
