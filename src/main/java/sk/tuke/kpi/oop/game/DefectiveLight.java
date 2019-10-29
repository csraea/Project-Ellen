package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light implements Repairable {

    private Disposable defect;

    public DefectiveLight() {
        super();
    }

    public void setLightDefect() {
        int randomNum = (int) (Math.random() * 20) + 1;
        if(randomNum == 3) {
            super.toggle();
        }
    }

    @Override
    public boolean repair(){
        if(super.isOn()) {
            return false;
        } else {
            defect.dispose();
            new ActionSequence<>(
                new Wait<>(10),
                new Invoke<>(this::setDefect)
            ).scheduleFor(this);
            return true;
        }

    }

    private void setDefect() {
        defect = new Loop<>(new Invoke<>(this::setLightDefect)).scheduleFor(this);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        setDefect();
    }
}
