package sk.tuke.kpi.oop.game;


import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor {

    public Helicopter() {
        Animation animation = new Animation("sprites/heli.png", 64, 64, 0.08f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
    }

    public void searchAndDestroy() {
        Scene scene = getScene();
        Player player = (Player) scene.getFirstActorByName("Player");
        int plX = player.getPosX();
        int plY = player.getPosY();

        int yCoord = this.getPosX();
        int xCoord= this.getPosY();

        yCoord = (plY > yCoord) ? yCoord + 1 : yCoord -1;
        xCoord = (plX > xCoord) ? xCoord + 1 : xCoord -1;

        this.setPosition(xCoord, yCoord);

        if(this.intersects(player)) player.setEnergy(player.getEnergy()-1);
    }

}
