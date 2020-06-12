package ca.sfu.cmpt213.assignment2.model.entities;

import ca.sfu.cmpt213.assignment2.model.Entity;

public class Power extends Entity {

    public static final String SYMBOL = "$";

    public Power(int x, int y) {
        super(x, y, SYMBOL);
    }
}
