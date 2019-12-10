package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.ActorFactory;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.*;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.characters.ShootingAlien;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Destroyable.Barrel;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;
//import sk.tuke.kpi.oop.game.openables.FinalDoor;
//import sk.tuke.kpi.oop.game.openables.LockedDoor;
//import sk.tuke.kpi.oop.game.openables.TrapDoor;
//import sk.tuke.kpi.oop.game.weapons.Template.Magazine;


public class Map implements SceneListener {

    private Ripley ellen;

    @Override
    public void sceneInitialized(Scene scene) {
        ellen = scene.getFirstActorByType(Ripley.class);
        scene.follow(ellen);
        scene.getGame().pushActorContainer(ellen.getBackpack());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> scene.getGame().getOverlay().drawText("Ripley Died!", 100, 100));
        scene.getGame().pushActorContainer(ellen.getBackpack());

    }

    @Override
    public void sceneUpdating(Scene scene) {
        ellen.showRipleyState();
    }

    @Override
    public void sceneCreated(Scene scene) {

    }

    public static class Factory implements ActorFactory {

        Reactor reactor;
        @Nullable
        @Override
        public Actor create(String type, String name) {
            if (name.equals("ellen"))                   return new Ripley();
            if (name.equals("Vertical door"))           return new Door("door", Door.Orientation.VERTICAL);
//            if (name.equals("Vertical door locked"))    return new LockedDoor("door", Door.Orientation.VERTICAL);
//            if (name.equals("Vertical trap door"))     return new TrapDoor("door", Door.Orientation.VERTICAL);
            if (name.equals("Horizontal door"))         return new Door("door", Door.Orientation.HORIZONTAL);
//            if (name.equals("access card"))             return new AccessCard();
//            if (name.equals("Final door"))              return new FinalDoor("Final door", Door.Orientation.HORIZONTAL);
//            if (name.equals("Horizontal trap door"))    return new TrapDoor("door", Door.Orientation.HORIZONTAL);
//            if (name.equals("ventilator"))              return new Ventilator();
            if (name.equals("energy"))                  return new Energy();
            if (name.equals("ammo"))                    return new Ammo();
            if (name.equals("alien"))                   return new Alien();
            if (name.equals("shooting alien"))          return new ShootingAlien();
            if (name.equals("alien mother"))            return new AlienMother();
            if (name.equals("barrel"))                  return new Barrel();
//            if (name.equals("energy locker"))           return new Locker("energy");
//            if (name.equals("ammo locker"))             return new Locker("ammo");
//            if (name.equals("locker"))                  return new Locker();
//            if (name.equals("card locker"))             return new Locker("card");
//            if (name.equals("food"))                    return new Food();
            if (name.equals("teleport"))                return new Teleport();
            if (name.equals("reactor")){               reactor = new Reactor(); return reactor; }
            if (name.equals("cooler"))                  return new Cooler(reactor);
//            if (name.equals("magazine"))                return new Magazine();
            else
                return null;
        }
    }
}
