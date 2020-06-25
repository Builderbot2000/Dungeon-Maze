package ca.sfu.cmpt213.assignment2.model;

/**
 * Coordinate class which is used to map out coordinates in the game space
 */
public class Coordinates {

    private final int x, y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
