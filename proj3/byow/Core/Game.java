package byow.Core;


import byow.InputDemo.InputSource;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

import java.util.Random;

public class Game {
    Engine engine;
    private long seed;
    private String moveCommands = "";
    private MapGenerator generator;
    private TETile[][] world, newWorld;
    private Player player;
    private TERenderer ter;
    InputSource inputSource;
//    public int mouse_new_x;
//    public int mouse_new_y;

    public Game(Engine e, long s) {
        engine = e;
        this.seed = s;
        moveCommands = "";
        generator = new MapGenerator(seed);
        world = generator.generate();
    }
    public Game(Engine e, long s, String moves) {
        engine = e;
        this.seed = s;
        moveCommands += moves;
        generator = new MapGenerator(seed);
        world = generator.generate();
    }

    public void begin(TERenderer renderer, Player p, InputSource iS) {
        //everything done to load game frame
        this.ter = renderer;
        player = p;
//        System.out.println("loading game frame");
        loadGameFrame();

        //handle input
        this.inputSource = iS;
//        System.out.println("handling game input");
        handleInput();
    }
    public void handleInput() {
        if (moveCommands.length() != 0) {
            for (int i = 0; i < moveCommands.length(); i++) {
                player.moveByCommand(newWorld, moveCommands.charAt(i));
            }
            loadGameFrame();
        }
        while (true) {
//            System.out.println("handling user input...");
            if (inputSource.possibleNextInput()) {
                char c = Character.toLowerCase(inputSource.getNextKey());
                System.out.println("just typed " + c);
                moveCommands += c;
                if (c == ':') {
                    moveCommands = moveCommands.substring(0, moveCommands.length() - 1);
                    if (endGame()) {
                        System.out.println("move commands are: " + moveCommands);
                        if (moveCommands.length() != 0) {
                            engine.saveGame(moveCommands, moveCommands.charAt(0) != 'n');
                        } else {
                            engine.saveGame("n" + seed + "s", false);
                        }
                        System.exit(0);
                    }
                }
//                System.out.println("player moving...");
                player.moveByCommand(newWorld, Character.toLowerCase(c));
//                System.out.println("loading game frame");
                loadGameFrame();
            }
        }
    }
    private boolean endGame() {
        while (true) {
            if (inputSource.possibleNextInput()) {
                char c = Character.toLowerCase(inputSource.getNextKey());
                if (c == 'q') {
                    return true;
                }
                return false;
            }
        }
    }
    private void loadGameFrame() {
        ter.initialize(Engine.WIDTH, Engine.HEIGHT);
        newWorld = copyWorld(world);
        player.placeInCurrentPosition(newWorld);
//        System.out.println("starting HUD...");
//        headsUpDisplay(newWorld);
        ter.renderFrame(newWorld);
//        headsUpDisplay(newWorld);
    }

    private TETile[][] copyWorld(TETile[][] w) {
        TETile[][] temp = new TETile[Engine.WIDTH][Engine.HEIGHT];
        for (int i = 0; i < Engine.WIDTH; i++) {
            for (int j = 0; j < Engine.HEIGHT; j++) {
                temp[i][j] = w[i][j];
            }
        }
        return temp;
    }

//    public void setSeed(long newSeed) {
//        seed = newSeed;
//    }
    public long getSeed() {
        return seed;

    }
//    public void addMoveCommands(String newMoves) {
//        moveCommands += newMoves;
//    }

    public String getMoveCommands() {
        return moveCommands;
    }

    public void setPlayer(Player p) {
        player = p;
    }
//
//    public Player getPlayer() {
//        return player;
//    }
    public TETile[][] getWorld() {
        return world;
    }
    public Random getRandom() {
        return generator.getRandomNumberGenerator();
    }
    /**
     * Adding the HeadUpDisplay bar to our world
     */
//    In main we call the TERender with xoffset = 0 and yoffset = 0
//    public void headUpDisplay(TETile[][] world) {
//        //ter.renderFrame(world);
//        int mouse_x_cord = (int) StdDraw.mouseX();
// check the mouse cooridinate for x and y using stdDraw
//        int mouse_y_cord = (int) StdDraw.mouseY();
//        System.out.println("mouse coordination is" + mouse_x_cord + "and" + mouse_y_cord);
//        if (mouse_x_cord < 0 || mouse_x_cord > Engine.WIDTH - 1 || mouse_y_cord < 0
//        || mouse_y_cord > Engine.HEIGHT - 1) {     //when mouse is out of our grid
//            mouse_new_x = (int) mouse_x_cord;
//            mouse_new_y = (int) mouse_y_cord;
//            System.out.println("mouse coordination is outside of our world");
//            StdDraw.setPenColor(StdDraw.WHITE);
//            Font font = new Font("Monaco", Font.BOLD, 9);
//            StdDraw.setFont(font);
//            StdDraw.textRight(.1, .1, "Not in our world");
//            //StdDraw.show();
//            //t = Tileset.NOTHING;
//            //this.lastHoverPoint = newHoverPoint;
//            //StdDraw.textLeft(1, -1, "Tile: " + t.description());
//        }
//        //if (mouse_x_cord >= 0 && mouse_x_cord < Engine.WIDTH
//        && mouse_y_cord >= 0 && mouse_y_cord < Engine.HEIGHT)
//        else {   //mouse in world
//            //mouse_new_x = (int) mouse_x_cord;
//            //mouse_new_y = (int) mouse_y_cord;
//            if (mouse_new_x != (int) mouse_x_cord || mouse_new_y != (int) mouse_y_cord) {
// mouse is located at a different tile
//                mouse_new_x = (int) mouse_x_cord;
//                mouse_new_y = (int) mouse_y_cord;
//                System.out.println("mouse is inside our world");
//                TETile mouseat = world[mouse_new_y][mouse_new_y];   //which tile the mouse is at
//                StdDraw.setPenColor(StdDraw.WHITE);
//                Font font = new Font("Monaco", Font.BOLD, 9);
//                //StdDraw.setFont(font);
//                System.out.println("Mouse is at tile: " + mouseat.description());
//
//                //if (mouseat.description() == "you") {
// when the tile is our AVATAR
//                // StdDraw.text(1, 1, "Jennifer and Katherine");
//                //StdDraw.text(2, 2, mouseat.description());
//                //} else {
//                StdDraw.textRight(.2, .2, mouseat.description());
////                StdDraw.show();
//                //}
//                StdDraw.textRight(mouse_x_cord / Engine.WIDTH, mouse_y_cord
//                / Engine.HEIGHT, "Tile: " + mouseat.description());
//            }
//        }
//
//        StdDraw.show();
//    }
//    public void headsUpDisplay(TETile[][] w) {
////        Font titleFont = new Font("Monospaced Bold", Font.BOLD, 50);
////        Font regularFont = new Font("Monospaced", Font.PLAIN, 32);
//        Font smallFont = new Font("Monospaced", Font.ITALIC, 15);
//
////        StdDraw.setCanvasSize(90, 100);
////        System.out.println("canvas width 90, height 100");
//        StdDraw.clear(Color.darkGray);
////        StdDraw.setPenColor(Color.cyan);
////        StdDraw.setFont(smallFont);
////        StdDraw.text(.5, .8, "CS61B: BYOW");
//
//        Font tinyPlain = new Font("Monaco", Font.PLAIN, 10);
//        StdDraw.setFont(tinyPlain);
//        StdDraw.setPenColor(Color.white);
//
//        TETile t;
//        double xPos = StdDraw.mouseX();
//        double yPos = StdDraw.mouseY();
////        newHoverPoint = new Point(xPos, yPos);
////        System.out.println("hover point: " + newHoverPoint);
//
//        if (xPos < 1 && xPos > 0 && yPos > 0 && yPos < 1) {
//            t = w[(int) xPos * 80][(int) yPos * 10];
//            StdDraw.textRight(xPos / Engine.WIDTH, yPos / Engine.HEIGHT, "Tile: "
//            + t.description());
//            StdDraw.show();
//        }
////        if (xPos < 0 || xPos > 1 || yPos < 0 || yPos > ) {
////            t = Tileset.NOTHING;
////            this.lastHoverPoint = newHoverPoint;
////        } else if (lastHoverPoint != newHoverPoint) {
////            this.lastHoverPoint = newHoverPoint;
////            t = w[xPos][yPos];
////        }
//
//    }
//    public void headsUpDisplay(TETile[][] w) {
//        Font tinyPlain = new Font("Monaco", Font.PLAIN, 10);
//        StdDraw.setFont(tinyPlain);
//        StdDraw.setPenColor(Color.white);
//
//        TETile t;
//        double xPos = StdDraw.mouseX();
//        double yPos = StdDraw.mouseY();
//
//        double width = Engine.WIDTH * 16;
//        double height = Engine.HEIGHT * 16;
//        if (xPos < 1 && xPos > 0 && yPos > 0 && yPos < 1) {
//            t = world[(int) ((int) xPos * width)][(int) ((int) yPos * height)];
//            StdDraw.text(.5, .5, "Tile: " + t.description());
//            StdDraw.show();
//        }
//    }
}
