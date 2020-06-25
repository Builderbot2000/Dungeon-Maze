package ca.sfu.cmpt213.assignment2.model;

import ca.sfu.cmpt213.assignment2.model.entities.Entity;
import ca.sfu.cmpt213.assignment2.model.entities.Hero;
import ca.sfu.cmpt213.assignment2.model.entities.Monster;
import ca.sfu.cmpt213.assignment2.model.entities.Power;
import ca.sfu.cmpt213.assignment2.model.enumerators.Direction;
import ca.sfu.cmpt213.assignment2.model.enumerators.Terrain;
import ca.sfu.cmpt213.assignment2.userInterface.UserInterface;

import java.util.ArrayList;
import java.util.Random;

/**
 * Sets up text menu and gameplay environment.
 * Manages user inputs and handles them accordingly.
 */

public class Handler {

    private final Level level;
    private final Hero hero;
    private static final int MONSTER_COUNT = 3;
    private int winCondition = MONSTER_COUNT;
    private boolean fogOfWar;

    /**
     * The entityList hosts the hero at index 0 and the three monsters at index 1,2,3 respectively.
     */
    ArrayList<Entity> entityList = new ArrayList<>(); //also holds the powers

    /**
     * Creates a game with level, populates level, and sets up control scheme.
     */
    public Handler() {

        // Initialization of scanner, level, and hero
        this.level = new Level();
        this.hero = new Hero(1,1,Utility.generateID(entityList));
        this.spawnEntity(hero);
        this.fogOfWar = true;

        // Spawn three monsters
        this.spawnEntity(new Monster(Level.CHAMBER_WIDTH - 1,1,Utility.generateID(entityList)));
        this.spawnEntity(new Monster(1,Level.CHAMBER_HEIGHT - 1,Utility.generateID(entityList)));
        this.spawnEntity(new Monster(Level.CHAMBER_WIDTH - 1,Level.CHAMBER_HEIGHT - 1,Utility.generateID(entityList)));

        // Spawn one power
        this.scatterPower();

        // Initialize help menu
        UserInterface.helpMenu();
    }

    // Entity Manipulation Methods
    /**
     * Spawns a defined entity based on their position field.
     * @param entity The entity to be spawned.
     * @return True if spawn is successful and false if spawn failed.
     */
    private boolean spawnEntity(Entity entity) {

        boolean success = setEntity(entity,entity.getPosition());
        if (success) this.entityList.add(entity);
        return success;
    }

    /**
     * Sets an entity at a designated location.
     * @param entity The entity to be set.
     * @param newCoordinates Where the entity should be set on the level map.
     * @return True if set is successful and false if set failed.
     */
    private boolean setEntity(Entity entity, Coordinates newCoordinates) {

        // Define original and new coordinates
        int originalX = entity.getPosition().getX(), originalY = entity.getPosition().getY();
        int newX = newCoordinates.getX(), newY = newCoordinates.getY();
        Tile originalTile = this.level.getMap()[originalY][originalX];
        Tile targetTile = this.level.getMap()[newY][newX];

        // New coordinates feasibility check
        if (targetTile.getTerrain() != Terrain.WALL) {

            // Tile transition procedures
            entity.setPosition(newCoordinates);

            originalTile.removeThisInhabitant(entity);
            originalTile.updateTile();

            targetTile.addInhabitant(entity);
            targetTile.updateTile();

            // Reveal tiles if entity is hero
            if (entity.getEntityType().equals("hero")) {
                revealTiles(entity);

                // Trigger overlap resolution
                if (targetTile.getInhabitants().size() > 1) {
                    resolveOverlap(targetTile);
                }
            }
            else if (fogOfWar) {
                targetTile.setVisible(true);
                originalTile.setVisible(false);
            }

            // Remember path if entity is monster
            if (entity.getEntityType().equals("monster")) {
                ((Monster)entity).setPreviousLocation(new Coordinates(originalX,originalY));
            }

            return true;
        }
        else return false;
    }

    /**
     * Moves an entity one unit towards any eight directions.
     * @param entity The entity to be moved.
     * @param direction Any of eight directions defined in the Directions enumerator
     */
    public String moveEntity(Entity entity, Direction direction) {
            if (
                !setEntity(entity, Utility.locateDirection(entity.getPosition(),direction))
                && entity.getEntityType().equals("hero")
            ) {
                return "You can't pass through walls!";
            }
            return null;
    }

    // Game Logic
    /**
     * Determines the result of an entity overlap based on game rules
     * @param tile The tile where the overlap occurs.
     */
    private void resolveOverlap(Tile tile) {

        tile.sortInhabitants();
        ArrayList<Entity> subjects = tile.getInhabitants();
        Hero hero = ((Hero)subjects.get(0));

        for (int i = 1; i < subjects.size(); i++) {
           Entity target = subjects.get(i);
           if (target.getEntityType().equals("power")) {
               hero.setPowerCount(hero.getPowerCount() + 1);
               //noinspection SuspiciousListRemoveInLoop
               subjects.remove(i);
               scatterPower(); // Spawn a power somewhere to replace this one
           }
           else if (target.getEntityType().equals("monster")) {
               hero.setPowerCount(hero.getPowerCount() - 1);
               hero.setKillCount(hero.getKillCount() + 1);

               // Removes monster from tile and cast into temporary storage
               Monster targetMonster = ((Monster)subjects.remove(i));

               // Removes this specific monster from entityList based on id
               // This is so that its presence on handler is gone as well
               Entity targetEntity = null;
               for (Entity entity : this.entityList) {
                   if (entity.getId() == targetMonster.getId()) targetEntity = entity;
               }
               this.entityList.remove(targetEntity);
           }
        }
        hero.update();
    }

    private void scatterPower() {
        // Spawn powers in random locations, keep trying until POWER_COUNT powers have been successfully spawned
        int placementSuccess = 0;
        Random rand = new Random();
        while (placementSuccess < 1) {
            int randomX = rand.nextInt((Level.CHAMBER_WIDTH - 2) - 2) + 2;
            int randomY = rand.nextInt((Level.CHAMBER_HEIGHT - 2) - 2) + 2;
            if (randomX == 1 && randomY == 1) return;
            if (spawnEntity(new Power(randomX, randomY, Utility.generateID(entityList)))) {
                placementSuccess++;
                level.getMap()[randomY][randomX].setVisible(true);
            }
        }
    }

    /**
     * Set eight tiles around an entity to visible.
     * @param entity The entity from which vision is casted.
     */
    public void revealTiles (Entity entity) {

        // Reveal tile that entity is standing on
        this.level.getMap()[entity.getPosition().getY()][entity.getPosition().getX()].setVisible(true);

        // Reveal tiles around the entity in eight directions
        for (Direction direction : Direction.values()) {
            Coordinates targetCoordinates = Utility.locateDirection(entity.getPosition(),direction);
            Tile targetTile = this.level.getMap()[targetCoordinates.getY()][targetCoordinates.getX()];
            targetTile.setVisible(true);
        }
    }

    // Debug Methods
    /**
     * Permanently reveals all tiles of level
     */
    public void revealMap() {
        for (Tile[] tiles : this.level.getMap()) {
            for (Tile tile : tiles) {
                tile.setVisible(true);
            }
        }
        fogOfWar = false;
    }


    /**
     * Enables various overpowered debug features
     */
    public void debug() {

        // Set hero to invincible
        this.hero.setPowerCount(999999);

        // Spawn 20 random monsters
        int placementSuccess = 0;
        while (placementSuccess < 20) {
            int randomX = new Random().nextInt((Level.CHAMBER_WIDTH - 2) - 2) + 2;
            int randomY = new Random().nextInt((Level.CHAMBER_HEIGHT - 2) - 2) + 2;
            if (spawnEntity(new Monster(randomX,randomY,Utility.generateID(entityList)))) placementSuccess ++;
        }
    }

    // Getters and Setters
    public ArrayList<Entity> getEntityList() {
        return entityList;
    }

    public int getWinCondition() {
        return winCondition;
    }

    public Level getLevel() {
        return level;
    }

    public Hero getHero() {
        return hero;
    }

    public void setWinCondition(int winCondition) {
        this.winCondition = winCondition;
    }
}
