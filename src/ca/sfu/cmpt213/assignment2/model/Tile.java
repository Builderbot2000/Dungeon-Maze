package ca.sfu.cmpt213.assignment2.model;

import ca.sfu.cmpt213.assignment2.model.entities.Entity;
import ca.sfu.cmpt213.assignment2.model.enumerators.Terrain;

import java.util.ArrayList;
import java.util.Collections;

// Kevin Tang (301357455 | kta76@sfu.ca)
// Oliver Yalcın Wells (301350814 | oliveryalcin@hotmail.co.uk)

/**
 * Stores information in regards to what an individual tile holds (Monster,Hero,empty,wall)
 * Is used to create and later on manage the Level while playing the game.
 */

public class Tile {

    private Coordinates position;
    private Terrain terrain;
    private Boolean isVisible, isVisited, isInhabited;
    private final ArrayList<Entity> inhabitants = new ArrayList<>();
    private final boolean[] pathDirection = new boolean[4];

    public Tile() {
        terrain = Terrain.EMPTY;
        isVisible = false;
        isInhabited = false;
        isVisited = false;
    }

    // Getters and Setters
    public Coordinates getPosition() {
        return position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    public Boolean isVisited() {
        return isVisited;
    }

    public void setVisited(Boolean visited) {
        this.isVisited = visited;
    }

    public Terrain getTerrain() {
        return this.terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Boolean isVisible() {
        return this.isVisible;
    }

    public void setVisible(Boolean visible) {
        this.isVisible = visible;
    }

    public Boolean isInhabited() {
        return isInhabited;
    }

    public ArrayList<Entity> getInhabitants() {
        return this.inhabitants;
    }

    public void addInhabitant(Entity newInhabitant) {
        this.inhabitants.add(newInhabitant);
    }

    public void removeThisInhabitant(Entity entity) {
        this.inhabitants.remove(entity);
    }

    /**
     * Automatically sets isInhabited flag
     */
    public void updateTile() {
        this.isInhabited = this.inhabitants.size() != 0;
    }

    /**
     * Sort inhabitants based on their priority, low values go first.
     */
    public void sortInhabitants() {
        Collections.sort(inhabitants);
    }

    /**
     * Used in Depth First Search algorithm to determine direction of path for casting
     * @return direction of path that runs through this tile
     */
    public boolean[] getPathDirection() {
        return pathDirection;
    }
}
