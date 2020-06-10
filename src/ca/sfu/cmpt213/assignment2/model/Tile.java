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

    Terrain terrain;
    Boolean isVisible;
    ArrayList<Entity> inhabitant;

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    public ArrayList<Entity> getInhabitant() {
        return inhabitant;
    }

    public void setInhabitant(ArrayList<Entity> inhabitant) {
        this.inhabitant = inhabitant;
    }
}
