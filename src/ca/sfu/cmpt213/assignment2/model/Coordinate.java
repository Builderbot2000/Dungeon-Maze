package ca.sfu.cmpt213.assignment2.model;

/**
 * Coordinate class which is used to map out coordinates in the game space
 */
public class Coordinate {
    private int x, y;

    Coordinate(){
        x = 0;
        y = 0;
    }

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
