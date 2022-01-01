package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Avatar {
    /*
     Create the avatar object
     that moves in a given world of TETile[][]
     */
    private Position currPos;
    private TETile[][] world;
    private MapGenerator map;

    public Avatar(MapGenerator map) {
        this.map = map;
        this.world = map.world();
        this.currPos = map.getFirstPos();
        world[currPos.getX()][currPos.getY()] = Tileset.AVATAR;
    }

    public void move(char dir) {
        Position next;
        if (dir == 'W' || dir == 'w') { // up
            next = new Position(currPos.getX(), currPos.getY() + 1);
        } else if (dir == 'S' || dir == 's') { // down
            next = new Position(currPos.getX(), currPos.getY() - 1);
        } else if (dir == 'A' || dir == 'a') { // left
            next = new Position(currPos.getX() - 1, currPos.getY());
        } else { // right
            next = new Position(currPos.getX() + 1, currPos.getY());
        }
        if (isValid(next)) {
            world[currPos.getX()][currPos.getY()] = Tileset.FLOOR;
            currPos = next;
            world[next.getX()][next.getY()] = Tileset.AVATAR;
        }
    }

    private boolean isValid(Position next) {
        return world[next.getX()][next.getY()].equals(Tileset.FLOOR)
                || world[next.getX()][next.getY()].equals(Tileset.TREE);
    }

    public Position currPos() {
        return currPos;
    }

    public static void main(String[] args) {
        MapGenerator map = new MapGenerator(80, 30, 33, null);
        map.drawWorld();
        Avatar a = new Avatar(map);
        map.drawWorld();

    }
}
