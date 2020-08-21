package ca.sfu.cmpt213.assignment2.model.entities;

import ca.sfu.cmpt213.assignment2.model.Coordinates;

// Kevin Tang (301357455 | kta76@sfu.ca)
// Oliver Yalcın Wells (301350814 | oliveryalcin@hotmail.co.uk)

/**
 * Abstract Entity class parent of Hero and Monster, serves as a basis for all entities.
 */
public abstract class Entity implements Comparable<Entity> {

    private boolean isAlive;
    private Coordinates position; // Entity's position on level map
    private String symbol; // Placeholder symbol
    private final String entityType; // Type that defines what this entity is
    private final int priority; // The lower the number, the earlier it gets resolved
    private final int id; // Unique identifier for each individual entity

    public Entity(int x, int y, String symbol, String entityType, int priority, int id) {
        this.symbol = symbol;
        this.entityType = entityType;
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

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getEntityType() {
        return entityType;
    }

    public int getId() {
        return id;
    }

    public void update() {
    }

    @Override
    public int compareTo(Entity o) {
        return Integer.compare(priority, o.priority);
    }
}
