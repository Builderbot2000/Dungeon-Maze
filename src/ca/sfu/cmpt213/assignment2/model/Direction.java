package ca.sfu.cmpt213.assignment2.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
//used for ai so monster can go in random directions
public enum Direction {
    NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST;

    // Utility array of eight directions
    public static final Direction[] directions = new  Direction[] {
        NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST
    };

    // Utility array of four cardinal directions
    public static final Direction[] cardinals = new Direction[] {
        NORTH, SOUTH, EAST, WEST
    };

    public static Direction randomDirection()  {
        return directions[new Random().nextInt(directions.length)];
    }

    public static Direction randomCardinal() {
        return cardinals[new Random().nextInt(cardinals.length)];
    }
}
