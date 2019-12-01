package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Teleport;


public class GreenButton extends AbstractActor implements Usable<Teleport>, Collectible {

    public GreenButton(){
        setAnimation(new Animation("sprites/button_green.png", 16,16));
    }


    @Override
    public void useWith(Teleport actor) {
        actor.useWith(this);
    }

    @Override
    public Class<Teleport> getUsingActorClass() {
        return Teleport.class;
    }


}
