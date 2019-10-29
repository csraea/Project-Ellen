package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;

public class Wrench extends BreakableTool implements Usable {

    public Wrench() {
        super(2);
        Animation animation = new Animation("sprites/wrench.png");
        setAnimation(animation);
    }

    @Override
    public void useWith(Actor actor) {
        if(actor.equals(new DefectiveLight())) super.useWith(actor);
        
    }
}
