package ca.sfu.cmpt213.assignment2.model.entities;

public class Power extends Entity {

    public static final String SYMBOL = "$";
    public static final int PRIORITY = 3;

    public Power(int x, int y, int id) {
        super(x, y, id, SYMBOL,PRIORITY);
    }
}
