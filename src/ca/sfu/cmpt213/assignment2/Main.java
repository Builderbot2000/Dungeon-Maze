package ca.sfu.cmpt213.assignment2;

import ca.sfu.cmpt213.assignment2.model.Direction;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        Handler handler = new Handler(inputScanner);
        handler.debug();
        handler.runGame();
    }
}
//added some changes