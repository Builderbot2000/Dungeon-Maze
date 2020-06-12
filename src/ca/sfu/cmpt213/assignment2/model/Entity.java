package ca.sfu.cmpt213.assignment2.model;

/**
 * Abstract Entity class parent of Hero and Monster.
 */
public abstract class Entity {

    private boolean isAlive;
    private Coordinate position;
    public String symbol = "E";

    public void move(String input) {
        /*
        switch (input) {
            case "N" -> {
                int expectedY = position.getY() + 1;
            }
        }
        */
    }

    // Getters and Setters
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
}
