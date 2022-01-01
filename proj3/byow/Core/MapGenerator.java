package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Font;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.HashMap;
import static byow.TileEngine.Tileset.*;

public class MapGenerator {
    private final int width;
    private final int height;
    private TETile [][] world;
    private int load;
    private final double limit;
    private final int maxRoomWidth;
    private final int maxRoomHeight;
    private final Random RANDOM;
    private Position firstPos;
    private TERenderer ter;

    /**
     * helper method for creating blank world -helen
     */
    public MapGenerator(int width, int height, long seed, TERenderer ter) {
        this.width = width;
        this.height = height;
        load = 0;
        limit = width * height * 1.5;
        maxRoomHeight = height / 15;
        maxRoomWidth = width / 4;
        this.RANDOM = new Random(seed);
        this.world = new TETile[width][height];
        this.world = getWorld();
        this.ter = ter;
        if (ter != null) {
            ter.initialize(80, 30);
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        MapGenerator a = new MapGenerator(80, 30, 1, ter);
        a.initializeMap();
        a.drawWorld();
    }

    public void initializeMap() {
        if (ter == null) {
            return;
        }
        ter.initialize(width, height);
    }

    public void drawWorld() {
        if (ter == null) {
            return;
        }
        //draws the final world
        ter.renderFrame(world);
    }

    private TETile[][] getWorld() {
        blankWorld();
        this.firstPos = firstPos();
        Room firstRoom = new Room(RANDOM.nextInt(maxRoomWidth) + 3,
                RANDOM.nextInt(maxRoomHeight) + 3, false, firstPos);

        //draw rooms and hallways while there are less stuff tiles than limit
        drawRoomHelper(firstRoom);
        return world;
    }

    public TETile[][] world() {
        return world;
    }

    public void hover() {
        Font font = new Font("Monaco", Font.BOLD, 10);
        StdDraw.setFont(font);
        java.util.Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //timer
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
            }
        }, 1, 1000);
    }

    /*
    generates rooms / hallways recursively --> then draws a room stemming from the given room
    firstRoom is passed in first
    Room(width, height, hallway, pos)
     */
    private void drawRoomHelper(Room given) {
        if (given == null) {
            return; // stop recursion
        }
        drawRoom(given);

        Room nextTop = nextRoom(given, "top");
        Room nextRight = nextRoom(given, "right");
        drawRoom(nextTop);
        drawRoom(nextRight);

        drawRoomHelper(nextRight);
        drawRoomHelper(nextTop);
    }

    /*
    generate next room given a room
     */
    private Room nextRoom(Room r, String direction) {
        if (r == null || load > limit) {
            return null;
        }

        HashMap<String, Position> info = nextPos(r);
        Position nextPos;
        if (direction.equals("top")) {
            nextPos = info.get("topPos");
        } else {
            nextPos = info.get("rightPos");
        }

        int randW = RANDOM.nextInt(maxRoomWidth) + 5;
        int randH = RANDOM.nextInt(maxRoomHeight) + 3;
        Room next;

        if (!r.isHallway()) { // must draw hallway
            if (direction.equals("top")) { //draw hallway at top: width = 1
                next = new Room(1, randH + 12, true, nextPos);
            } else { // draw hallway to the side: height = 1
                next = new Room(randW + 12, 1, true, nextPos);
            }
        } else { // must draw room
            next = new Room(randW, randH, false, nextPos);
        }

        if (!inBounds(next)) {
            next = null;
        }
        return next;
    }

    private boolean inBounds(Room r) {
        int w = r.topRight().getX();
        int h = r.topRight().getY();
        return (0 < w && w < width) && (0 < h && h < height);
    }

    private void blankWorld() {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    /*
    given a room, generates where the next starting position should be.
    only generates rooms / hallways from the top / right of a given room
    topPos rightPos
     */
    private HashMap<String, Position> nextPos(Room r) {
        int w = r.width();
        int h = r.height();
        int top = RANDOM.nextInt(w) + 1;
        int bottom = RANDOM.nextInt(w) + 1;
        int right = RANDOM.nextInt(h) + 1;
        int left = RANDOM.nextInt(h) + 1;

        HashMap<String, Position> result = new HashMap<>();
        result.put("topPos", new Position(r.topLeft().getX() + top, r.topLeft().getY()));
        result.put("botPos", new Position(r.botLeft().getX() + bottom, r.botLeft().getY()));
        result.put("rightPos", new Position(r.botRight().getX(), r.botRight().getY() + right));
        result.put("leftPos", new Position(r.botLeft().getX(), r.botLeft().getY() + left));
        return result;
    }

    /*
    generates the random position of the first room
     */
    private Position firstPos() {
        int widthLim = width / 10;
        int heightLim = height / 10;
        return new Position(RANDOM.nextInt(widthLim) + 1, RANDOM.nextInt(heightLim) + 1);
    }

    public Position getFirstPos() {
        return firstPos;
    }

    /**
     * Draw a given room at startPos
     */
    private void drawRoom(Room r) {
        if (r == null) {
            return;
        }
        int w = r.width();
        int h = r.height();
        Position startPos = new Position(r.botLeft().getX() + 1, r.botLeft().getY() + 1);

        for (int i = 0; i < h; i++) {
            drawRow(new Position(startPos.getX(), startPos.getY() + i), w, FLOOR);
        }

        drawRow(r.botLeft(), w + 2, WALL);
        drawRow(r.topLeft(), w + 2, WALL);

        drawCol(r.botLeft(), h + 2, WALL);
        drawCol(r.botRight(), h + 2, WALL);
    }

    /**
     * Draws a single row of tiles starting from given Pos with the given tileType
     */
    private void drawRow(Position startPos, int rWidth, TETile tileType) {
        for (int i = 0; i < rWidth; i++) {
            putTile(new Position(startPos.getX() + i, startPos.getY()), tileType);
        }

    }

    private void drawCol(Position startPos, int rHeight, TETile tileType) {
        for (int i = 0; i < rHeight; i++) {
            putTile(new Position(startPos.getX(), startPos.getY() + i), tileType);
        }
    }

    private void putTile(Position pos, TETile type) {
        int randTree = RANDOM.nextInt(20);
        TETile under = world[pos.getX()][pos.getY()];
        load += 1;
        if (under.equals(NOTHING)) {
            world[pos.getX()][pos.getY()] = type;
        } else if (under.equals(FLOOR) || (under.equals(TREE))
                || type.equals(FLOOR)) {
            if (randTree == 1) {
                world[pos.getX()][pos.getY()] = TREE;
            } else {
                world[pos.getX()][pos.getY()] = FLOOR;
            }
        } else {
            world[pos.getX()][pos.getY()] = WALL;
        }
    }
}
