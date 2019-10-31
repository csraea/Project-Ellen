package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class ChainBomb extends TimeBomb {

    private List<Actor> l;

    public ChainBomb(float deltaTime) {
        super(deltaTime);
    }

    @Override
    public void activate() {
        if(!isActivated) {
            Animation A_bombActivated = new Animation("sprites/bomb_activated.png", 16, 16, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
            setAnimation(A_bombActivated);
            this.isActivated = true;
            new ActionSequence<>(
                new Wait<>(deltaTime),
                new Invoke<>(this::explode),
                new Wait<>(0.46f),
                new Invoke<>(this::magic),
                new Invoke<>(this::disappear)
            ).scheduleFor(this);
        }
    }
    private void magic() {
        Scene scene = getScene();
        l = scene.getActors();

        Ellipse2D.Float bombR = new Ellipse2D.Float(getPosX(), getPosY() + getHeight(), 50, 50);

        for (Actor b : l) {
            if (b instanceof ChainBomb) {
                Rectangle2D bomb = new Rectangle2D.Float(b.getPosX(), b.getPosY() + b.getHeight(), b.getWidth(), b.getHeight());
                if (bombR.intersects(bomb)) {
                    ((ChainBomb) b).activate();
                }
            }
        }
    }
}
