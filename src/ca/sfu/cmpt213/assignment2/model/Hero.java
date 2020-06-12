package ca.sfu.cmpt213.assignment2.model;

/**
 * Hero class that is the player with fields kill count and powerCount.
 * Other fields and methods inherited from Entity
 */
public class Hero extends Entity {

    private int killCount; //number of monsters killed
    private int powerCount; //number of powers collected

    public Hero(int x, int y) {
        symbol = "@";
        this.setAlive(true);
        this.setPosition(new Coordinates(x,y));
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
