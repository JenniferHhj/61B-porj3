package byow.Core;

import byow.TileEngine.TERenderer;

public class MapVisualTest {

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(80, 30);
        Engine e = new Engine();
//        MapGenerator map = new MapGenerator(80, 30, 500);
        ter.renderFrame(e.interactWithInputString("n5197880843569031643s"));
    }
}
