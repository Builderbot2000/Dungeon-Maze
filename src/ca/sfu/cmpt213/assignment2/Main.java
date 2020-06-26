package ca.sfu.cmpt213.assignment2;

import ca.sfu.cmpt213.assignment2.model.Handler;
import ca.sfu.cmpt213.assignment2.userInterface.UserInterface;

// Kevin Tang (301357455 | kta76@sfu.ca)
// Oliver Yalcın Wells (301350814 | oliveryalcin@hotmail.co.uk)

/**
 * The Main method through which the game is initialized.
 */
public class Main {

    /**
     * default false, change to true for more visually pleasing levels
     */
    public static final boolean BETTER_GRAPHICS = true;

    public static void main(String[] args) {
        Handler handler = new Handler();
        UserInterface.runGame(handler);
    }
}