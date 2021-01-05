package byow.Core;

import byow.TileEngine.TETile;

import java.awt.*;
import java.util.Random;

import static byow.TileEngine.Tileset.FLOOR;

public class HallwayObject implements GridObject {

    private int length;
//    private int width, height;
    private Point position;
    private boolean direction;
    private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;
    private Random random;

    public HallwayObject(int len, Point pos, boolean dir, Random ran) {
        length = len;
        position = pos;
        direction = dir;
        random = ran;
//        Random random = MapGenerator.getRandomNumberGenerator();
//        int leftOrRight, topOrBottom;
//        if (horizontal) {
//            leftOrRight = RandomUtils.uniform(random, 2);
//            if (leftOrRight == 0) {
//                direction = Orientation.LEFT;
//            } else {
//                direction = Orientation.RIGHT;
//            }
//        } else {
//            topOrBottom = RandomUtils.uniform(random, 2);
//            if (topOrBottom == 0) {
//                direction = Orientation.TOP;
//            } else {
//                direction = Orientation.BOTTOM;
//            }
//        }
    }

    public Point getPosition() {
        return position;
    }

    public HallwayObject makeNewHallway() {
        int newXPos, newYPos, newLength;
        Point newPosition;
        if (direction == HORIZONTAL) {          //horizontal
            if (length == 1) {
                newXPos = position.x;
            } else {
                newXPos = random.nextInt(length - 1) + position.x;
            }
            newYPos = random.nextInt(position.y) + 1;
            newLength = random.nextInt(Math.abs(Engine.HEIGHT - 1 - position.y))
                                            + position.y - newYPos;
            newPosition = new Point(newXPos, newYPos);
            return new HallwayObject(newLength, newPosition, VERTICAL, random);
        } else {                                //vertical
            newXPos = position.x;
            if (length <= 1) {
                newYPos = position.y;
            } else {
                newYPos = random.nextInt(length - 1) + position.y;
            }
            newLength = random.nextInt((Math.max(1, Engine.WIDTH - 1 - position.x) / 2)) + 1;
            newPosition = new Point(newXPos, newYPos);
            return new HallwayObject(newLength, newPosition, HORIZONTAL, random);
        }
//        switch (direction) {
//            case LEFT:
//            case RIGHT:
//                width = RandomUtils.uniform(MapGenerator.getRandomNumberGenerator(),
//                        MapGenerator.width - 1 - position.x / 4) +  1;
//                height = 1;
//                position = pos;
//                direction = dir;
//                break;
//            case TOP:
//            case BOTTOM:
//                width = 1;
//                height = RandomUtils.uniform(MapGenerator.getRandomNumberGenerator(),
//                        MapGenerator.height - 1 - position.y / 4) + 1;
//                position = pos;
//                direction = dir;
//                break;
//        }
    }

//    public HallwayObject(Point pos, Orientation dir) {
//        width = RandomUtils.uniform(MapGenerator.getRandomNumberGenerator(),
//                MapGenerator.width / 4) + 5;
//        height = RandomUtils.uniform(MapGenerator.getRandomNumberGenerator(),
//                MapGenerator.height / 4) + 5;
//        position = pos;
//        direction = dir;
//    }
    @Override
    public void drawIn(TETile[][] world) {
        if (direction == HORIZONTAL) {
            for (int x = position.x; x < position.x + length; x++) {
                world[x][position.y] = FLOOR;
            }
        } else {
            for (int y = position.y; y < position.y + length; y++) {
                world[position.x][y] = FLOOR;
            }
        }
//        for (int x = position.x; x < position.x + width; x++) {
//            for (int y = position.y; y < position.y + height; y++) {
//                world[x][y] = FLOOR;
//            }
//        }
    }
}
