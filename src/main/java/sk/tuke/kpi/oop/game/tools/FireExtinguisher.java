package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class FireExtinguisher extends BreakableTool<Actor> {

    public FireExtinguisher() {
        super(1);
        Animation animation = new Animation("sprites/extinguisher.png");
        setAnimation(animation);
    }

    public int getExtinguisherUseNum() {
        return super.getRemainingUses();
    }

    @Override
    public void useWith(Actor actor) {
        super.useWith(actor);
    }

}
