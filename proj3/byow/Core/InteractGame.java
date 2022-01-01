package byow.Core;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import byow.InputDemo.KeyboardInputSource;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;
import java.awt.Font;
import java.awt.Color;
import static byow.TileEngine.Tileset.*;


public class InteractGame {
    private String instructions;
    private Game game;
    private KeyboardInputSource keyboardInput;
    private int width;
    private int height;
    private TETile[][] world;
    private TERenderer ter;

    public static void main(String[] args) throws IOException {
        InteractGame ig = new InteractGame(80, 30, null);
    }

    public InteractGame(int width, int height, TERenderer ter) {
        this.width = width;
        this.height = height;
        this.instructions = "";
        this.keyboardInput = new KeyboardInputSource();
        this.ter = ter;
    }
    public void mainMenu() {
        StdDraw.setCanvasSize(1280, 480);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, 80);
        StdDraw.setYscale(0, 30);
        StdDraw.clear(Color.BLACK);

        StdDraw.setPenColor(Color.WHITE);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.text(40, 28, "CS 61B: Project 3 BYOW");
                StdDraw.text(40, 25, "New Game (N) ");
                StdDraw.text(40, 22, "Load Game (L) ");
                StdDraw.text(40, 19, "Quit Game (Q) ");
                StdDraw.text(40, 16, "Replay (R) ");

                if (StdDraw.isKeyPressed('N') || StdDraw.isKeyPressed('n')) {
                    System.out.println("N has been pressed!");
                    System.out.println("Enter a string: ");
                    StdDraw.text(10, 10, "Enter a String: ");
                    String seed = solicitNCharsInput();
                    timer.cancel();
                    newGame("N" + seed + "S");
                } else if (StdDraw.isKeyPressed('L') || StdDraw.isKeyPressed('l')) {
                    try {
                        timer.cancel();
                        loadGame();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (StdDraw.isKeyPressed('Q') || StdDraw.isKeyPressed('q')) {
                    System.exit(0);
                } else if (StdDraw.isKeyPressed('R') || StdDraw.isKeyPressed('r')) {
                    try {
                        timer.cancel();
                        replayGame();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 1, 1000);
    }


    public void drawFrame(String s) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(80 / 2, 30 / 2, s);
        StdDraw.show();
    }

    public String solicitNCharsInput() { //N333S
        String userinput = "";
        String stop = "";
        while (!(stop.equals("S") || stop.equals("s"))) {
            if (StdDraw.hasNextKeyTyped()) {
                char currChar = StdDraw.nextKeyTyped();
                if (currChar == 'S' || currChar == 's') {
                    stop += currChar;
                    continue;
                }
                if (currChar == 'N' || currChar == 'n' || currChar == 'L' || currChar == 'l'
                        || currChar == 'R' || currChar == 'r') {
                    continue;
                }
                userinput += currChar;
                this.drawFrame(userinput);
            }
        }
        System.out.println(userinput);
        return userinput;
    }

    private void newGame(String userInput) {
        game = new Game(userInput, width, height, false, ter);
        world = game.world();
        game.map().drawWorld();
        game.map().hover();
        runGame();

    }

    private void saveGame() {
        File gameFile = new File("savedGame.txt");
        try {
            FileWriter myWriter = new FileWriter(gameFile);
            instructions = game.getInstructions();
            myWriter.write(instructions);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadGame() throws IOException {
        File gameFile = new File("savedGame.txt");
        String gameData = readFile(gameFile);
        game = new Game(gameData, width, height, false, ter);
        world = game.world();
        game.map().drawWorld();
        game.map().hover();
        runGame();
    }

    private String readFile(File gameFile) throws IOException {
        String hold = "";
        Scanner myReader = new Scanner(gameFile);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            hold += (data + " ");
        }
        return hold;
    }

    private void replayGame() throws IOException {
        File gameFile = new File("savedGame.txt");
        String gameData = readFile(gameFile);
        game = new Game(gameData, width, height, true, ter);
        world = game.world();
        game.map().drawWorld();
        game.map().hover();
        runGame();
    }

    public void runGame() { //where the running of the game happens
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(5, 29, 3, 1);
        String string = new SimpleDateFormat("HH:mm:ss").format(new Date());
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(5, 29, string);
        StdDraw.show();

        //mouse hover floor or tile
        Integer x = (int) StdDraw.mouseX();
        Integer y = (int) StdDraw.mouseY();
        if (world[x][y].equals(FLOOR)) {
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.filledRectangle(1, 29, 2, 1);
            StdDraw.setPenColor(Color.white);
            StdDraw.text(1, 29, "Floor");
            StdDraw.show();
        } else if (world[x][y].equals(WALL)) {
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.filledRectangle(1, 29, 2, 1);
            StdDraw.setPenColor(Color.white);
            StdDraw.text(1, 29, "Wall");
            StdDraw.show();
        } else if (world[x][y].equals(TREE)) {
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.filledRectangle(1, 29, 2, 1);
            StdDraw.setPenColor(Color.white);
            StdDraw.text(1, 29, "Tree");
            StdDraw.show();
        } else {
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.filledRectangle(1, 29, 2, 1);
            StdDraw.setPenColor(Color.white);
            StdDraw.text(1, 29, "Nothing");
            StdDraw.show();
        }
        while (true) {
            while (!StdDraw.hasNextKeyTyped()) {
                StdDraw.pause(500);
            }
            char c = StdDraw.nextKeyTyped();
            if (c == 'W' || c == 'w') {
                game.avatar().move('W');
                game.addInstructions('W');
                instructions += 'W';
            } else if (c == 'A' || c == 'a') {
                instructions += 'A';
                game.avatar().move('A');
                game.addInstructions('A');
            } else if (c == 'S' || c == 's') {
                instructions += 'S';
                game.avatar().move('S');
                game.addInstructions('S');
            } else if (c == 'd' || c == 'D') {
                instructions += 'D';
                game.avatar().move('D');
                game.addInstructions('D');
            } else if (c == ':') {
                while (true) {
                    while (!StdDraw.hasNextKeyTyped()) {
                        StdDraw.pause(500);
                    }
                    char q = StdDraw.nextKeyTyped();
                    if (q == 'Q' || q == 'q') {
                        saveGame();
                        System.exit(0);
                    }
                }
            }
            game.map().drawWorld();
        }
    }

}
