package ca.sfu.cmpt213.assignment2.userInterface;

import ca.sfu.cmpt213.assignment2.model.enumerators.Direction;
import ca.sfu.cmpt213.assignment2.model.Handler;
import ca.sfu.cmpt213.assignment2.model.entities.Entity;
import ca.sfu.cmpt213.assignment2.model.entities.Monster;

import java.util.Scanner;

/**
 * Initializes the UI and manages user input in relation to the game
 * and the various mechanics of the Objects that it uses.
 */
public class UserInterface {

    private static final Scanner scanner = new Scanner(System.in);

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
                " Press Q to quit the game or press ? to bring up the starting menu.\n";
        System.out.println(output);
    }

    /**
     * Prints various in-game statistics.
     * @param handler The current game Handler.
     */
    public static void printStats(Handler handler) {
        System.out.println("Total number of monsters that needs to be killed: " + handler.getWinCondition());
        System.out.println("Number of powers currently in possession: " + handler.getHero().getPowerCount());
        int survivorCount = 0;
        for (Entity entity : handler.getEntityList()) {
            if (entity.getSymbol().equals("!")) survivorCount++;
        }
        System.out.println("Number of monsters alive: " + survivorCount);
    }

    /**
     * Main game input and display loop.
     * @param handler The current game Handler.
     */
    public static void runGame(Handler handler) {
        while (true) {

            // Lose Condition
            if (!handler.getHero().isAlive()) {
                System.out.println("You perished in battle and was eaten by a monster. (or multiple monsters)");
                handler.getLevel().getTileAtCoordinates(handler.getHero().getPosition()).getInhabitants().get(0).setSymbol("X");
                handler.revealMap();
                break;
            }

            // Win Condition
            if (handler.getHero().getKillCount() >= handler.getWinCondition()) {
                System.out.println("You won! You have conquered the dungeon maze.");
                handler.revealMap();
                break;
            }

            // Move monsters according to directions given by AI
            for (int i = 1; i < handler.getEntityList().size(); i++) {
                if (handler.getEntityList().get(i).getSymbol().equals("!")) {
                    Monster currentMonster = ((Monster) handler.getEntityList().get(i));
                    handler.moveEntity(currentMonster, currentMonster.getAIDirection(handler.getLevel().getMap().clone()));
                }
            }

            // Print level and stats, open console for entry
            System.out.println(handler.getLevel().toString());
            UserInterface.printStats(handler);

            // Process user entry
            boolean pass = false;
            while (!pass) {
                System.out.println("Enter your move - [W|A|S|D] or [?|M|C]:");
                String entry = scanner.nextLine();
                String message = null;
                entry = entry.toUpperCase();
                switch (entry) {
                    case "W" -> {
                        message = handler.moveEntity(handler.getHero(), Direction.NORTH);
                        pass = true;
                    }
                    case "A" -> {
                        message = handler.moveEntity(handler.getHero(), Direction.WEST);
                        pass = true;
                    }
                    case "S" -> {
                        message = handler.moveEntity(handler.getHero(), Direction.SOUTH);
                        pass = true;
                    }
                    case "D" -> {
                        message = handler.moveEntity(handler.getHero(), Direction.EAST);
                        pass = true;
                    }
                    case "?" -> {
                        UserInterface.helpMenu();
                        pass = false;
                    }
                    case "M" -> {
                        handler.revealMap();
                        System.out.println(handler.getLevel().toString());
                        pass = false;
                    }
                    case "C" -> {
                        handler.setWinCondition(1);
                        pass = false;
                    }
                    case "`" -> {
                        handler.debug();
                        pass = false;
                    }
                    default -> {
                        System.out.println("Invalid move. Please enter just A (left), S (down), D (right), W (up), or ? (help), M (show map), C (cheat).");
                        pass = false;
                    }
                }
                if (message != null) System.out.println(message);
            }
            handler.revealTiles(handler.getHero());
        }
    }
}
