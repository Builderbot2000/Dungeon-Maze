package ca.sfu.cmpt213.assignment2.model.entities;

import ca.sfu.cmpt213.assignment2.model.Entity;

/**
 * Monster class which inherits its fields from Entity.
 * Manages monster objects movement patterns
 */
public class Monster extends Entity {

    public static final String SYMBOL = "!";

    public Monster(int x, int y) {
        super(x,y,SYMBOL);
    }

    public void update() {
        // Needs Implementation
    }
}
