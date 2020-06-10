package ca.sfu.cmpt213.assignment2.ui;

import ca.sfu.cmpt213.assignment2.model.Tile;

/**
 * Creates the map where in which gameplay will take place
 */

public class Level {

    private static final int WIDTH = 20;
    private static final int HEIGHT = 15;
    private Tile[][] map = new Tile[WIDTH][HEIGHT];

    //we can actually ommit the Tile.Terrain.wall or empty by just moving the enum to a seperate file....
    private void createChamber() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 15; j++) {
                if (i == 0)
                    map[i][j].setTerrain(Tile.Terrain.wall);
                else if (j == 0) {
                    map[i][j].setTerrain(Tile.Terrain.wall);
                } else if (j == 14 || i == 19) {
                    map[i][j].setTerrain(Tile.Terrain.wall);
                } else
                    map[i][j].setTerrain(Tile.Terrain.empty);
            }

        }

    }

    public Tile[][] getMap() {

        return map;
    }

    public void setMap(Tile[][] map) {
        this.map = map;
    }
}
