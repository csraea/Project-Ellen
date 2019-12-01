package sk.tuke.kpi.oop.game;



public enum Direction {

    NORTH(0, 1), NORTHWEST(-1, 1), NORTHEAST(1,1),
    WEST(-1, 0), EAST(1, 0),
    SOUTH(0, -1), SOUTHWEST(-1, -1), SOUTHEAST(1, -1),
    NONE(0, 0);

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public final int dx, dy;


    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public float getAngle(){
        return (float)Math.toDegrees( Math.atan2((double) dy,  (double) dx))-90;
    }

    public Direction combine(Direction other){
        int x;
        int y;
        if (this == Direction.NONE  ){
            return other;
        }
        if (other == null || other == Direction.NONE){
            return this;
        }

//        if (this.getDx() == 0) {
//            x = other.getDx();
//            y = this.getDy();
//        } else {
//            x = this.getDx();
//            y = other.getDy();
//        }

        x = other.getDx() + this.getDx();

        y = this.getDy() + other.getDy();
        int hx = update(x);
        int hy = update(y);
        Direction help = Direction.NONE;
        for (Direction dir : Direction.values()) {
            if (dir.getDx() == hx && dir.getDy() == hy ){
                help = dir;
            }
        }
        return help;
    }

    public static Direction fromAngle(float angle){
        float helpAngle;
        if (angle>180) {
            helpAngle = angle - 360;

            for (Direction help : Direction.values()) {
                if (help.getAngle() == helpAngle) {
                    return help;
                }
            }
        }else{
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
        if (arg >= 1)  return 1;
        else {
            if (arg <= -1) return -1;
            else return 0;
        }

    }
//    private int updateY(int y){
//        if (y > 1)  y = 1;
//        else {
//            if (y < -1) y = -1;
//        }
//        return y;
//    }
}

