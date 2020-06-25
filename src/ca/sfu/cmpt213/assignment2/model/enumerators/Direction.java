package ca.sfu.cmpt213.assignment2.model.enumerators;

//used for ai so monster can go in random directions

/**
 * Is used for multiple purposes. Such as managing direction input from the User and also managing
 * the monster AI feautred within the game.
 */
public enum Direction {
    NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST;

    // Utility array of four cardinal directions
    public static final Direction[] cardinals = new Direction[] {
        NORTH, SOUTH, EAST, WEST
    };
}
