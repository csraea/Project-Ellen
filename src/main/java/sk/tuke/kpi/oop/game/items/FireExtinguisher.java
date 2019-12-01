package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class FireExtinguisher extends BreakableTool<Reactor> implements Collectible{

    public FireExtinguisher() {
        super(1);
        Animation animation = new Animation("sprites/extinguisher.png");
        setAnimation(animation);
    }

    public int getExtinguisherUseNum() {
        return super.getRemainingUses();
    }

    @Override
    public void useWith(Reactor reactor) {
        if(reactor != null && getExtinguisherUseNum() > 0 && reactor.extinguish()) {
           super.useWith(reactor);
        }
    }

    @Override
    public Class<Reactor> getUsingActorClass() {
        return Reactor.class;
    }

    public int getRemainingUses() {
        return super.getRemainingUses();
    }


}
