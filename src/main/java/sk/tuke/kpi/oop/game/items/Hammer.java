package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;

import sk.tuke.kpi.oop.game.Repairable;

public class Hammer extends BreakableTool<Repairable> implements Collectible {

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

    @Override
    public void useWith(Repairable repairable) {
        if (repairable != null && getHammerUseNum() > 0 && repairable.repair()) {
            super.useWith(repairable);
        }
    }

    @Override
    public Class<Repairable> getUsingActorClass() {
        return Repairable.class;
    }
}
