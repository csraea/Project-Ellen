package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Controller extends AbstractActor {

    private Reactor reactor;

    public Controller(Reactor reactor) {
        this.reactor = reactor;
        Animation animation = new Animation("sprites/switch.png");
        setAnimation(animation);
    }

    public void toggle() {
        if(reactor.isRunning()) {
            reactor.turnOff();
        } else {
            reactor.turnOn();
        }
    }
}
