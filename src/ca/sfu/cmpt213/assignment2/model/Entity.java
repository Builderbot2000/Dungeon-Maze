package ca.sfu.cmpt213.assignment2.model;

/**
 * Abstract Entity class parent of Hero and Monster.
 */
public abstract class Entity {

    private boolean isAlive;
    private Coordinate position;

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public void move(String input) {

    }
}
