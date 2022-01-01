package byow.Core;


import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

public class Game {

    private MapGenerator map;
    private TETile[][] world;
    private String instructions;
    private long seed;
    private String directions;
    private int width;
    private int height;
    private Avatar avatar;
    private TERenderer ter;

    public Game(String instructions, int width, int height, boolean replay, TERenderer ter) {
        /*
        at the minimum: String instructions should have N###S
        N###SWWWDDD
        if L is pressed, pass in the saved Instruction string to instructions
        create a game
        store movement controls, name, seed, position of avatar, TETile[][]
         */
        this.instructions = instructions;
        this.width = width;
        this.height = height;
        this.ter = ter;
        this.seed = calcSeed();

        //generate world, map
        this.map = new MapGenerator(width, height, seed, ter);
        this.world = map.world();

        // get directions
        this.directions = calcDirections();

        // move around Avatar
        if (replay) {
            this.replay(directions);
        } else {
            this.avatar = new Avatar(map);
            setAvatar(this.directions);
        }
    }

    /*
    put an avatar in the right position
    move the avatar according to directions
    update Avatar position
     */
    private void setAvatar(String direction) {
        for (int i = 0; i < direction.length(); i += 1) {
            avatar.move(direction.charAt(i));
        }
    }

    private void replay(String dir) {
        this.avatar = new Avatar(map);
        for (int i = 0; i < dir.length(); i++) {
            avatar.move(dir.charAt(i));
            map.drawWorld();
            map.hover();
            StdDraw.pause(500);
        }
    }

    // gets the seed of the game
    private long calcSeed() {
        StringInputDevice sid = new StringInputDevice(instructions);
        char start = sid.getNextKey();
        String seedStr = "";
        char c = sid.getNextKey();
        while ((c != 'S' && c != 's') && sid.possibleNextInput()) {
            seedStr += c;
            c = sid.getNextKey();
        }
        return Long.parseLong(seedStr);
    }

    private String calcDirections() {
        StringInputDevice sid = new StringInputDevice(instructions);
        char front = sid.getNextKey();
        while ((front != 'S' && front != 's') && sid.possibleNextInput()) {
            front = sid.getNextKey();
        }
        // front = S
        String dirString = "";
        while (sid.possibleNextInput()) {
            dirString += sid.getNextKey();
        }
        return dirString;
    }

    public void addInstructions(char c) {
        instructions += c;
    }

    public String getInstructions() {
        return instructions;
    }

    public MapGenerator map() {
        return map;
    }

    public Avatar avatar() {
        return avatar;
    }

    public TETile[][] world() {
        return world;
    }

    private long seed() {
        return seed;
    }

    private String directions() {
        return directions;
    }

    public static void main(String[] args) {

    }
}

