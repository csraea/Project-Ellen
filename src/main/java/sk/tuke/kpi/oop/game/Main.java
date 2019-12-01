package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.scenarios.FirstSteps;

public class Main {
    public static void main(String[] args) {

        FirstSteps scenario = new FirstSteps();

        // nastavenie okna hry: nazov okna a jeho rozmery
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 1200, 1200);

        // vytvorenie instancie hernej aplikacie
        // pouzijeme implementaciu rozhrania `Game` triedou `GameApplication`
        Game game = new GameApplication(windowSetup);

        // vytvorenie sceny pre hru
        // pouzijeme implementaciu rozhrania `Scene` triedou `World`
        Scene scene = new World("world");
        //Scene scene = new InspectableScene(new World("world"), List.of("sk.tuke.kpi"));
        scene.addListener(scenario);

        Ripley ellen = new Ripley();
        scene.addActor(ellen, 0, 0);

        MovableController movableController = new MovableController(ellen);
        // pridanie sceny do hry
        game.addScene(scene);
        scene.getInput().registerListener(movableController);
        // spustenie hry
        game.start();

        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);

    }
}
