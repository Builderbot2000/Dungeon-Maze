package ca.sfu.cmpt213.assignment2.model;

import java.util.Stack;

/**
 * Creates the map where in which gameplay will take place
 */

public class Level {

    public static final int MAP_WIDTH = 15, MAP_HEIGHT = 20, CHAMBER_WIDTH = 14, CHAMBER_HEIGHT = 19;
    private Tile[][] map = new Tile[MAP_HEIGHT][MAP_WIDTH];
    private static int numberOfCellsVisited;
    private static final Stack<Tile> mapStack = new Stack<>();
    // private final Tile[][] tempMap = new Tile[CHAMBER_HEIGHT/2][CHAMBER_WIDTH/2]; //https://gamedev.stackexchange.com/questions/142524/how-do-you-create-a-perfect-maze-with-walls-that-are-as-thick-as-the-other-tiles

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
        createChamber();
    }

    private void initializeChamber(Tile currentTile) { /*
        int yIndex;
        int xIndex;

        I was changing my code, I need to implement this logic //https://gamedev.stackexchange.com/questions/142524/how-do-you-create-a-perfect-maze-with-walls-that-are-as-thick-as-the-other-tiles
        The reason behind this is, all of the algorithms and the sudo code for them are made with the idea that walls have 0 thickness/width and height. The one that do have thick walls inflate them during the drawing phase
        I believe this stackoverflow link that I added will solve our problems, I also added stuff accordingly

        while (numberOfCellsVisited < (CHAMBER_WIDTH * CHAMBER_HEIGHT)/4)
        {
            yIndex = currentTile.getCurrentPosition().getY();
            xIndex = currentTile.getCurrentPosition().getX();
            map[currentTile.getCurrentPosition().getY()][currentTile.getCurrentPosition().getX()].setVisited(true);

            ArrayList<Integer> neighbours = new ArrayList<>();

            //creating a set of unvisited neighbours
            // North neighbour
            if (yIndex - 1 > 1) {
                if (!map[currentTile.getCurrentPosition().getY() - 1][currentTile.getCurrentPosition().getX()].getVisited()) //Northern neighbour unvisited
                    neighbours.add(0);
            }
            //Southern neighbour
            if (!(yIndex + 1 > CHAMBER_HEIGHT)) { //y index if increased is not greater than height of the chamber
                if (!map[currentTile.getCurrentPosition().getY() + 1][currentTile.getCurrentPosition().getX()].getVisited()) //Northern neighbour unvisited
                    neighbours.add(1);
            }
            //Eastern neighbour
            if (!(xIndex + 1 >= CHAMBER_WIDTH)) {
                if (!map[currentTile.getCurrentPosition().getY()][currentTile.getCurrentPosition().getX() + 1].getVisited()) {
                    neighbours.add(2);
                }
            }
            //Western neighbour
            if (xIndex - 1 > 1) {
                if (!map[currentTile.getCurrentPosition().getY()][currentTile.getCurrentPosition().getX() - 1].getVisited()) {
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
                    case 0 -> {
                        map[currentTile.getCurrentPosition().getY() - 1][currentTile.getCurrentPosition().getX()].setTerrain(Terrain.EMPTY);
                        mapStack.push(map[currentTile.getCurrentPosition().getY() - 1][currentTile.getCurrentPosition().getX()]); //push northern neighbour
                        currentTile = map[currentTile.getCurrentPosition().getY() - 1][currentTile.getCurrentPosition().getX()];
                    }
                    case 1 -> {
                        map[currentTile.getCurrentPosition().getY() + 1][currentTile.getCurrentPosition().getX()].setTerrain(Terrain.EMPTY);
                        mapStack.push(map[currentTile.getCurrentPosition().getY() + 1][currentTile.getCurrentPosition().getX()]); //push northern neighbour
                        currentTile = map[currentTile.getCurrentPosition().getY() + 1][currentTile.getCurrentPosition().getX()];
                    }
                    case 2 -> {
                        map[currentTile.getCurrentPosition().getY()][currentTile.getCurrentPosition().getX() + 1].setTerrain(Terrain.EMPTY);
                        mapStack.push(map[currentTile.getCurrentPosition().getY()][currentTile.getCurrentPosition().getX() + 1]); //push northern neighbour
                        currentTile = map[currentTile.getCurrentPosition().getY()][currentTile.getCurrentPosition().getX() + 1];
                    }
                    case 3 -> {
                        map[currentTile.getCurrentPosition().getY()][currentTile.getCurrentPosition().getX() - 1].setTerrain(Terrain.EMPTY);
                        mapStack.push(map[currentTile.getCurrentPosition().getY()][currentTile.getCurrentPosition().getX() - 1]); //push northern neighbour
                        currentTile = map[currentTile.getCurrentPosition().getY()][currentTile.getCurrentPosition().getX() - 1];
                    }
                }
                numberOfCellsVisited++;
            } else {
                currentTile = mapStack.pop(); //backtrack
            }
        }*/
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
        for (Tile[] tiles: map) {
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
                tile.setPosition(new Coordinates(CurrentX,CurrentY));
                CurrentX ++;
            }
            CurrentY ++;
        }

        /*
        filling in the entire matrix with walls where in which I can break them down

        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                if (y == 0) { //make walls surrounding chamber visible
                    map[y][x].setVisible(true);
                    map[y][x].setVisited(true);
                } else if (x == 0) { //make walls surrounding chamber visible
                    map[y][x].setVisible(true);
                    map[y][x].setVisited(true);
                } else if (x == CHAMBER_WIDTH || y == CHAMBER_HEIGHT) { //make walls surrounding chamber visible
                    map[y][x].setVisible(true);
                    map[y][x].setVisited(true);
                } else {
                    map[y][x].setVisible(true);
                    map[y][x].setVisited(false);
                }
                map[y][x].setTerrain(Terrain.WALL); // fill everywhere with walls
                map[y][x].setCurrentPosition(new Coordinate(x, y)); //get all coordinates
            }
        }
        numberOfCellsVisited = 1; //setting number of cells visited to 1 because I have visited one now!
        map[1][1].setVisited(true);
        mapStack.push(map[1][1]); //Chamber start position
         */
        // initializeChamber(map[1][1]); //Chamber start position
    }

    public Tile[][] getMap() {
        return map;
    }

    public void setMap(Tile[][] map) {
        this.map = map;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Tile[] tiles : this.map) {
            StringBuilder line = new StringBuilder();
            for (Tile tile : tiles) {
                if (tile.getVisible()) {
                    if (tile.getIsInhabited()) {
                        line.append(tile.getInhabitants().get(0).getSymbol()).append(" ");
                    }
                    else {
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
