package ca.sfu.cmpt213.assignment2;

import ca.sfu.cmpt213.assignment2.model.Hero;
import ca.sfu.cmpt213.assignment2.model.Level;

import java.util.Scanner;

/**
 * Sets up text menu and gameplay environment.
 * Manages user inputs and handles them accordingly
 */

public class Handler {
    Level level;
    Scanner scanner;
    Hero hero;

    Handler(Scanner in) {
        scanner = in;
        this.level = new Level();
        this.hero = new Hero();
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
                        " #: Wall" +
                        " @: You (a hero)" +
                        " !: Monster" +
                        " $: Power" +
                        ".: Unexplored space" +
                        "MOVES:" +
                        " Use W (up), A (left), S (down) and D (right) to move." +
                        " (You must press enter after each move)";
        System.out.println(output);
    }

    public void setUpUI() {
        System.out.println(this.level.toString());
        System.out.println("Enter your move [WASD?]:");
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

    private static void enableDebugMode() {
        /*
            Don't remember how we decided to implement this
            so I left the method type void
         */
    }
}
