package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {

    private Movable actor;
    private Move<Movable> move;
    private Set<Direction> keys = new HashSet<>();
    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.RIGHT, Direction.EAST),
        Map.entry(Input.Key.LEFT, Direction.WEST)
    );

    public MovableController(Movable actor){
        this.actor = actor;
        move = null;
    }

    @Override
    public void keyPressed(Input.Key key) {
        if (keyDirectionMap.containsKey(key)){
            keys.add(keyDirectionMap.get(key));
            updateMove();
        }
    }

    @Override
    public void keyReleased(Input.Key key) {
        if (keyDirectionMap.containsKey(key)){
            keys.remove(keyDirectionMap.get(key));
            updateMove();
        }
    }

    private void updateMove(){
        Direction buf = Direction.NONE;
        for (Direction direction : keys){
            if (direction != null){
                buf = buf.combine(direction);
            }
        }
        if(keys.isEmpty() || move != null ){
            check();
        }
        if (buf != Direction.NONE){
            move = new Move<>(buf, 999999f);
            negativeCase();
        }
    }

    private void check() {
        move.stop();
    }

    private void negativeCase() {
        move.scheduleFor(actor);
    }
}
