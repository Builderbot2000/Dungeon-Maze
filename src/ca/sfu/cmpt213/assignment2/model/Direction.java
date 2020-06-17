package ca.sfu.cmpt213.assignment2.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Direction {
    NORTH, EAST, WEST, SOUTH, NORTHEAST, NORTHWEST, SOUTHWEST, SOUTHEAST;

    private static final List<Direction> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Direction randomDirection()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
