package sk.tuke.kpi.oop.game.characters.Prototype;

import sk.tuke.kpi.gamelib.framework.AbstractActor;

public abstract class CloneableActor extends AbstractActor implements Cloneable {

    public CloneableActor(String name){
        super(name);
    }

    public CloneableActor(){
        super();
    }

    public Object clone() {
        Object clone = null;

        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return clone;
    }

    public String getName(){
        return super.getName();
    }

}
