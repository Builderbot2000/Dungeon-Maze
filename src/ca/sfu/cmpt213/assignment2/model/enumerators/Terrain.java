package ca.sfu.cmpt213.assignment2.model.enumerators;

// Kevin Tang (301357455 | kta76@sfu.ca)
// Oliver Yalcın Wells (301350814 | oliveryalcin@hotmail.co.uk)

/**
 * Terrain class which is used to determine whether a Tile object is Empty or if it is a wall. This Enumerator is used
 * extensively within the DFS Algorithm implemented inside Level and the toString methods which help visualize the Level.
 */
public enum Terrain {
    EMPTY, WALL
}