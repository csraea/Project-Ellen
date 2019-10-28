package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Hammer extends BreakableTool<Actor> {

    public Hammer(int uses) {
        super(uses);
        Animation animation = new Animation("sprites/hammer.png");
        setAnimation(animation);
    }

    public Hammer() {
        this(1);
    }

    public int getHammerUseNum() {
        return super.getRemainingUses();
    }



}
