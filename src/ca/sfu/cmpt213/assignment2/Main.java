package ca.sfu.cmpt213.assignment2;

import ca.sfu.cmpt213.assignment2.model.Handler;
import ca.sfu.cmpt213.assignment2.userInterface.UserInterface;

/**
 * The Main method where in which the game is initialized via the User Interface.
 */
public class Main {

    public static void main(String[] args) {
        Handler handler = new Handler();
        UserInterface.runGame(handler);
    }
}