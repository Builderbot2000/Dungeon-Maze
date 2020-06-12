package ca.sfu.cmpt213.assignment2;

import ca.sfu.cmpt213.assignment2.model.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Sets up text menu and gameplay environment.
 * Manages user inputs and handles them accordingly
 */

public class Handler {

    private Scanner scanner;
    private Level level;
    private Hero hero;
    private static int monsterCount = 3;

    /**
     * The entityList hosts the hero at index 0 and the three monsters at index 1,2,3 respectively.
     */
    ArrayList<Entity> entityList = new ArrayList<>();

    Handler(Scanner in) {
        scanner = in;
        this.level = new Level();

        // Spawn hero and three monsters
        this.hero = new Hero(1,1);
        spawnEntity(hero);
        spawnEntity(new Monster(Level.MAP_WIDTH - 2,1));
        spawnEntity(new Monster(1,Level.MAP_HEIGHT - 2));
        spawnEntity(new Monster(Level.MAP_WIDTH - 2,Level.MAP_HEIGHT - 2));

        helpMenu();
    }

    /*
        Set all methods to private as we don't have to call any of them
        inside out main (besides setupUI?)
    */
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
        System.out.println("Total number of monsters to be killed: " + monsterCount);
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
                    running = false;
                }
                default -> System.out.println("Invalid input! Input can only be W|A|S|D or Q.");
            }
        }
    }

    public void spawnEntity(Entity entity) {
        entityList.add(entity);
        setEntity(entity,entity.getPosition());
    }

    public void setEntity(Entity entity, Coordinates newCoordinates) {

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
            originalTile.update();
            targetTile.addInhabitant(entity);
            targetTile.update();

            // Trigger overlap resolution
            if (targetTile.getInhabitants().size() > 1) {
                resolveOverlap(targetTile.getInhabitants());
            }
        }
        else System.out.println("You can't pass through walls!");
    }

    public void moveEntity(Entity entity, Direction direction) {

        // Generate new coordinates based on movement
        int originalX = entity.getPosition().getX(), originalY = entity.getPosition().getY();
        int newX = originalX, newY = originalY;
        switch (direction) {
            case NORTH -> newY = originalY - 1;
            case WEST -> newX = originalX - 1;
            case SOUTH -> newY = originalY + 1;
            case EAST -> newX = originalX + 1;
        }
        System.out.println("Moving " + direction);
        setEntity(entity,new Coordinates(newX,newY));
    }

    private static void resolveOverlap(ArrayList<Entity> entityList) {
        System.out.println("Resolve Overlap!");
    }

    public void enableDebugMode() {

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
