package byow.Core;

import byow.TileEngine.TETile;

import java.awt.*;
import java.util.Random;

import static byow.TileEngine.Tileset.FLOOR;

public class RoomObject implements GridObject {
    private int width, height;
    private Point position;
    private Random random;
//    private Orientation direction;


    public RoomObject(Point pos, Random ran) {
        random = ran;
        width = random.nextInt(Math.min(MapGenerator.WIDTH - 1 - pos.x, 5));
        height = random.nextInt(Math.min(MapGenerator.HEIGHT - 1 - pos.y, 5));
//        width = Math.min(random.nextInt(MapGenerator.WIDTH - 1 - pos.x),
//                                            random.nextInt(5));
//        height = Math.min(random.nextInt(MapGenerator.HEIGHT - 1 - pos.y),
//                                            random.nextInt(5));
//        width = RandomUtils.uniform(MapGenerator.getRandomNumberGenerator(),
//                                    MapGenerator.WIDTH / 4) + 5;
//        height = RandomUtils.uniform(MapGenerator.getRandomNumberGenerator(),
//                                    MapGenerator.HEIGHT / 4) + 5;
        position = pos;
    }
    @Override
    public void drawIn(TETile[][] world) {
        for (int x = position.x; x < position.x + width; x++) {
            for (int y = position.y; y < position.y + height; y++) {
                world[x][y] = FLOOR;
            }
        }
    }
}
