package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Teleport extends AbstractActor {

    private Teleport destinationTeleport;
    private boolean teleportAccess;
    private boolean wasOutside;

    public Teleport() {
        this(null);
    }

    public Teleport(Teleport teleport) {
        Animation animation = new Animation("sprites/lift.png", 48, 48);
        setAnimation(animation);
        setDestination(teleport);
        teleportAccess = false;
        wasOutside = false;
    }

    public Teleport getDestination() {
        return destinationTeleport;
    }

    public void setDestination(Teleport teleport) {
        if(this.equals(teleport)) destinationTeleport = null;
        else destinationTeleport = teleport;
    }

    public void teleportPlayer(Player player) {
        if(player != null && destinationTeleport != null && teleportAccess) {
            player.setPosition(destinationTeleport.getPosX() + destinationTeleport.getWidth() / 2, destinationTeleport.getPosY() + destinationTeleport.getHeight() / 2);

            destinationTeleport.wasOutside = false;
            destinationTeleport.teleportAccess = false;
            this.wasOutside = false;
            this.teleportAccess = false;

        }
    }

    private Player getPlayer() {
        return (Player) getScene().getFirstActorByName("Player");
    }

    public boolean isOutside() {
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
}
