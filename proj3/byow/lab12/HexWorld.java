package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
//    private int size; // size
//    private TETile tileType;
//    private Position topLeft;
//    private List<Position> tilePos = new ArrayList<>();
//
//    public Hexagon(int size, TETile tileType, Position topLeft) {
//        this.size = size;
//        this.tileType = tileType;
//        this.topLeft = topLeft;
//        addHexagonTiles();
//    }
//
//    private void addHexagonTiles() {
//        for (int row = 0; row < this.size * 2; row++) {
//            Position rowStart = getRowStart(row);
//        }
//    }
//
//    private Position getRowStart(int rowNum) {
//        if (rowNum < this.size) { //check you're at the top half of hextile
//            int x = this.topLeft.getX() - rowNum;
//            int y = this.topLeft.getY() - rowNum;
//            Position start = new Position(x, y);
//            return start;
//        } else {
//            int x = this.topLeft.getX() - (size - 1) + (rowNum - size);
//            int y = this.topLeft.getY() - rowNum;
//            Position start = new Position(x, y);
//            return start;
//        }
//    }

}
