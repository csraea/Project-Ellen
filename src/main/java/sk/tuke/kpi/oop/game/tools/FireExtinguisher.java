package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class FireExtinguisher extends BreakableTool {

    private Animation animation;

    public FireExtinguisher() {
        super(1);
        animation = new Animation("sprites/extinguisher.png");
        setAnimation(animation);
    }

    public int getExtinguisherUseNum() {
        return super.getRemainingUses();
    }

}
