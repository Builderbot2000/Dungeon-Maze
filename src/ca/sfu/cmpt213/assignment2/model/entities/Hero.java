package ca.sfu.cmpt213.assignment2.model.entities;

/**
 * Hero class that is the player with fields kill count and powerCount.
 * Other fields and methods inherited from Entity
 */
public class Hero extends Entity{

    private int killCount; //number of monsters killed
    private int powerCount; //number of powers collected
    public static final String SYMBOL = "@";
    public static final int PRIORITY = 1;

    public Hero(int x, int y, int id) {
        super(x,y,id,SYMBOL,PRIORITY);
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

    @Override
    public void update() { if (powerCount < 0) this.setAlive(false); }
}
