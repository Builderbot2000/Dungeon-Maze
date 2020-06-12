package ca.sfu.cmpt213.assignment2.model;

import ca.sfu.cmpt213.assignment2.model.Coordinates;

/**
 * Abstract Entity class parent of Hero and Monster.
 */
public abstract class Entity {

    private boolean isAlive;
    private Coordinates position;
    public String symbol; // Placeholder symbol

    public Entity(int x, int y, String symbol) {
        this.symbol = symbol;
        this.setAlive(true);
        this.setPosition(new Coordinates(x,y));
    }

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
