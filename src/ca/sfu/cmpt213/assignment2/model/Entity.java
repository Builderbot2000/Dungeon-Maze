package ca.sfu.cmpt213.assignment2.model;

/**
 * Abstract Entity class parent of Hero and Monster.
 */
public abstract class Entity {

    private boolean isAlive;
    private Coordinates position;
    public String symbol = "E";

    // Getters and Setters
    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Coordinates getPosition() {
        return position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }
}
