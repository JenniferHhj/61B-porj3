package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.KeyboardInputSource;
import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

import java.io.*;


public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final String FILEPATH = "./savedWorld.txt";
    public static final File FILE = new File(FILEPATH);


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        /**
         * display menu page
         * parse user input
         * possibly save game
         */
        Game game;
        Player player;
        KeyboardInputSource keyboard = new KeyboardInputSource();
        Menu menu = new Menu(this, 35, 50, keyboard);
        menu.loadMenuFrame();
    }

    public void beginGame(long seed, InputSource inputSource, String commands) {
        Game game = new Game(this, seed, commands);
        ter.initialize(WIDTH, HEIGHT);
        Player p = new Player(game.getWorld(), game.getRandom());
        game.setPlayer(p);
        game.begin(ter, p, inputSource);
    }

    private String getInputFromUser(InputSource keyboard) {
        String inputString = "";
        while (keyboard.possibleNextInput()) {
            char c = keyboard.getNextKey();
            if (Character.toLowerCase(c) == 's') {
                inputString += c;
                break;
            } else {
                inputString += c;
            }
        }
        return inputString;
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        InputSource inputSource = new StringInputDevice(input);
        Player player;
        Game game;

        char key = Character.toLowerCase(inputSource.getNextKey());
        //every string starts w/ N##S or L
        switch (key) {
            case 'n':                               //initialize new game
                //want to overwrite the text file, not append
                String processedInput = processNewInput(input, false);
                game = parseToGame(processedInput);
                player = new Player(game.getWorld(), game.getRandom());
                player.moveByCommands(game.getMoveCommands());
                player.placeInCurrentPosition(game.getWorld());
                return game.getWorld();
            case 'l':                               //load last saved game
                String savedGame = getSavedGameString();
                if (savedGame == null || savedGame.length() == 0) {
                    System.exit(0);
                } else {
                    String newMoves = processNewInput(input, true);
                    String totalString = savedGame + newMoves;
                    game = parseToGame(totalString);
                    player = new Player(game.getWorld(), game.getRandom());
                    player.moveByCommands(game.getMoveCommands());
                    player.placeInCurrentPosition(game.getWorld());
                    return game.getWorld();
                }
                break;
            default:
                return null;
        }
        return null;
    }

    /**
     * if input ends in :q, method will write everything but :q to .txt file
     * will either append or overwrite current text file depending on first letter ('n' or 'l')
     * if input does not end in :q, will only load the game and not save
     * @param input
     * @param append
     * @return
     */
    public String processNewInput(String input, boolean append) {
        String toWrite;
        if (Character.toLowerCase(input.charAt(input.length() - 1)) == 'q'    //will quit and save
                && input.charAt(input.length() - 2) == ':') {
            toWrite = input.substring(0, input.length() - 2);
            saveGame(toWrite, append);
            return toWrite;
        } else {                                                             //will not quit or save
            return input;
        }
    }

    //parse seed & moves from string
    //returns game based on these values
    public Game parseToGame(String input) {
        long seed;
        int postSeedIndex;
        String moves;
        for (int i = 0; i < input.length(); i++) {
            if (Character.toLowerCase(input.charAt(i)) == 's') {
                seed = Long.parseLong(input.substring(1, i));
                postSeedIndex = i + 1;
                moves = input.substring(postSeedIndex);
                return new Game(this, seed, moves);
            }
        }
        return null;
    }

    /**
     * @source https://stackabuse.com/reading-and-writing-files-in-java/
     * @param data
     * @param append
     */
    public void saveGame(String data, boolean append) {
        FileWriter fileWriter;
        try {
            if (append) {
                fileWriter = new FileWriter(FILE, true);
            } else {
                fileWriter = new FileWriter(FILE);
            }
            fileWriter.write(data);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("getSavedGameString()\nIO Exception");
            e.printStackTrace();
        }
    }

    /**
     * @source https://docs.oracle.com/javase/8/docs/api/?java/io/FileReader.html
     * @return String stored in text file
     */
    public String getSavedGameString() {
        StringBuilder gameData = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(FILE);
            int i = fileReader.read();
            while (i != -1) {
                gameData.append((char) i);
                i = fileReader.read();
            }
        } catch (FileNotFoundException e) {
            System.out.println("getSavedGameString()\nFile not found");
            e.printStackTrace();
        } catch (IOException c) {
            System.out.println("getSavedGameString()\nIO Exception");
            c.printStackTrace();
        }
        return gameData.toString();
    }



}
