package sk.tuke.kpi.oop.game;


import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Helicopter extends AbstractActor {

    public Helicopter() {
        Animation animation = new Animation("sprites/heli.png", 64, 64, 0.08f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
    }

    public void searchAndDestroy() {
        new Loop<Helicopter>(new Invoke<Helicopter>(this::moveTo)).scheduleFor(this);
    }

    public void moveTo() {
        Ripley player = (Ripley) getScene().getFirstActorByName("ellen");
        if(player != null && player.getEnergy() >= 0) {
            if (player.getPosX() > this.getPosX()) {
                setPosition(this.getPosX() + 1, this.getPosY());
            }
            if (player.getPosX() < this.getPosX()) {
                setPosition(this.getPosX() - 1, this.getPosY());
            }
            if (player.getPosY() > this.getPosY()) {
                setPosition(this.getPosX(), this.getPosY() + 1);
            }
            if (player.getPosY() < this.getPosY()) {
                setPosition(this.getPosX(), this.getPosY() - 1);
            }

            if (this.intersects(player)) player.setEnergy(player.getEnergy() - 1);
        }
    }
}
