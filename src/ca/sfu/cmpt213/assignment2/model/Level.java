package ca.sfu.cmpt213.assignment2.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Creates the map where in which gameplay will take place
 */

public class Level {

    private static final int MAP_WIDTH = 15, MAP_HEIGHT = 20, CHAMBER_WIDTH = 13, CHAMBER_HEIGHT = 18;
    private Tile[][] map = new Tile[MAP_HEIGHT][MAP_WIDTH];

    /**
     * Creates the "chamber" where the 20x15 matrix's walls are created. Thus we only work with the left over 18x13 space
     * However this also means while initializing maze we need to use the indexes in loops/recursion as j = (MAP_WIDTH-CHAMBER_WIDTH)/2 and i = (MAP_HEIGHT-CHAMBER_HEIGHT)/2 with
     * the LENGTHS being 18 and 13 respectively. This is to ensure that we are working within the chamber and not modifying the
     * walls of the chamber while using our algorithm to generate the maze
     */
    //we can actually omit the Tile.Terrain.wall or empty by just moving the enum to a seperate file....
    public Level() {
        createChamber();
        initializeChamber();
    }

    private void initializeChamber() {
        //implement DFS to occupy maze


        //in second pass check if there are more than 2 empty spaces around
    }

    private void createChamber() {
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {
                if (i == 0)
                    map[i][j].setTerrain(Tile.Terrain.wall);
                else if (j == 0) {
                    map[i][j].setTerrain(Tile.Terrain.wall);
                } else if (j == CHAMBER_WIDTH + 1 || i == CHAMBER_HEIGHT + 1) {
                    map[i][j].setTerrain(Tile.Terrain.wall);
                } else
                    map[i][j].setTerrain(Tile.Terrain.empty);
            }

        }

    }

    public Tile[][] getMap() { return this.map; }

    public void setMap(Tile[][] map) { this.map = map; }

    public String toString(ArrayList<Entity> entityList) {
        return "";
    }
}
