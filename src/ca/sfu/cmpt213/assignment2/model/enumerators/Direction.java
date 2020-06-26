package ca.sfu.cmpt213.assignment2.model.enumerators;

// Kevin Tang (301357455 | kta76@sfu.ca)
// Oliver Yalcın Wells (301350814 | oliveryalcin@hotmail.co.uk)

/**
 * Is used for multiple purposes such as managing direction input from the user and the monster AI.
 */
public enum Direction {
    NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST;

    // Utility array of four cardinal directions
    public static final Direction[] cardinals = new Direction[]{
            NORTH, SOUTH, EAST, WEST
    };
}
