package byow.Core;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private boolean hallway;
    private int width;
    private int height;
    private Position bottomLeft;
    private List<Position> roomPos;

    /*
    BL: bottom left position of the FLOOR
     */
    public Room(int width, int height, boolean hallway, Position bl) {
        this.width = width;
        this.height = height;
        this.hallway = hallway;
        //bottom left including the wall
        this.bottomLeft = new Position(bl.getX() - 1, bl.getY() - 1);
//        this.wall = wall;
//        this.floor = floor;
        this.roomPos = new ArrayList<>();
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public boolean isHallway() {
        return hallway;
    }

    public Position botLeft() {
        return bottomLeft;
    }

    public Position botRight() {
        return new Position(topRight().getX(), botLeft().getY());
    }

    public Position topRight() {
        int trX = bottomLeft.getX() + width + 1;
        int trY = bottomLeft.getY() + height + 1;
        return new Position(trX, trY);
    }

    public Position topLeft() {
        return new Position(botLeft().getX(), topRight().getY());
    }
}
