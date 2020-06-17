package ca.sfu.cmpt213.assignment2.model.entities;

import ca.sfu.cmpt213.assignment2.model.Entity;

/**
 * Monster class which inherits its fields from Entity.
 * Manages monster objects movement patterns
 */
public class Monster extends Entity {

    public static final String SYMBOL = "!";
    public static final int PRIORITY = 3;

    public Monster(int x, int y, int id) {
        super(x,y,id,SYMBOL,PRIORITY);
    }

    @Override
    public void update() {
        // Needs Implementation
    }
}
