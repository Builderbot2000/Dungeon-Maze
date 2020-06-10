package ca.sfu.cmpt213.assignment2.ui;

import ca.sfu.cmpt213.assignment2.model.Tile;

/**
 * Creates the map where in which gameplay will take place
 */

public class Level {

    private static final int WIDTH = 20;
    private static final int HEIGHT = 15;
    private Tile[][] map = new Tile[WIDTH][HEIGHT];

    public Tile[][] getMap() {
        return map;
    }

    public void setMap(Tile[][] map) {
        this.map = map;
    }
}
