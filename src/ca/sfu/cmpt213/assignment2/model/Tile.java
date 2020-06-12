package ca.sfu.cmpt213.assignment2.model;

import java.util.ArrayList;

/**
 * Stores information in regards to what the an individual tile holds (Monster,Hero,empty,wall)
 */

public class Tile {

    private Coordinate currentPosition;
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
    public Coordinate getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Coordinate currentPosition) {
        this.currentPosition = currentPosition;
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

    public ArrayList<Entity> getInhabitants() {
        return this.inhabitants;
    }

    public void addInhabitant(Entity newInhabitant) {
        this.inhabitants.add(newInhabitant);
    }
}
