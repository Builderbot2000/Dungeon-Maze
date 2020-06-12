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
        this.hero = new Hero();
        entityList.add(this.hero);
        for (int i = 0; i < monsterCount; i++) {
            entityList.add(new Monster());
        }
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
                " (You must press enter after each move)\n";
        System.out.println(output);
    }

    public void printStats() {
        System.out.println("Total number of monsters to be killed: " + monsterCount);
        System.out.println("Number of powers currently in possession: " + this.hero.getPowerCount());
        int survivorCount = this.entityList.size() - 1;
        System.out.println("Number of monsters alive: " + survivorCount);
    }

    public void setUpUI() {
        System.out.println(this.level.toString());
        this.printStats();
        System.out.println("Enter your move [W|A|S|D?]:");
        String entry = scanner.nextLine();

        switch (entry) {
            case "N" -> {
                System.out.println("Moving north...");
                hero.move("N");
            }
            case "W" -> {
                System.out.println("Moving west...");
                hero.move("W");
            }
            case "S" -> {
                System.out.println("Moving south...");
                hero.move("S");
            }
            case "E" -> {
                System.out.println("Moving east...");
                hero.move("E");
            }
            default -> System.out.println("Invalid input! Input can only be W|A|S|D.");
        }
    }

    private static void resolveOverlap() {

    }

    private void enableDebugMode() {
        this.hero.setPowerCount(999999);
    }
}
