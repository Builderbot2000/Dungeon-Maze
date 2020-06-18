package ca.sfu.cmpt213.assignment2.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * Creates the map where in which gameplay will take place
 */

public class Level {

    public static final int MAP_WIDTH = 20, MAP_HEIGHT = 15, CHAMBER_WIDTH = 19, CHAMBER_HEIGHT = 14;
    private Tile[][] map = new Tile[MAP_HEIGHT][MAP_WIDTH];
    private int numberOfCellsVisited;
    private Stack<Tile> mapStack = new Stack<>();
    private final Tile[][] tempMap = new Tile[CHAMBER_HEIGHT / 2][CHAMBER_WIDTH / 2]; //https://gamedev.stackexchange.com/questions/142524/how-do-you-create-a-perfect-maze-with-walls-that-are-as-thick-as-the-other-tiles

    /**
     * Creates the "chamber" where the 20x15 matrix's walls are created. Thus we only work with the left over 18x13 space
     * However this also means while initializing maze we need to use the indexes in loops/recursion as j = (MAP_WIDTH-CHAMBER_WIDTH)/2 and i = (MAP_HEIGHT-CHAMBER_HEIGHT)/2 with
     * the LENGTHS being 18 and 13 respectively. This is to ensure that we are working within the chamber and not modifying the
     * walls of the chamber while using our algorithm to generate the maze
     */
    public Level() {
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++)
                map[i][j] = new Tile();
        }
        for (int i = 0; i < CHAMBER_HEIGHT / 2; i++) {
            for (int j = 0; j < CHAMBER_WIDTH / 2; j++)
                tempMap[i][j] = new Tile();
        }
        createChamber();
    }

    private void initializeTempMap(Tile currentTile) {
        /*
        I was changing my code, I need to implement this logic //https://gamedev.stackexchange.com/questions/142524/how-do-you-create-a-perfect-maze-with-walls-that-are-as-thick-as-the-other-tiles
        The reason behind this is, all of the algorithms and the sudo code for them are made with the idea that walls have 0 thickness/width and height. The one that do have thick walls inflate them during the drawing phase
        I believe this stackoverflow link that I added will solve our problems, I also added stuff accordingly
         */
        int yIndex;
        int xIndex;

        while (numberOfCellsVisited < (((CHAMBER_WIDTH-1) * (CHAMBER_HEIGHT)) / 4)) {
            yIndex = currentTile.getPosition().getY();
            xIndex = currentTile.getPosition().getX();
            System.out.println("y index" + yIndex);
            System.out.println("x index" + xIndex);
            tempMap[currentTile.getPosition().getY()][currentTile.getPosition().getX()].setVisited(true);

            ArrayList<Integer> neighbours = new ArrayList<>();

            //creating a set of unvisited neighbours
            // North neighbour
            if (yIndex - 1 >= 0) {
                if (!tempMap[currentTile.getPosition().getY() - 1][currentTile.getPosition().getX()].getVisited()) //Northern neighbour unvisited
                    neighbours.add(0);


            }
            //Southern neighbour
            if (yIndex + 1 < CHAMBER_HEIGHT / 2) { //y index if increased is not greater than height of the chamber
                if (!tempMap[currentTile.getPosition().getY() + 1][currentTile.getPosition().getX()].getVisited()) //Northern neighbour unvisited
                    neighbours.add(1);
            }
            //Eastern neighbour
            if (xIndex + 1 < CHAMBER_WIDTH / 2) { //check edge case
                if (!tempMap[currentTile.getPosition().getY()][currentTile.getPosition().getX() + 1].getVisited()) { //check if visited
                    neighbours.add(2);
                }
            }
            //Western neighbour
            if (xIndex - 1 >= 0) {
                if (!tempMap[currentTile.getPosition().getY()][currentTile.getPosition().getX() - 1].getVisited()) {
                    neighbours.add(3);
                }
            }
            if (!neighbours.isEmpty()) {
                //generate random number between 0 and 3 for direction
                Random rand = new Random();
                int upperBound = 4;
                boolean randomizer;
                int myDirection;
                do {
                    myDirection = rand.nextInt(upperBound);
                    randomizer = neighbours.contains(myDirection);
                } while (!randomizer);
                //knock down wall
                switch (myDirection) {
                    case 0 -> { //North
                        tempMap[currentTile.getPosition().getY()][currentTile.getPosition().getX()].pathDirection[0] = true;
                        mapStack.push(tempMap[currentTile.getPosition().getY() - 1][currentTile.getPosition().getX()]); //push northern neighbour
                        currentTile = tempMap[currentTile.getPosition().getY() - 1][currentTile.getPosition().getX()];
                    }
                    case 1 -> { //South
                        tempMap[currentTile.getPosition().getY()][currentTile.getPosition().getX()].pathDirection[1] = true;
                        mapStack.push(tempMap[currentTile.getPosition().getY() + 1][currentTile.getPosition().getX()]); //push southern neighbour
                        currentTile = tempMap[currentTile.getPosition().getY() + 1][currentTile.getPosition().getX()];
                    }
                    case 2 -> { //East
                        tempMap[currentTile.getPosition().getY()][currentTile.getPosition().getX()].pathDirection[2] = true;
                        mapStack.push(tempMap[currentTile.getPosition().getY()][currentTile.getPosition().getX() + 1]); //push western neighbour
                        currentTile = tempMap[currentTile.getPosition().getY()][currentTile.getPosition().getX() + 1];
                    }
                    case 3 -> { //West
                        tempMap[currentTile.getPosition().getY()][currentTile.getPosition().getX()].pathDirection[3] = true;
                        mapStack.push(tempMap[currentTile.getPosition().getY()][currentTile.getPosition().getX() - 1]); //push eastern neighbour
                        currentTile = tempMap[currentTile.getPosition().getY()][currentTile.getPosition().getX() - 1];
                    }
                }
                numberOfCellsVisited++;
                System.out.println(numberOfCellsVisited + " is the number of cells visited");
            } else {

                currentTile = mapStack.pop(); //backtrack
            }
        }

        initializeChamber(tempMap[0][0], -1);
    }

    void initializeChamber(Tile currentMap, int prevDirection) {
        int y = currentMap.getPosition().getY();
        int x = currentMap.getPosition().getX();

        map[(2 * y) + 1][(2 * x) + 1].setTerrain(Terrain.EMPTY);
        if (currentMap.pathDirection[0] && prevDirection != 1) {
            map[(2 * y)][(2 * x) + 1].setTerrain(Terrain.EMPTY);
            initializeChamber(tempMap[y - 1][x], 0);
        }
        if (currentMap.pathDirection[1] && prevDirection != 0) {
            map[(2 * y) + 2][(2 * x) + 1].setTerrain(Terrain.EMPTY);
            initializeChamber(tempMap[y + 1][x], 1);
        }
        if (currentMap.pathDirection[2] && prevDirection != 3) {
            map[(2 * y) + 1][(2 * x) + 2].setTerrain(Terrain.EMPTY);
            initializeChamber(tempMap[y][x + 1], 2);
        }
        if (currentMap.pathDirection[3] && prevDirection != 2) {
            map[(2 * y) + 1][(2 * x)].setTerrain(Terrain.EMPTY);
            initializeChamber(tempMap[y][x - 1], 3);
        }

    }

    /**
     * Creates an empty chamber with walls on all four edges.
     */
    private void createChamber() {
        // Fill top edge with walls
        for (Tile tile : map[0]) {
            tile.setTerrain(Terrain.WALL);
            tile.setVisible(true);
        }

        // Fill sides with walls
        for (Tile[] tiles : map) {
            tiles[0].setTerrain(Terrain.WALL);
            tiles[0].setVisible(true);
            tiles[MAP_WIDTH - 1].setTerrain(Terrain.WALL);
            tiles[MAP_WIDTH - 1].setVisible(true);
        }

        // Fill bottom edge with walls
        for (Tile tile : map[MAP_HEIGHT - 1]) {
            tile.setTerrain(Terrain.WALL);
            tile.setVisible(true);
        }

        // Give all tiles coordinates
        int CurrentY = 0;
        for (Tile[] tiles : map) {
            int CurrentX = 0;
            for (Tile tile : tiles) {
                tile.setPosition(new Coordinates(CurrentX, CurrentY));
                tile.setTerrain(Terrain.WALL);
                CurrentX++;
            }
            CurrentY++;
        }

        for (int y = 0; y < CHAMBER_HEIGHT / 2; y++) {
            for (int x = 0; x < CHAMBER_WIDTH / 2; x++) {
                tempMap[y][x].setPosition(new Coordinates(x, y));
            }
        }

        tempMap[0][0].setVisited(true);
        numberOfCellsVisited = 1; //setting number of cells visited to 1 because I have visited one now!
        mapStack.push(tempMap[0][0]); //Chamber start position
        initializeTempMap(tempMap[0][0]); //Chamber start position
    }


    public Tile[][] getMap() {
        return map;
    }

    public void setMap(Tile[][] map) {
        this.map = map;
    }


    //1 character is worth 2 spaces
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Tile[] tiles : this.map) {
            StringBuilder line = new StringBuilder();
            for (Tile tile : tiles) {
                if (tile.getVisible()) {
                    if (tile.getIsInhabited()) {
                        line.append(tile.getInhabitants().get(0).getSymbol()).append(" ");
                    } else {
                        Terrain terrain = tile.getTerrain();
                        if (terrain == Terrain.EMPTY) {
                            line.append("  ");
                        } else {
                            line.append("# ");
                        }
                    }
                } else {
                    line.append(". ");
                }
            }
            line.append("\n");
            output.append(line);
        }
        return output.toString();
    }
}
