package ca.sfu.cmpt213.assignment2.model;

// Kevin Tang (301357455 | kta76@sfu.ca)
// Oliver Yalcın Wells (301350814 | oliveryalcin@hotmail.co.uk)

/**
 * Coordinate class which is used to define position on the game map
 */
public class Coordinates {

    private final int X, Y;

    public Coordinates(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    // Getters
    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    /**
     * Debug method that displays the coordinates current position
     * @return current position of the coordinate
     */
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + X +
                ", y=" + Y +
                '}';
    }
}
