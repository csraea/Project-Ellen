package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.MovableController;

public class FirstSteps implements SceneListener {

    private Ripley ripley;

    @Override
    public void sceneInitialized(@NotNull Scene scene){
        ripley = new Ripley();
        scene.addActor(ripley, 0, 0);
        MovableController movableController = new MovableController(ripley);
        scene.getInput().registerListener(movableController);
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene){
        int textPosition = scene.getGame().getWindowSetup().getHeight() - GameApplication.STATUS_LINE_OFFSET;
        scene.getGame().getOverlay().drawText(" Energy: " + ripley.getEnergy() + "| Ammo: " + ripley.getAmmo(), 100, textPosition);
    }
}
