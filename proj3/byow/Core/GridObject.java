package byow.Core;

// This class represents an object in our world
// It can be either a room or a hallway

import byow.TileEngine.TETile;

//public class GridObject {
//    int index;
//    int width, height;
//    int xPos, yPos;
//    Orientation direction;
//
//    public GridObject(int width, int height, int xPos, int yPos, Orientation direction) {
//        this.width = width;
//        this.height = height;
//        this.xPos = xPos;
//        this.yPos = yPos;
//        this.direction = direction;
//    }
//}
public interface GridObject {
    void drawIn(TETile[][] world);
}
