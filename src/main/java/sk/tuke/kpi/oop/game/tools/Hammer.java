package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class Hammer extends BreakableTool<Reactor> {

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
    public void useWith(Reactor reactor) {
        if(reactor != null && getHammerUseNum() > 0 && reactor.repair()) {
            super.useWith(reactor);
        }
    }
}
