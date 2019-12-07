package sk.tuke.kpi.oop.game;

public enum Direction {
    NORTH(0, 1), NORTHWEST(-1, 1), NORTHEAST(1,1),
    WEST(-1, 0), EAST(1, 0),
    SOUTH(0, -1), SOUTHWEST(-1, -1), SOUTHEAST(1, -1),
    NONE(0, 0);
    public final int dx, dy;

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public float getAngle(){
        return (float)Math.toDegrees(Math.atan2((double) dy,  (double) dx))-90;
    }

    public Direction combine(Direction dir){
        int x, y;
        if (this == Direction.NONE){
            return dir;
        }
        if (dir == null || dir == Direction.NONE){
            return this;
        }

        x = dir.getDx() + this.getDx();
        y = dir.getDy() + this.getDy();

        int hx = update(x);
        int hy = update(y);

        Direction buf = Direction.NONE;
        for (Direction d : Direction.values()) {
            if (d.getDx() == hx && d.getDy() == hy ){
                buf = d;
            }
        }
        return buf;
    }

    public static Direction fromAngle(float angle){
        float helpAngle;
        if (angle > 180) {
            helpAngle = angle - 360;
            for (Direction help : Direction.values()) {
                if (help.getAngle() == helpAngle) {
                    return help;
                }
            }
        } else {
            if (angle == 180) return Direction.SOUTH;
            if (angle == 135) return Direction.SOUTHWEST;
            else {
                for (Direction help : Direction.values()) {
                    if (help.getAngle() == angle) {
                        return help;
                    }
                }
            }
        }
        return Direction.NONE;
    }

    private int update(int arg){
        return (arg >= 1) ? 1 : (arg <= -1) ? -1 : 0;
    }
}

