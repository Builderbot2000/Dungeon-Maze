package ca.sfu.cmpt213.assignment2.model.enumerators;

/**
 * Terrain class which is used to determine wether a Tile object is Empty or if it is a wall. This Enumerator is used
 * extensively within the DFS Algorithm implemented inside Level and the toString methods which help visualize the Level.
 */
public enum Terrain {
    EMPTY, WALL
}