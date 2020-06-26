package ca.sfu.cmpt213.assignment2.model.entities;

// Kevin Tang (301357455 | kta76@sfu.ca)
// Oliver Yalcın Wells (301350814 | oliveryalcin@hotmail.co.uk)

/**
 * Hero class that is the player with fields kill count and powerCount.
 * Other fields and methods inherited from Entity
 */
public class Hero extends Entity {

    private int killCount; //number of monsters killed
    private int powerCount; //number of powers collected

    public Hero(int x, int y, int id) {
        super(x, y, "@", "hero", 1, id);
        this.killCount = 0;
        this.powerCount = 0;
    }

    // Getters and Setters
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

    @Override
    public void update() {
        if (powerCount < 0) this.setAlive(false);
    }
}
