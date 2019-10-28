package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class Hammer extends BreakableTool {

    public Hammer() {
        super(1);
        Animation animation = new Animation("sprites/hammer.png");
        setAnimation(animation);
    }

    public Hammer(int uses) {
        super(uses);
        Animation animation = new Animation("sprites/hammer.png");
        setAnimation(animation);
    }

    public int getHammerUseNum() {
        return super.getRemainingUses();
    }



}
