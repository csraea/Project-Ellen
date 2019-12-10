package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.ActorFactory;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

public class EscapeRoom implements SceneListener {

    private Ripley ellen;

    @Override
    public void sceneInitialized(Scene scene) {
        ellen = scene.getFirstActorByType(Ripley.class);
        scene.follow(ellen);



        scene.getGame().pushActorContainer(ellen.getBackpack());
//        int windowHeight = Objects.requireNonNull(scene).getGame().getWindowSetup().getHeight();
//        int topOffset = GameApplication.STATUS_LINE_OFFSET;
//        int yTextPos = windowHeight - topOffset;
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> scene.getGame().getOverlay().drawText("Ripley Died!", 100, 100));
        scene.getGame().pushActorContainer(ellen.getBackpack());


    }

    @Override
    public void sceneUpdating(Scene scene) {
        ellen.showRipleyState();
    }

    @Override
    public void sceneCreated(Scene scene) {
        //scene.getMessageBus().subscribe(World.ACTOR_ADDED_TOPIC, );
    }

    public static class Factory implements ActorFactory {

        @Nullable
        @Override
        public Actor create(String type, String name) {
            if (name.equals("ellen"))           return new Ripley();
            if (name.equals("Vertical door"))   return new Door("door",Door.Orientation.VERTICAL);
            if (name.equals("Horizontal door")) return new Door("door",Door.Orientation.HORIZONTAL);
            //if (name.equals("access card"))     return new AccessCard();
            //if (name.equals("ventilator"))      return new Ventilator();
            if (name.equals("energy"))          return new Energy();
            if (name.equals("ammo"))            return new Ammo();
            if (name.equals("alien"))           return new Alien();
            if (name.equals("alien mother"))    return new AlienMother();
            else
                return null;
        }
    }
}
