package sk.tuke.kpi.oop.game.scenarios;

import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.items.FireExtinguisher;
import sk.tuke.kpi.oop.game.items.Hammer;


public class FirstSteps implements SceneListener {
    private Ripley ripley;
    private Energy energy;
    private Ammo ammo;

    @Override
    public void sceneInitialized(Scene scene) {
        ripley = new Ripley();
        scene.addActor(ripley, 0, 0);
        ripley.setEnergy(30);
        energy = new Energy();
        ammo = new Ammo();
        Hammer hammer = new Hammer();
        FireExtinguisher extinguisher = new FireExtinguisher();
//        backpack = new Backpack()
        scene.addActor(energy, 100, 100);
        scene.addActor(ammo, 100, 0);
        ripley.getContainer().add(extinguisher);
        //ripley.getContainer().add(energy);
        ripley.getContainer().add(hammer);
        //ripley.getContainer().shift();

        //new Use<>(energy).scheduleOn(ripley);
        KeeperController collectorController = new KeeperController(ripley);
        scene.getInput().registerListener(collectorController);


        MovableController key = new MovableController(ripley);
        //MovableController key = new MovableController(scene.getActorFactory().create("Ripley", "ellen")));
        scene.getInput().registerListener(key);
//        new When<Energy>(
//                action -> ripley.intersects(energy),
//                new Use<>(energy).scheduleFor(ripley)
//                ).scheduleFor(ripley);
//
//        new ActionSequence<>(
//                new Wait<>(3),
//                new Invoke<Collectible>(ripley.getContainer().shift())
//                ).scheduleOn(ripley);

    }

    @Override
    public void sceneUpdating(Scene scene) {
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int topOffset = GameApplication.STATUS_LINE_OFFSET;
        int yTextPos = windowHeight - topOffset;
        scene.getGame().getOverlay().drawText("Energy: "+ ripley.getEnergy(), 100, yTextPos);
        scene.getGame().getOverlay().drawText("Ammo: "+ ripley.getRemainingAmmo(), 250, yTextPos);

        if(ripley.intersects(energy)){
            new Use<>(energy).scheduleFor(ripley);
            //ripley.getContainer().add(energy);
            scene.removeActor(energy);
            //scene.dispose();
        }
        if(ripley.intersects(ammo)){
            new Use<>(ammo).scheduleFor(ripley);
           // ripley.getContainer().add(ammo);
            scene.removeActor(ammo);
        }
        scene.getGame().pushActorContainer(ripley.getContainer());
    }
}

