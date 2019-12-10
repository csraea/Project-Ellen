package sk.tuke.kpi.oop.game;


import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.scenarios.EscapeRoom;
import sk.tuke.kpi.oop.game.scenarios.Map;

public class Main {




    public static void main(String[] args) {
//        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);
//        Game game = new GameApplication(windowSetup);
//
//        Map.Factory factory = new Map.Factory();
//        Scene scene = new World("world", "maps/mission-impossible.tmx", EscapeRoom.Factory);
//        SceneListener sceneListener = new Map();
//
//        game.addScene(scene);
//
//        scene.addListener(sceneListener);
//
//        game.start();
//
//        scene.getInput().onKeyPressed(key -> {
//            if (key == Input.Key.ESCAPE) {
//                game.stop();
//            }
//
//        });

        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);
        Game game = new GameApplication(windowSetup);
        //Map.Factory factory = new Map.Factory();
        Scene scene = new World("world","maps/escape-room/escape-room.tmx",new EscapeRoom.Factory());

        SceneListener sceneListener = new Map();
        game.addScene(scene);
        scene.addListener(sceneListener);
        game.start();
        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);

    }
}
