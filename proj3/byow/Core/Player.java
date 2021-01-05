package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Player {

    private TETile PLAYER;
    private Point position;
    private TETile[][] world;
    private Random random;

    //array that keep track of each other 9 points we need to change
    private ArrayList<Point> changed;

    //use an array to keep track of each type of the tetiling
    private ArrayList<TETile> tetiling;
    private ArrayList<Point> changeback;

    //the player have it's own class that handle win or lost
    //    public RunningCat playerscat;
    //whether the player is winning or losing
    private boolean winorlost;
    private ArrayList<TETile> backtiling;
    //TODO find a way to store the xoffset and yoffset of the world drawing;

    public Player(TETile[][] w, Random r) {
        PLAYER = Tileset.AVATAR;
        this.world = w;
        random = r;
        setStartingPosition();
        changed = new ArrayList<>();
        tetiling = new ArrayList<>();
        changeback = new ArrayList<>();
        winorlost = true;
        backtiling = new ArrayList<TETile>();
        tetiling = new ArrayList<TETile>();
//        playerscat = new RunningCat(9, 0, 3);     //Initiate the Running cat's class
    }

    private void setStartingPosition() {
        int x = random.nextInt(Engine.WIDTH);
        int y = random.nextInt(Engine.HEIGHT);
        while (world[x][y] != Tileset.FLOOR) {
            x = random.nextInt(Engine.WIDTH);
            y = random.nextInt(Engine.HEIGHT);
        }
        position = new Point(x, y);
    }

    public void moveByCommand(TETile[][] thisWorld, char command) {
//        System.out.println("current position: (" + position.x + ", " + position.y + ")");
        switch (command) {
            case 'w':
//                System.out.println("moving up");
                moveTo(thisWorld, position.x, position.y + 1);
                break;
            case 's':
//                System.out.println("moving down");
                moveTo(thisWorld, position.x, position.y - 1);
                break;
            case 'a':
//                System.out.println("moving left");
                moveTo(thisWorld, position.x - 1, position.y);
                break;
            case 'd':
//                System.out.println("moving right");
                moveTo(thisWorld, position.x + 1, position.y);
                break;
            default:
                break;
        }
    }
    public void moveByCommands(String commands) {
        for (int i = 0; i < commands.length(); i++) {
            char command = Character.toLowerCase(commands.charAt(i));
            switch (command) {
                case 'w':
                    moveTo(position.x, position.y + 1);
                    break;
                case 's':
                    moveTo(position.x, position.y - 1);
                    break;
                case 'a':
                    moveTo(position.x - 1, position.y);
                    break;
                case 'd':
                    moveTo(position.x + 1, position.y);
                    break;
                default:
                    break;
            }
        }
    }

    public TETile[][] getWorld() {
        return world;
    }

    public Point getPosition() {
        return position;
    }
    public void placeInCurrentPosition(TETile[][] w) {
        w[position.x][position.y] = PLAYER;
    }
    private void moveTo(int x, int y) {
        if ((world[x][y] == Tileset.FLOOR) && y < Engine.HEIGHT
                && y > 0 && x < Engine.WIDTH && x > 0) {
            world[this.position.x][this.position.y] = Tileset.FLOOR;
            this.position = new Point(x, y);
            world[x][y] = PLAYER;
            changelist(x, y);
//            liveordie(playerscat);
        }
    }
    private void moveTo(TETile[][] thisWorld, int x, int y) {
        if ((thisWorld[x][y] != Tileset.WALL && thisWorld[x][y] != Tileset.NEWWALL)
                && y < Engine.HEIGHT && y > 0 && x < Engine.WIDTH && x > 0) {
            thisWorld[this.position.x][this.position.y] = Tileset.FLOOR;
            this.position = new Point(x, y);
            thisWorld[x][y] = PLAYER;
//            System.out.println("calling changeList");
            changelist(x, y);
//            System.out.println("finished calling changeList");
//            liveordie(playerscat);
        }
//        //if the tile is a FOOD tile, we increase the food for the player
//        if (world[x][y] == Tileset.LARVA && y < Engine.HEIGHT
//                && y > 0 && x < Engine.WIDTH && x > 0) {
//            playerscat.food = playerscat.food + 1;
//            liveordie(playerscat);
//        }
//        //if the tile is larva,we decrease the food supply for the player
//        if (world[x][y] == Tileset.LARVA && y < Engine.HEIGHT
//                && y > 0 && x < Engine.WIDTH && x > 0) {
//            playerscat.food = playerscat.food - 2;
//            liveordie(playerscat);
//        }
    }

//    private void moveAroundWorld(Game game, KeyboardInputSource kb, TERenderer ter) {
//        while (kb.possibleNextInput()) {
//            char c = kb.getNextKey();
//            p.moveByCommand(c);
//            ter.renderFrame(p.getWorld());
//        }
//    }

    /**We add the edge case in the same function, when x == 0, we let x -1 == 0 as well
     * when x == Width - 1, we let x + 1 == width - 1 as well
     * when y == 0, we let x - 1 == 0 as well
     * when y == height - 1, we let y + 1 == height - 1 as well
     * @param x
     * @param y
     */

    //store all the points that need to be shown in two list
    public void changelist(int x, int y) {
        //all the points that are surrounded around the avatar
        //int height = ter.height;
        //int width = ter.width;
        int height = 30;
        int width = 80;
        Point pd = new Point(x, Math.max(y - 1, 0));
        changed.add(pd);
        tetiling.add(world[pd.x][pd.y]);
        Point pu = new Point(x, Math.min(y + 1, height - 1));
        changed.add(pu);
        tetiling.add(world[pu.x][pu.y]);
        Point pl = new Point(Math.max(x - 1, 0), y);
        changed.add(pl);
        tetiling.add(world[pl.x][pl.y]);
        Point pr = new Point(Math.min(x + 1, width - 1), y);
        changed.add(pr);
        tetiling.add(world[pr.x][pr.y]);
        Point bl = new Point(Math.max(x - 1, 0), Math.max(y - 1, 0));
        changed.add(bl);
        tetiling.add(world[bl.x][bl.y]);
        Point ur = new Point(Math.min(x + 1, width - 1), Math.min(y + 1, height - 1));
        changed.add(ur);
        tetiling.add(world[ur.x][ur.y]);
        Point ul = new Point(Math.max(x - 1, 0), Math.min(y + 1, height - 1));
        changed.add(ul);
        tetiling.add(world[ul.x][ul.y]);
        Point bc = new Point(x + 1, Math.max(y - 1, 0));
        changed.add(bc);
        tetiling.add(world[bc.x][bc.y]);
        changingcolor();
    }

//    /**This method determine the survial of the player so we know
//    whether the player survive or dead*/
//    private void liveordie(RunningCat playerscat) {
//        //the playerscat lose one life when there's not enough food
//        if(playerscat.food <= 0) {
//            playerscat.lives = playerscat.lives - 1;
//            playerscat.food = 3;                        //reset the food supply to it's normal
//        }
//        playerscat.times = playerscat.times + 1;
//        //the player lost because the cat don't have lives anymore
//        if(playerscat.lives <= 0){
//            playerscat.survive = false;                     //the playerscat die
//            this.winorlost = false;                             //the player lost
//        }
//    }

    /**the following methods are for changing the vision of the play to
     * only see the tiles that are surrounded to
     * it's position.
     */
    //help change the background color of the new 9 point visited
    // and the old 9 point need to change back
    private void changingcolor() {
        //double xoffset = (double) 0.0 + 0.5;
        //double yoffset = (double) 0.0 + 0.5;
        if (changeback.size() > 0) {
//            StdDraw.setPenColor(Color.black);
            //int i = 0;
            for (int i = 0; i < changeback.size(); i++) {
                Point eachback = changeback.get(i);
                TETile origin = backtiling.get(i);
                if (origin == Tileset.WALL || origin == Tileset.NEWWALL) {
                    world[eachback.x][eachback.y] = Tileset.WALL;
                } else {
                    world[eachback.x][eachback.y] = Tileset.FLOOR;
                }
            }
            changeback.clear();
            backtiling.clear();
        }
//        StdDraw.setPenColor(Color.magenta);
        if (changed.size() > 0) {
            for (int a = 0; a < changed.size(); a++) {
                Point eachChange = changed.get(a);
                TETile storage = tetiling.get(a);
                if (storage == Tileset.WALL || storage == Tileset.NEWWALL) {
                    world[eachChange.x][eachChange.y] = Tileset.NEWWALL;
                } else {
                    world[eachChange.x][eachChange.y] = Tileset.NEWFLOOR;
                }
                backtiling.add(storage);
                changeback.add(eachChange);
            }
            changed.clear();
            tetiling.clear();
        }
    }
}
