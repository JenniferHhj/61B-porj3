package byow.Core;

import byow.TileEngine.TETile;

import java.awt.*;
import java.util.Random;

import static byow.TileEngine.Tileset.*;

public class MapGenerator {
    public static final int WIDTH = Engine.WIDTH;
    public static final int HEIGHT = Engine.HEIGHT;
    private Random random;
    private TETile[][] world;
    private Point[] roomStartLocations;

    public MapGenerator(long seed) {
        random = new Random(seed);
        world = new TETile[WIDTH][HEIGHT];
//        int numHallways = WIDTH * HEIGHT / 50;
        int numHallways = 69;
        roomStartLocations = new Point[numHallways];

        //initialize all tiles in world to nothing
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = NOTHING;
            }
        }
        drawHallways(numHallways);
        drawRooms();
        drawWalls();
    }

    public TETile[][] generate() {
        return world;
    }

    public Random getRandomNumberGenerator() {
        return random;
    }

    private void drawHallways(int numHallways) {
        int horizontalOrVertical = random.nextInt(2);
        boolean horizontal;
        if (horizontalOrVertical == 0) {            //horizontal
            horizontal = true;
        } else {
            horizontal = false;
        }

        int firstXPos, firstYPos, length;
        if (horizontal) {
            firstXPos = random.nextInt(2) + 1;
            firstYPos = random.nextInt(HEIGHT - 2) + 1;
            length = random.nextInt(WIDTH - 1 - firstXPos) + 1;
        } else {
            firstXPos = random.nextInt(2) + 1;
            firstYPos = random.nextInt(2) + 1;
            length = random.nextInt(HEIGHT - 1 - firstYPos) + 1;
        }
        Point newPosition = new Point(firstXPos, firstYPos);
        HallwayObject newHallway = new HallwayObject(length, newPosition, horizontal, random);

        for (int i = 0; i < numHallways; i++) {
//            System.out.println("hallway " + i + " in " + newHallway.getPosition());
            newHallway.drawIn(world);
            newHallway = newHallway.makeNewHallway();
            roomStartLocations[i] = newHallway.getPosition();
        }
        newHallway.drawIn(world);
    }
    private void drawRooms() {
        for (int i = 0; i < roomStartLocations.length; i++) {
            RoomObject newRoom = new RoomObject(roomStartLocations[i], random);
            newRoom.drawIn(world);
        }
    }

    private boolean needWall(int x, int y) {
        if (world[x][y] == FLOOR) {
            if (x == 0 || x == WIDTH - 1 || y == 0 || y == HEIGHT - 1) {
                return true;
            }
        } else if (world[x][y] != FLOOR) {
            if (x - 1 >= 0 && world[x - 1][y] == FLOOR) {
                return true;
            } else if (x + 1 < WIDTH && world[x + 1][y] == FLOOR) {
                return true;
            } else if (y + 1 < HEIGHT && world[x][y + 1] == FLOOR) {
                return true;
            } else if (y - 1 >= 0 && world[x][y - 1] == FLOOR) {
                return true;
            } else if (x - 1 >= 0 && y - 1 >= 0 && world[x - 1][y - 1] == FLOOR) {
                return true;
            } else if (x + 1 < WIDTH && y + 1 < HEIGHT && world[x + 1][y + 1] == FLOOR) {
                return true;
            } else if (x - 1 >= 0 && y + 1 < HEIGHT && world[x - 1][y + 1] == FLOOR) {
                return true;
            } else if (x + 1 < WIDTH && y - 1 >= 0 && world[x + 1][y - 1] == FLOOR) {
                return true;
            }
        }
        return false;
    }
    private void drawWalls() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (needWall(i, j)) {
                    world[i][j] = WALL;
                }
            }
        }
    }
}
