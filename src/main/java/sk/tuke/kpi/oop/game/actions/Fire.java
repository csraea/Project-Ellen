package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;

public class Fire<A extends Armed> extends AbstractAction<A> {

    private Armed actor;
    private boolean firstTime;

    public Fire(){
        firstTime = true;
    }

    @Override
    public void setActor(@Nullable A actor) {
        this.actor = actor;
    }

    @Override
    public void execute(float deltaTime) {
        if (isDone() || actor == null) {
            setDone(true);
            return;
        }

        if (firstTime) {
            Fireable fireable = actor.getFirearm().fire();
            if (fireable == null) {
                setDone(true);
                return;
            }
            firstTime = false;

            fireable.getAnimation().setRotation(Direction.fromAngle(actor.getAnimation().getRotation()).getAngle());
            actor.getScene().addActor(fireable, actor.getPosX() + 8, actor.getPosY() + 8);
            new Move<Fireable>(Direction.fromAngle(actor.getAnimation().getRotation()), 9999999999f).scheduleFor(fireable);
        }
        setDone(true);

    }

}
