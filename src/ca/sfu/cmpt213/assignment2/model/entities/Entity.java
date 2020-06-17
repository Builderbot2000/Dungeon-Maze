package ca.sfu.cmpt213.assignment2.model.entities;

import ca.sfu.cmpt213.assignment2.model.Coordinates;

/**
 * Abstract Entity class parent of Hero and Monster.
 */
public abstract class Entity implements Comparable<Entity> {

    private boolean isAlive;
    private Coordinates position;
    private String symbol; // Placeholder symbol
    private int priority; // The lower the number, the earlier it gets resolved
    private int id; // Unique identifier for each individual entity

    public Entity(int x, int y, int id, String symbol, int priority) {
        this.symbol = symbol;
        this.priority = priority;
        this.id = id;
        this.setAlive(true);
        this.setPosition(new Coordinates(x, y));
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

    public String getSymbol() {
        return symbol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void update() {
    }

    @Override
    public int compareTo(Entity o) {
        return Integer.compare(priority, o.priority);
    }
}
