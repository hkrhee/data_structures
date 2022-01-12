package bearmaps;

import java.util.ArrayList;
import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> data;

    public NaivePointSet(List<Point> points) {
        data = new ArrayList<>();
        for (Point p: points) {
            data.add(p);
        }
        data = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Point best = data.get(0);
        for (int i = 0; i < data.size(); i++) {
            if (Point.distance(goal, data.get(i)) < Point.distance(goal, best)) {
                best = data.get(i);
            }
        }
        return best;
    }


    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        ret.getX(); // evaluates to 3.3
        ret.getY(); // evaluates to 4.4
    }
}
