package ca.sfu.cmpt213.assignment2;

import ca.sfu.cmpt213.assignment2.model.*;
import ca.sfu.cmpt213.assignment2.model.Entity;
import ca.sfu.cmpt213.assignment2.model.entities.Hero;
import ca.sfu.cmpt213.assignment2.model.entities.Monster;
import ca.sfu.cmpt213.assignment2.model.entities.Power;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Sets up text menu and gameplay environment.
 * Manages user inputs and handles them accordingly
 */

public class Handler {

    private Scanner scanner;
    private Level level;
    private Hero hero;
    private static final int MONSTER_COUNT = 3;
    private static final int POWER_COUNT = 3;

    /**
     * The entityList hosts the hero at index 0 and the three monsters at index 1,2,3 respectively.
     */
    ArrayList<Entity> entityList = new ArrayList<>();

    Handler(Scanner in) {

        // Set input scanner
        scanner = in;

        // Create level
        this.level = new Level();

        // Spawn hero
        this.hero = new Hero(1,1);
        spawnEntity(hero);

        // Spawn three monsters
        spawnEntity(new Monster(Level.MAP_WIDTH - 2,1));
        spawnEntity(new Monster(1,Level.MAP_HEIGHT - 2));
        spawnEntity(new Monster(Level.MAP_WIDTH - 2,Level.MAP_HEIGHT - 2));

        // Spawn powers in random locations, keep trying until POWER_COUNT powers have been
        // successfully spawned
        int placementSuccess = 0;
        while (placementSuccess < POWER_COUNT) {
            int randomX = new Random().nextInt((Level.MAP_WIDTH - 3) - 3) + 3;
            int randomY = new Random().nextInt((Level.MAP_HEIGHT - 3) - 3) + 3;
            if (spawnEntity(new Power(randomX,randomY))) placementSuccess ++;
        }

        // Initialize help menu
        helpMenu();
    }

    /*
        Set all methods to private as we don't have to call any of them
        inside out main (besides setupUI?)
    */

    // User Interface Methods
    public static void helpMenu() {
        String output = "DIRECTIONS:\n" +
                " Kill 3 Monsters!\n" +
                "LEGEND:\n" +
                " #: Wall\n" +
                " @: You (a hero)\n" +
                " !: Monster\n" +
                " $: Power\n" +
                ".: Unexplored space\n" +
                "MOVES:\n" +
                " Use W (up), A (left), S (down) and D (right) to move.\n" +
                " (You must press enter after each move)\n" +
                " Press Q to quit the game or press H to bring up the help menu.\n";
        System.out.println(output);
    }

    public void printStats() {
        System.out.println("Total number of monsters to be killed: " + MONSTER_COUNT);
        System.out.println("Number of powers currently in possession: " + this.hero.getPowerCount());
        int survivorCount = this.entityList.size() - 1;
        System.out.println("Number of monsters alive: " + survivorCount);
    }

    public void setUpUI() {
        boolean running = true;
        while (running) {
            System.out.println(this.level.toString());
            this.printStats();
            System.out.println("Enter your move - [W|A|S|D] or [Q|H]:");
            String entry = scanner.nextLine();

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
    private boolean spawnEntity(Entity entity) {
        boolean success = setEntity(entity,entity.getPosition());
        if (success) entityList.add(entity);
        return success;
    }

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
            originalTile.removeInhabitant(entity);
            originalTile.updateTile(true,false);
            targetTile.addInhabitant(entity);
            targetTile.updateTile(true,false);

            // Reveal tiles if entity is hero
            if (entity.symbol.equals("@")) revealTiles(entity);

            // Trigger overlap resolution
            if (targetTile.getInhabitants().size() > 1) {
                resolveOverlap(targetTile.getInhabitants());
            }

            return true;
        }
        else return false;
    }

    private void moveEntity(Entity entity, Direction direction) {

        System.out.println("Moving " + direction);
        if (!setEntity(entity,locateDirection(entity,direction)) && entity.symbol.equals("@")) {
            System.out.println("You can't pass through walls!");
        }
    }

    /**
     * // Generate new coordinates based on direction
     * @param entity the entity from which the detection will originate
     * @param direction the direction to be detected
     * @return coordinates of the detected direction
     */
    private static Coordinates locateDirection (Entity entity, Direction direction) {

        int originalX = entity.getPosition().getX(), originalY = entity.getPosition().getY();
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

    private void revealTiles (Entity entity) {

        // Reveal tile that entity is standing on
        this.level.getMap()[entity.getPosition().getY()][entity.getPosition().getX()].setVisible(true);

        // Reveal tiles around the entity in eight directions
        for (Direction direction : Direction.values()) {
            Coordinates targetCoordinates = locateDirection(entity,direction);
            Tile targetTile = this.level.getMap()[targetCoordinates.getY()][targetCoordinates.getX()];
            targetTile.setVisible(true);
        }
    }

    private static void resolveOverlap(ArrayList<Entity> entityList) {
        System.out.println("Resolve Overlap!");
    }

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
