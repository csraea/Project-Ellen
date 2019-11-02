package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class ChainBomb extends TimeBomb {

    public ChainBomb(float deltaTime) {
        super(deltaTime);
    }

    @Override
    public void activate() {
        if(!isActivated()) {
            Animation A_bombActivated = new Animation("sprites/bomb_activated.png", 16, 16, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
            setAnimation(A_bombActivated);
            setState(true);
            new ActionSequence<>(
                new Wait<>(getDeltaTime()),
                new Invoke<>(this::magic),
                new Invoke<>(this::explode),
                new Wait<>(0.46f),
                new Invoke<>(this::disappear)
            ).scheduleFor(this);
        }
    }
    private void magic() {
        List<Actor> l = getScene().getActors();
        if(l.isEmpty()) return;

        Ellipse2D.Float bombRadius = new Ellipse2D.Float((float)getPosX() + ((float)(getWidth()))/2 - 45f, (float)getPosY() + ((float)(getHeight()))/2f - 45f, 90f, 90f);

        for (Actor b : l) {
            if (b instanceof ChainBomb) {
                Rectangle2D bomb = new Rectangle2D.Float(b.getPosX(), b.getPosY(), b.getWidth(), b.getHeight());
                if (bombRadius.intersects(bomb) || bombRadius.contains(bomb)) {
                    ((ChainBomb) b).activate();
                }
            }
        }
    }
}
