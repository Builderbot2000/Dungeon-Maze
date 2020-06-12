package ca.sfu.cmpt213.assignment2.model;

/**
 * Monster class which inherits its fields from Entity.
 * Manages monster objects movement patterns
 */
public class Monster extends Entity {

    public Monster(int x, int y) {
        symbol = "!";
        this.setAlive(true);
        this.setPosition(new Coordinates(x,y));
    }

    public void update() {
        // Needs Implementation
    }
}
