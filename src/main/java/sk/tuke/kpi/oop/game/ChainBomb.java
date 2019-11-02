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
                new Invoke<>(this::explode),
                new Wait<>(0.46f),
                new Invoke<>(this::magic),
                new Invoke<>(this::disappear)
            ).scheduleFor(this);
        }
    }
    private void magic() {
        float qX = getPosX() + ((float)getWidth())/2;
        float qY = getPosY() + ((float)getHeight())/2;
        Ellipse2D.Float bombR = new Ellipse2D.Float(qX - 50, qY + 50, 100, 100);
        List<Actor> l = getScene().getActors();
        for (Actor b : l) {
            if (b instanceof ChainBomb) {
                Rectangle2D bomb = new Rectangle2D.Float(b.getPosX() + (float)(b.getWidth())/2 - 50 - (float)b.getWidth()/2, (b.getPosY() + b.getHeight())/2 +, b.getWidth(), b.getHeight());
                if (bombR.intersects(bomb)) {
                    ((ChainBomb) b).activate();
                }
            }
        }
    }
}
