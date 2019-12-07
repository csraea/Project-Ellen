package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.scenarios.FirstSteps;

public class Main {
    public static void main(String[] args) {

        FirstSteps scenario = new FirstSteps();
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 1200, 1200);
        Game game = new GameApplication(windowSetup);
        Scene scene = new World("world");
        scene.addListener(scenario);
        game.addScene(scene);

        game.start();
        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);

    }
}
