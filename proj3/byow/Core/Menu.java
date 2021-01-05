package byow.Core;

import byow.InputDemo.InputSource;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

/**
 * first thing that is displayed to the user
 * has items on menu:
 * N for “new world”,
 * L for “load world”, and
 * Q for quit
 */
public class Menu {
    private Font titleFont = new Font("Monospaced Bold", Font.BOLD, 50);
    private Font regularFont = new Font("Monospaced", Font.PLAIN, 32);
    private Font smallFont = new Font("Monospaced", Font.ITALIC, 25);
    Engine engine;
    private int width, height;
    private String userInput;
    private InputSource inputSource;

    public Menu(Engine e, int w, int h, InputSource inputSource) {
        engine = e;
        width = w;
        height = h;
        this.inputSource = inputSource;
        StdDraw.setCanvasSize(width * 16, height * 16);
        loadMenuFrame();
    }
    public void loadMenuFrame() {
        displayMenu();
        userInput = handleUserInput();
        char selection = Character.toLowerCase(userInput.charAt(0));
        switch (selection) {
            case 'n':
                long seed = getSeed(userInput);
                engine.beginGame(seed, inputSource, "");
                break;
            case 'l':
                //load saved game
                /**
                 * handle saVBED GAME!!!!!!!! **********
                 */
                userInput = engine.getSavedGameString();
                Game game = engine.parseToGame(userInput);
                engine.beginGame(game.getSeed(), inputSource, game.getMoveCommands());
                break;
            case 'q':
                System.exit(0);
                break;
            default:
                break;
        }
    }

    private long getSeed(String input) {
        String longString = "";
        for (int i = 1; i < input.length(); i++) {
            if (Character.toLowerCase(input.charAt(i)) == 's') {
                break;
            }
            longString += input.charAt(i);
        }
        return Long.parseLong(longString);
    }
    private String handleUserInput() {
        boolean firstLetter = true;
        String uI = "";
        System.out.println("Please make a selection from the menu. "
                + "If you choose 'N', type a random seed and "
                + "press 's' when finished: ");
        while (true) {
            if (inputSource.possibleNextInput()) {
                char c = Character.toLowerCase(inputSource.getNextKey());
                uI += c;
                if (c == 's') {
                    break;
                } else if (firstLetter && c == 'l') {
                    String savedGame = engine.getSavedGameString();
                    if (savedGame == null || savedGame.length() == 0) {
                        System.exit(0);
                    } else {
                        return savedGame;
                    }
                } else if (firstLetter && c == 'q') {
                    System.exit(0);
                }
                System.out.println("just typed " + c);
                firstLetter = false;
            }
        }
        return uI;
    }
    private void displayMenu() {
        StdDraw.clear(Color.darkGray);
        StdDraw.setPenColor(Color.cyan);
        StdDraw.setFont(titleFont);
        StdDraw.text(.5, .8, "CS61B: BYOW");
        StdDraw.setFont(regularFont);
        StdDraw.text(.5, .54, "New Game (N)");
        StdDraw.text(.5, .46, "Load Game (L)");
        StdDraw.text(.5, .38, "Quit (Q)");
        StdDraw.setFont(smallFont);
        StdDraw.text(.5, .1, "Katherine & Jennifer");
        StdDraw.show();
    }
}
