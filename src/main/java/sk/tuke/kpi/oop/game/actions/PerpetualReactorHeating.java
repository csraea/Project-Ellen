package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Reactor;

public class PerpetualReactorHeating extends AbstractAction<Reactor> {

    private int tempToIncrease;

    public PerpetualReactorHeating(int tempToIncrease) {
        this.tempToIncrease = tempToIncrease;
    }

    @Override
    public void execute(float deltaTime) {
        if(getActor() == null) return;
        getActor().increaseTemperature(tempToIncrease);
    }

    
}
