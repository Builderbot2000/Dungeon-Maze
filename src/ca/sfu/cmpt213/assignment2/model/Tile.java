package ca.sfu.cmpt213.assignment2.model;

import java.util.ArrayList;

/**
 * Stores information in regards to what the an individual tile holds (Monster,Hero,empty,wall)
 */

public class Tile {


    public enum Terrain {   //lets discuss this, however feel free to play around with the location of these enums according to how you code and just let me know
        /*
         maybe we can consider making a separate .java file
         or we can make it static and just use Tile.Terrain to access it in UI if we need to?
      */
        empty, wall
    }
    private Coordinate currentPosition = new Coordinate();
    private Terrain terrain;
    private Boolean isVisible;
    private Boolean isVisited;
    private ArrayList<Entity> inhabitant = new ArrayList<>(); // this doesn't need to be an arraylist?
    private Boolean[] pathDirection = new Boolean[4];
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

    public ArrayList<Entity> getInhabitant() {
        return this.inhabitant;
    }

    public void setInhabitant(ArrayList<Entity> inhabitant) {
        this.inhabitant = inhabitant;
    }
}
