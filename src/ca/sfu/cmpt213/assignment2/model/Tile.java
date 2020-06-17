package ca.sfu.cmpt213.assignment2.model;

import ca.sfu.cmpt213.assignment2.model.entities.Entity;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Stores information in regards to what the an individual tile holds (Monster,Hero,empty,wall)
 */

public class Tile {

    private Coordinates position;
    private Terrain terrain;
    private Boolean isVisible, isVisited, isInhabited;
    private ArrayList<Entity> inhabitants = new ArrayList<>(); // this doesn't need to be an arraylist?
    private Boolean[] pathDirection = new Boolean[4];

    public Tile() {
        terrain = Terrain.EMPTY;
        isVisible = false;
        isInhabited = false;
    }

    // Getters and Setters
    public Coordinates getPosition() {
        return position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    public Boolean getVisited() {
        return isVisited;
    }

    public void setVisited(Boolean visited) {
        this.isVisited = visited;
    }

    public Terrain getTerrain() {
        return this.terrain;
    }

    public void setTerrain(Terrain terrain)
    {
        this.terrain = terrain;
    }

    public Boolean getVisible() {
        return this.isVisible;
    }

    public void setVisible(Boolean visible) {
        this.isVisible = visible;
    }

    public Boolean getIsInhabited() { return isInhabited; }

    public void setIsInhabited(Boolean inhabited) { isInhabited = inhabited; }

    public void updateTile(boolean flag1, boolean flag2) {

        // Automatically sets isInhabited flag
        if (flag1) this.isInhabited = this.inhabitants.size() != 0;

        // Automatically shuffles inhabitants list by priority
        if (flag2) System.out.println("Shuffle!");
    }

    public ArrayList<Entity> getInhabitants() {
        return this.inhabitants;
    }

    public void addInhabitant(Entity newInhabitant) { this.inhabitants.add(newInhabitant); }

    public boolean removeThisInhabitant(Entity entity) { return this.inhabitants.remove(entity); }

    /**
     * Sort inhabitants based on their overlap resolution priority
     */
    public void sortInhabitants() {
        Collections.sort(inhabitants);
    }
}
