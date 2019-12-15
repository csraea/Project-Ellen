package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.oop.game.Direction;

import java.util.Random;

public class RandomlyMoving {

    public RandomlyMoving(){

    }

    private Direction randomdirect() {
        Direction direction = Direction.NONE;
        switch (new Random().nextInt(8)) {
            case 0:
                direction = Direction.EAST;
                break;
            case 1:
                direction = Direction.WEST;
                break;
            case 2:
                direction = Direction.NORTH;
                break;
            case 3:
                direction = Direction.SOUTH;
                break;
            case 4:
                direction = Direction.NORTHEAST;
                break;
            case 5:
                direction = Direction.NORTHWEST;
                break;
            case 6:
                direction = Direction.SOUTHEAST;
                break;
            case 7:
                direction = Direction.SOUTHWEST;
                break;
        }
        return direction;
    }
}
