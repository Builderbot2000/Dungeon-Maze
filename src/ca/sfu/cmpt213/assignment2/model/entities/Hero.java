package ca.sfu.cmpt213.assignment2.model.entities;

import ca.sfu.cmpt213.assignment2.model.Entity;

/**
 * Hero class that is the player with fields kill count and powerCount.
 * Other fields and methods inherited from Entity
 */
public class Hero extends Entity {

    private int killCount; //number of monsters killed
    private int powerCount = 0; //number of powers collected
    public static final String SYMBOL = "@";

    public Hero(int x, int y) {
        super(x,y,SYMBOL);
        this.killCount = 0;
        this.powerCount = 0;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }

    public int getPowerCount() {
        return powerCount;
    }

    public void setPowerCount(int powerCount) {
        this.powerCount = powerCount;
    }
}
