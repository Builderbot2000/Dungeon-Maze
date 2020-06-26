package ca.sfu.cmpt213.assignment2.model;

import ca.sfu.cmpt213.assignment2.model.enumerators.Direction;
import ca.sfu.cmpt213.assignment2.model.entities.Entity;

import java.util.ArrayList;
import java.util.Random;

// Kevin Tang (301357455 | kta76@sfu.ca)
// Oliver Yalcın Wells (301350814 | oliveryalcin@hotmail.co.uk)

/**
 * Hosts a number of independent utility methods that streamlines in-game logic.
 */
public class Utility {

    /**
     * Generate coordinates correspondent to direction relative to current position.
     * @param currentCoordinates The position from which the detection will originate.
     * @param direction The direction to be detected.
     * @return Coordinates corresponding to the input direction.
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
     * @param entityList The entityList field of the current Handler.
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
     * @param direction Current direction.
     * @return The opposite direction of current direction.
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
