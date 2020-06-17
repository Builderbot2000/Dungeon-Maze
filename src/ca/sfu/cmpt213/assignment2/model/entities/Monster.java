package ca.sfu.cmpt213.assignment2.model.entities;

import ca.sfu.cmpt213.assignment2.Handler;
import ca.sfu.cmpt213.assignment2.model.Coordinates;
import ca.sfu.cmpt213.assignment2.model.Direction;
import ca.sfu.cmpt213.assignment2.model.Terrain;
import ca.sfu.cmpt213.assignment2.model.Tile;

/**
 * Monster class which inherits its fields from Entity.
 * Manages monster objects movement patterns
 */
public class Monster extends Entity {

    public static final String SYMBOL = "!";
    public static final int PRIORITY = 3;
    private Coordinates previousLocation;

    public Monster(int x, int y, int id) {
        super(x,y,id,SYMBOL,PRIORITY);
        previousLocation = new Coordinates(0,0);
    }

    public void setPreviousLocation(Coordinates location) { previousLocation = location; }

    public Direction getAIDirection (Tile[][] map) {
        while (true) {
            Direction direction = Direction.randomDirection();
            if (
                    direction != Direction.NORTHEAST &&
                    direction != Direction.NORTHWEST &&
                    direction != Direction.SOUTHEAST &&
                    direction != Direction.SOUTHWEST
            ) {
                Coordinates targetCoordinates = Handler.locateDirection(this, direction);
                Tile targetTile = map[targetCoordinates.getY()][targetCoordinates.getX()];
                if (targetTile.getTerrain().equals(Terrain.EMPTY) && !targetTile.getPosition().equals(previousLocation)) {
                    return direction;
                }
            }
        }
    }
}
