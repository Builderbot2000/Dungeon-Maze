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

    public static Direction opposite(Direction direction) {
        switch (direction) {
            case NORTH -> {
                return SOUTH;
            }
            case WEST -> {
                return EAST;
            }
            case SOUTH -> {
                return NORTH;
            }
            case EAST -> {
                return WEST;
            }
            default -> {
                return null;
            }
        }
    }

}
