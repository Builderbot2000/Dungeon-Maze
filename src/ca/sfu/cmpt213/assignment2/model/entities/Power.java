package ca.sfu.cmpt213.assignment2.model.entities;

/**
 * Power class which inherits its fields from the Entity class.
 * Is used as a power which the Hero "obtain". Entity inheritance can be explained by the need
 * to compare Power,Monster and Hero priorities in the "Level"
 */
public class Power extends Entity {

    public Power(int x, int y, int id) {
        super(x,y,"$","power",3,id);
    }
}
