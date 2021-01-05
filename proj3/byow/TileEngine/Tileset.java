package byow.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile AVATAR = new TETile('@', Color.white, Color.magenta,
            "you", "./avatar.png");
//    public static final TETile WALL = new TETile('#', Color.black, Color.black,
//            "wall");
//    public static final TETile WALL = new TETile('#', Color.yellow, Color.black,
//            "wall", "./wall.png");
//    public static final TETile FLOOR = new TETile('·', Color.black, Color.black,
//            "floor", "./floor.png");
//    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black,
//    "nothing", "./nothing.png");

    public static final TETile WALL = new TETile('#', Color.black, Color.black,
            "wall");
    public static final TETile FLOOR = new TETile('·', Color.black, Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
    public static final TETile LEFT = new TETile('L', Color.green, Color.black, "tree");
    public static final TETile RIGHT = new TETile('R', Color.green, Color.black, "tree");
    public static final TETile UP = new TETile('U', Color.green, Color.black, "tree");
    public static final TETile BOTTOM = new TETile('B', Color.green, Color.black, "tree");
    public static final TETile LEFTOP = new TETile('L', Color.red, Color.black, "tree");
    public static final TETile RIGHTOP = new TETile('R', Color.red, Color.black, "tree");
    public static final TETile TOPOP = new TETile('T', Color.red, Color.black, "tree");
    public static final TETile BOTTOMOP = new TETile('B', Color.red, Color.black, "tree");
    public static final TETile CLOSE = new TETile('C', Color.blue, Color.black, "tree");
    public static final TETile DOOR = new TETile('$', Color.red, Color.yellow, "door");
    public static final TETile FOOD = new TETile('9', Color.green, Color.black, "FOOD");
    public static final TETile LARVA = new TETile('+', Color.red, Color.black, "LARVA");
    public static final TETile NEWWALL = new TETile('&', Color.yellow, Color.GRAY,
            "wall", "./wall.png");
    public static final TETile NEWFLOOR = new TETile('~', Color.red, Color.GRAY,
            "floor", "./floor.png");





}
