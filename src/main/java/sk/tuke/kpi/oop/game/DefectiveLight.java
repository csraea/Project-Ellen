package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light implements Repairable{

    public DefectiveLight() {
        super();
    }

    public void setLightDefect() {
        int randomNum = (int) (Math.random() * 10) + 1;
        if(randomNum == 1) {
            super.setPowered(false);
        } else {
            super.setPowered(true);
        }
    }

    @Override
    public boolean repair(){
        super.setPowered(true);
        //new Wait<>(10);
        return true;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::setLightDefect)).scheduleFor(this);
    }
}
