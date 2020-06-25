package ca.sfu.cmpt213.assignment2.model;

import ca.sfu.cmpt213.assignment2.model.entities.Entity;
import ca.sfu.cmpt213.assignment2.model.enumerators.Terrain;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Stores information in regards to what the an individual tile holds (Monster,Hero,empty,wall)
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

    public void updateTile() {
        // Automatically sets isInhabited flag
        this.isInhabited = this.inhabitants.size() != 0;
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
     * Sort inhabitants based on their overlap resolution priority
     */
    public void sortInhabitants() {
        Collections.sort(inhabitants);
    }

    public boolean[] getPathDirection() {
        return pathDirection;
    }
}
