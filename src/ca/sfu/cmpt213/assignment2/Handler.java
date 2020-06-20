package ca.sfu.cmpt213.assignment2;

import ca.sfu.cmpt213.assignment2.model.*;
import ca.sfu.cmpt213.assignment2.model.entities.Entity;
import ca.sfu.cmpt213.assignment2.model.entities.Hero;
import ca.sfu.cmpt213.assignment2.model.entities.Monster;
import ca.sfu.cmpt213.assignment2.model.entities.Power;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Sets up text menu and gameplay environment.
 * Manages user inputs and handles them accordingly.
 */

public class Handler {

    private final Scanner scanner;
    private final Level level;
    private final Hero hero;
    private static final int MONSTER_COUNT = 3;
    private static final int POWER_COUNT = 3;

    /**
     * The entityList hosts the hero at index 0 and the three monsters at index 1,2,3 respectively.
     */
    ArrayList<Entity> entityList = new ArrayList<>(); //also holds the powers

    /**
     * Creates a game with level, populates level, and sets up control scheme.
     * @param in The system input scanner.
     */
    Handler(Scanner in) {

        // Set input scanner
        scanner = in;

        // Create level
        this.level = new Level();

        // Spawn hero
        this.hero = new Hero(1,1,generateID());
        spawnEntity(hero);

        // Spawn three monsters
        spawnEntity(new Monster(Level.CHAMBER_WIDTH - 1,1,generateID()));
        // spawnEntity(new Monster(1,Level.CHAMBER_HEIGHT - 1,generateID()));
        // spawnEntity(new Monster(Level.CHAMBER_WIDTH - 1,Level.CHAMBER_HEIGHT - 1,generateID()));

        // Spawn powers in random locations, keep trying until POWER_COUNT powers have been
        // successfully spawned
        int placementSuccess = 0;
        while (placementSuccess < POWER_COUNT) {
            int randomX = new Random().nextInt((Level.CHAMBER_WIDTH - 2) - 2) + 2;
            int randomY = new Random().nextInt((Level.CHAMBER_HEIGHT - 2) - 2) + 2;
            if (spawnEntity(new Power(randomX,randomY,generateID()))) placementSuccess ++;
        }

        // Initialize help menu
        helpMenu();
    }

    /*
        Set all methods to private as we don't have to call any of them
        inside out main (besides setupUI?)
    */

    // User Interface Methods

    /**
     * Prints instructions for keyboard inputs.
     */
    public static void helpMenu() {
        String output = "DIRECTIONS:\n" +
                " Kill 3 Monsters!\n" +
                "LEGEND:\n" +
                " #: Wall\n" +
                " @: You (a hero)\n" +
                " !: Monster\n" +
                " $: Power\n" +
                " .: Unexplored space\n" +
                "MOVES:\n" +
                " Use W (up), A (left), S (down) and D (right) to move.\n" +
                " (You must press enter after each move)\n" +
                " Press Q to quit the game or press H to bring up the help menu.\n";
        System.out.println(output);
    }

    /**
     * Prints various in-game statistics.
     */
    public void printStats() {
        System.out.println("Total number of monsters to be killed: " + MONSTER_COUNT);
        System.out.println("Number of powers currently in possession: " + this.hero.getPowerCount());
        int survivorCount = 0;
        for (Entity entity: entityList) {
            if (entity.getSymbol().equals("!")) survivorCount ++;
        }
        System.out.println("Number of monsters alive: " + survivorCount);
    }

    /**
     * Game loop.
     */
    public void runGame() {
        boolean running = true;
        while (running) {

            // Move monsters according to directions given by AI
            for (int i = 1; i < this.entityList.size(); i++) {
                if (entityList.get(i).getSymbol().equals("!")) {
                    Monster currentMonster = ((Monster)entityList.get(i));
                    moveEntity(currentMonster,currentMonster.getAIDirection(level.getMap().clone()));
                }
            }

            // Print level and stats, open console for entry
            System.out.println(this.level.toString());
            this.printStats();
            System.out.println("Enter your move - [W|A|S|D] or [Q|H]:");
            String entry = scanner.nextLine();

            // Moves hero based on entry and terminates if dead
            switch (entry) {
                case "W" -> moveEntity(hero,Direction.NORTH);
                case "A" -> moveEntity(hero,Direction.WEST);
                case "S" -> moveEntity(hero,Direction.SOUTH);
                case "D" -> moveEntity(hero,Direction.EAST);
                case "H" -> helpMenu();
                case "Q" -> {
                    System.out.println("Quitting...");
                    hero.setAlive(false);
                }
                default -> System.out.println("Invalid input! Input can only be W|A|S|D or Q.");
            }
            revealTiles(hero);
            running = hero.isAlive();
        }
    }

    // Entity Manipulation Methods

    /**
     * Generates a random ID for each entity so that they could be identified.
     * individually in the entities list. The ID is always unique from existing IDs
     * in the entities list.
     * @return A random integer ID.
     */
    private int generateID() {
        int randomID = 0;
        boolean isUnique = false;
        while (!isUnique) {
            randomID = new Random().nextInt();
            isUnique = true;
            for (Entity entity : this.entityList) {
                if (randomID == entity.getId()) {
                    isUnique = false;
                    break;
                }
            }
        }
        return randomID;
    }

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
            originalTile.updateTile(true,false);

            targetTile.addInhabitant(entity);
            targetTile.updateTile(true,false);

            // Reveal tiles if entity is hero
            if (entity.getSymbol().equals("@")) {
                revealTiles(entity);

                // Trigger overlap resolution
                if (targetTile.getInhabitants().size() > 1) {
                    resolveOverlap(targetTile);
                }
            }

            // Remember path if entity is monster
            if (entity.getSymbol().equals("!")) {
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
    private void moveEntity(Entity entity, Direction direction) {
            if (
                !setEntity(entity,locateDirection(entity.getPosition(),direction))
                && entity.getSymbol().equals("@")
            ) {
                System.out.println("You can't pass through walls!");
            }
    }

    /**
     * // Generate new coordinates based on direction
     * @param currentCoordinates the coordinates from which the detection will originate
     * @param direction the direction to be detected
     * @return coordinates of the detected direction
     */
    public static Coordinates locateDirection(Coordinates currentCoordinates, Direction direction) {

        int originalX = currentCoordinates.getX(), originalY = currentCoordinates.getY();
        int newX = originalX, newY = originalY;
        switch (direction) {
            case NORTH -> newY = originalY - 1;
            case WEST -> newX = originalX - 1;
            case SOUTH -> newY = originalY + 1;
            case EAST -> newX = originalX + 1;
            case NORTHEAST -> { newY = originalY - 1; newX = originalX + 1; }
            case NORTHWEST -> { newY = originalY - 1; newX = originalX - 1; }
            case SOUTHEAST -> { newY = originalY + 1; newX = originalX + 1; }
            case SOUTHWEST -> { newY = originalY + 1; newX = originalX - 1; }
        }
        return new Coordinates(newX,newY);
    }

    /**
     * Set eight tiles around an entity to visible.
     * @param entity The entity from which vision is casted.
     */
    private void revealTiles (Entity entity) {

        // Reveal tile that entity is standing on
        this.level.getMap()[entity.getPosition().getY()][entity.getPosition().getX()].setVisible(true);

        // Reveal tiles around the entity in eight directions
        for (Direction direction : Direction.values()) {
            Coordinates targetCoordinates = locateDirection(entity.getPosition(),direction);
            Tile targetTile = this.level.getMap()[targetCoordinates.getY()][targetCoordinates.getX()];
            targetTile.setVisible(true);
        }
    }

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
           if (target.getSymbol().equals("$")) {
               hero.setPowerCount(hero.getPowerCount() + 1);
               //noinspection SuspiciousListRemoveInLoop
               subjects.remove(i);
           }
           else if (target.getSymbol().equals("!")) {

               // Deal damage to hero and check if dead
               hero.setPowerCount(hero.getPowerCount() - 1);
               hero.update();

               if (hero.isAlive()) {
                   hero.setKillCount(hero.getKillCount() + 1);

                   // Removes monster from tile and cast into temporary storage
                   Monster targetMonster = ((Monster)subjects.remove(i));

                   // Removes this specific monster from entityList based on id
                   // This is so that its presence on handler is gone as well
                   Entity targetEntity = null;
                   for (Entity entity : this.entityList) {
                       if (entity.getId() == targetMonster.getId()) {
                           targetEntity = entity;
                       }
                   }
                   this.entityList.remove(targetEntity);
                   System.out.println("A monster is killed.");
               }
               else System.out.println("You perished in battle.");
           }
           else System.out.println("Entity not recognized!");
        }
    }

    /**
     * Reveals all tiles and makes hero invincible.
     */
    public void debug() {

        // Set all tiles to visible
        for (Tile[] tiles : level.getMap()) {
            for (Tile tile : tiles) {
                tile.setVisible(true);
            }
        }

        // Set hero to invincible
        this.hero.setPowerCount(999999);
    }
}
