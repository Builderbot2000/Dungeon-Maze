package ca.sfu.cmpt213.assignment2;

import ca.sfu.cmpt213.assignment2.model.Handler;
import ca.sfu.cmpt213.assignment2.userInterface.UserInterface;

/**
 * The Main method where in which the game is initialized.
 */
public class Main {

    /**
     * default false, change to true for more visually pleasing levels
     */
    public static final boolean BETTER_GRAPHICS = false;

    public static void main(String[] args) {
        Handler handler = new Handler();
        UserInterface.runGame(handler);
    }
}