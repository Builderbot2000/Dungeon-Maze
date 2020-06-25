package ca.sfu.cmpt213.assignment2.model;

import ca.sfu.cmpt213.assignment2.model.enumerators.Direction;
import ca.sfu.cmpt213.assignment2.model.entities.Entity;

import java.util.ArrayList;
import java.util.Random;

/**
 * Generates unique ID's for various Entities (Hero,Monster,Power) Updates Coordinates of the various Objects in the Level
 * and Handles their movements.
 */
public class Utility {

    /**
     * // Generate new coordinates based on direction
     *
     * @param currentCoordinates the coordinates from which the detection will originate
     * @param direction          the direction to be detected
     * @return coordinates of the detected direction
     */
    public static Coordinates locateDirection(Coordinates currentCoordinates, Direction direction) {

        int originalX = currentCoordinates.getX(), originalY = currentCoordinates.getY();
        int newX = originalX, newY = originalY;
        switch (direction) {
            case NORTH -> newY = originalY - 1;
            case WEST -> newX = originalX - 1;
            case SOUTH -> newY = originalY + 1;
            case EAST -> newX = originalX + 1;
            case NORTHEAST -> {
                newY = originalY - 1;
                newX = originalX + 1;
            }
            case NORTHWEST -> {
                newY = originalY - 1;
                newX = originalX - 1;
            }
            case SOUTHEAST -> {
                newY = originalY + 1;
                newX = originalX + 1;
            }
            case SOUTHWEST -> {
                newY = originalY + 1;
                newX = originalX - 1;
            }
        }
        return new Coordinates(newX, newY);
    }

    /**
     * Generates a random ID for each entity so that they could be identified.
     * individually in the entities list. The ID is always unique from existing IDs
     * in the entities list.
     *
     * @return A random integer ID.
     */
    public static int generateID(ArrayList<Entity> entityList) {
        int randomID = 0;
        boolean isUnique = false;
        while (!isUnique) {
            randomID = new Random().nextInt();
            isUnique = true;
            for (Entity entity : entityList) {
                if (randomID == entity.getId()) {
                    isUnique = false;
                    break;
                }
            }
        }
        return randomID;
    }

    /**
     * Finds the direction opposite to current direction.
     *
     * @param direction current direction
     * @return the opposite direction of current direction
     */
    public static Direction opposite(Direction direction) {
        switch (direction) {
            case NORTH -> {
                return Direction.SOUTH;
            }
            case WEST -> {
                return Direction.EAST;
            }
            case SOUTH -> {
                return Direction.NORTH;
            }
            case EAST -> {
                return Direction.WEST;
            }
            default -> {
                return null;
            }
        }
    }
}
