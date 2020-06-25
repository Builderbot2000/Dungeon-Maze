package ca.sfu.cmpt213.assignment2.model;

import ca.sfu.cmpt213.assignment2.model.enumerators.Direction;
import ca.sfu.cmpt213.assignment2.model.enumerators.Terrain;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * Creates the map where in which gameplay will take place. Uses DFS and Backtracking to achieve the creation of the Level.
 */
//some local changes
public class Level {

    public static final int
            MAP_WIDTH = 20, MAP_HEIGHT = 15,
            CHAMBER_WIDTH = MAP_WIDTH - 1, CHAMBER_HEIGHT = MAP_HEIGHT - 1,
            CAST_WIDTH = (int)Math.ceil(CHAMBER_WIDTH/2.0), CAST_HEIGHT = (int)Math.ceil(CHAMBER_HEIGHT/2.0);

    private final Tile[][] map = new Tile[MAP_HEIGHT][MAP_WIDTH];
    private int numberOfCellsVisited;
    private final Stack<Tile> mapStack = new Stack<>();
    private final Tile[][] tempMap = new Tile[CAST_HEIGHT][CAST_WIDTH];

    /**
     * Creates the "chamber" where the 20x15 matrix's walls are created. Thus we only work with the left over 18x13 space
     * However this also means while initializing maze we need to use the indexes in loops/recursion as j = (MAP_WIDTH-CHAMBER_WIDTH)/2 and i = (MAP_HEIGHT-CHAMBER_HEIGHT)/2 with
     * the LENGTHS being 18 and 13 respectively. This is to ensure that we are working within the chamber and not modifying the
     * walls of the chamber while using our algorithm to generate the maze
     */
    public Level() {

        // Create map
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++)
                map[i][j] = new Tile();
        }

        // Create cast map
        for (int i = 0; i < CAST_HEIGHT; i++) {
            for (int j = 0; j < CAST_WIDTH; j++)
                tempMap[i][j] = new Tile();
        }

        createChamber();
    }

    private void createMaze(Tile currentTile) {

        /*
        I was changing my code, I need to implement this logic //https://gamedev.stackexchange.com/questions/142524/how-do-you-create-a-perfect-maze-with-walls-that-are-as-thick-as-the-other-tiles
        The reason behind this is, all of the algorithms and the sudo code for them are made with the idea that walls have 0 thickness/width and height. The one that do have thick walls inflate them during the drawing phase
        I believe this stackoverflow link that I added will solve our problems, I also added stuff accordingly
         */
        while (numberOfCellsVisited < CAST_WIDTH * CAST_HEIGHT) {

            // Get coordinates of current tile and set as visited
            int currentX = currentTile.getPosition().getX(), currentY = currentTile.getPosition().getY();
            tempMap[currentY][currentX].setVisited(true);

            // Begin neighbourhood scan
            ArrayList<Integer> neighbours = new ArrayList<>();

            // If a neighbour is not visited and its projected position is within bounds, add it to the list of valid neighbours
            // North neighbour
            if (currentY - 1 >= 0) {
                if (!tempMap[currentY - 1][currentX].isVisited()) //Northern neighbour unvisited
                    neighbours.add(0);
            }
            //Southern neighbour
            if (currentY + 1 < CAST_HEIGHT) { //y index if increased is not greater than height of the chamber
                if (!tempMap[currentY + 1][currentX].isVisited()) //Northern neighbour unvisited
                    neighbours.add(1);
            }
            //Eastern neighbour
            if (currentX + 1 < CAST_WIDTH) { //check edge case
                if (!tempMap[currentY][currentX + 1].isVisited()) { //check if visited
                    neighbours.add(2);
                }
            }
            //Western neighbour
            if (currentX - 1 >= 0) {
                if (!tempMap[currentY][currentX - 1].isVisited()) {
                    neighbours.add(3);
                }
            }
            if (!neighbours.isEmpty()) {

                //generate random number between 0 and 3 for direction
                Random rand = new Random();
                boolean randomizer;
                int myDirection;
                do {
                    myDirection = rand.nextInt(4);
                    randomizer = neighbours.contains(myDirection);
                } while (!randomizer);

                //knock down wall
                switch (myDirection) {
                    case 0 -> { // North
                        tempMap[currentY][currentX].getPathDirection()[0] = true;
                        currentTile = tempMap[currentY - 1][currentX];
                    }
                    case 1 -> { // South
                        tempMap[currentY][currentX].getPathDirection()[1] = true;
                        currentTile = tempMap[currentY + 1][currentX];
                    }
                    case 2 -> { // East
                        tempMap[currentY][currentX].getPathDirection()[2] = true;
                        currentTile = tempMap[currentY][currentX + 1];
                    }
                    case 3 -> { // West
                        tempMap[currentY][currentX].getPathDirection()[3] = true;
                        currentTile = tempMap[currentY][currentX - 1];
                    }
                }

                mapStack.push(currentTile);
                numberOfCellsVisited++;
            }
            else {
                currentTile = mapStack.pop(); //backtrack
            }
        }

        castMaze(tempMap[0][0], -1);
    }

    void castMaze(Tile currentMap, int prevDirection) {
        int y = currentMap.getPosition().getY();
        int x = currentMap.getPosition().getX();

        map[(2 * y) + 1][(2 * x) + 1].setTerrain(Terrain.EMPTY);
        if (currentMap.getPathDirection()[0] && prevDirection != 1) {
            map[(2 * y)][(2 * x) + 1].setTerrain(Terrain.EMPTY);
            castMaze(tempMap[y - 1][x], 0);
        }
        if (currentMap.getPathDirection()[1] && prevDirection != 0) {
            map[(2 * y) + 2][(2 * x) + 1].setTerrain(Terrain.EMPTY);
            castMaze(tempMap[y + 1][x], 1);
        }
        if (currentMap.getPathDirection()[2] && prevDirection != 3) {
            map[(2 * y) + 1][(2 * x) + 2].setTerrain(Terrain.EMPTY);
            castMaze(tempMap[y][x + 1], 2);
        }
        if (currentMap.getPathDirection()[3] && prevDirection != 2) {
            map[(2 * y) + 1][(2 * x)].setTerrain(Terrain.EMPTY);
            castMaze(tempMap[y][x - 1], 3);
        }
    }

    /**
     * Creates an empty chamber with walls on all four edges.
     */
    private void createChamber() {

        // Give all tiles coordinates for map and temp map
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

        for (int y = 0; y < CAST_HEIGHT; y++) {
            for (int x = 0; x < CAST_WIDTH; x++) {
                tempMap[y][x].setPosition(new Coordinates(x, y));
            }
        }

        // Initialize algorithm at starting position (0, 0)
        tempMap[0][0].setVisited(true);
        numberOfCellsVisited = 1; //setting number of cells visited to 1 because I have visited one now!
        mapStack.push(tempMap[0][0]); //Chamber start position
        createMaze(tempMap[0][0]); //Chamber start position

        // Reveal top edge, sides, and bottom edge
        for (Tile tile : map[0]) { tile.setVisible(true); }
        for (Tile[] tiles : map) {
            tiles[0].setVisible(true);
            tiles[CHAMBER_WIDTH].setTerrain(Terrain.WALL);
            tiles[CHAMBER_WIDTH].setVisible(true);
        }
        for (Tile tile : map[CHAMBER_HEIGHT]) { tile.setVisible(true); }

        // Clear bugged walls
        for (int i = 1; i < MAP_HEIGHT - 2; i += 2) map[i][MAP_WIDTH - 2].setTerrain(Terrain.EMPTY);

        // Randomly make regulated holes in walls to create cycles
        Random rand = new Random(); // Preset rng for performance purposes
        for (int i = 1; i < MAP_HEIGHT-1; i++) {
            for (int j = 1; j < MAP_WIDTH - 1; j++) {
                Tile tile = map[i][j];
                if (tile.getTerrain().equals(Terrain.WALL)) {

                    // Neighbourhood Check
                    int neighbourCount = 0, index = 0;
                    boolean[] neighbourhood = new boolean[]{false,false,false,false}; // validity flags

                    for (Direction direction : Direction.cardinals) {
                        Coordinates targetCoordinates = Utility.locateDirection(tile.getPosition(), direction);
                        Tile neighbour = getTileAtCoordinates(targetCoordinates);
                        if (neighbour.getTerrain().equals(Terrain.WALL)) {
                            neighbourCount++;
                            neighbourhood[index] = true;
                        }
                        index ++;
                    }

                    // Corner exclusion test, tests vertical NS and horizontal EW
                    boolean isStraight = false;
                    if (neighbourhood[0] && neighbourhood[1]) isStraight = true;
                    if (neighbourhood[2] && neighbourhood[3]) isStraight = true;

                    if (neighbourCount == 2 && isStraight) {
                        if (rand.nextInt(5) == 4) tile.setTerrain(Terrain.EMPTY);
                    }
                }
            }
        }

        // Check for enclosed spaces
        for (int i = 1; i < MAP_HEIGHT - 2; i++) probe(map[i][MAP_WIDTH - 2],Direction.EAST);
    }

    public Tile[][] getMap() {
        return map;
    }

    public Tile getTileAtCoordinates(Coordinates coordinates) {
        return this.map[coordinates.getY()][coordinates.getX()];
    }

    private boolean probe(Tile tile, Direction previousDirection) {
        int currentX = tile.getPosition().getX();
        int currentY = tile.getPosition().getY();

        if (tile.getTerrain().equals(Terrain.EMPTY)) return false;
        else if (currentX == 0 || currentX == MAP_WIDTH - 1 || currentY == 0 || currentY == MAP_HEIGHT - 1) {
            return true; // Check if tile hits edge wall
        } else {
            for (Direction direction : Direction.cardinals) {
                Tile targetTile = getTileAtCoordinates(Utility.locateDirection(tile.getPosition(),direction));
                if (!direction.equals(previousDirection) && targetTile.getTerrain().equals(Terrain.WALL)) {
                    if (probe(targetTile,Utility.opposite(direction))) {
                        tile.setTerrain(Terrain.EMPTY);
                        return false;
                    }
                }
            }
            return false;
        }
    }

    //1 character is worth 2 spaces
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Tile[] tiles : this.map) {
            StringBuilder line = new StringBuilder();
            for (Tile tile : tiles) {
                if (tile.isVisible()) {
                    if (tile.isInhabited()) {
                        tile.sortInhabitants();
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