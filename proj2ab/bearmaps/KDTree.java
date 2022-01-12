package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private Node root;
    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;

    private class Node {
        private Point p;
        private Node left, right;
        private boolean direction;

        Node(Point p, boolean direction) {
            this.p = p;
            this.direction = direction;
        }
    }

    public KDTree(List<Point> points) {
        for (Point p: points) {
            root = add(p, root, HORIZONTAL);
        }
    }

    private Node add(Point p, Node n, boolean direction) {
        if (n == null) {
            return new Node(p, direction);
        }
        if (p.equals(n.p)) {
            return n;
        }
        double cmp = comparePoints(p, n.p, direction);
        if (cmp < 0) {
            n.left = add(p, n.left, !direction);
        } else if (cmp >= 0) {
            n.right = add(p, n.right, !direction);
        }
        return n;
    }

    private double comparePoints(Point p1, Point p2, boolean direction) {
        if (direction == HORIZONTAL) {
            return Double.compare(p1.getX(), p2.getX());
        } else {
            return Double.compare(p1.getY(), p2.getY());
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        return nearestHelper(root, goal, root).p;
    }

    private Node nearestHelper(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        if (Point.distance(n.p, goal) < Point.distance(best.p, goal)) {
            best = n;
        }

        Node goodSide;
        Node badSide;
        if (comparePoints(goal, n.p, n.direction) < 0) {
            goodSide = n.left;
            badSide = n.right;
        } else {
            goodSide = n.right;
            badSide = n.left;
        }

        best = nearestHelper(goodSide, goal, best);

        if (pruning(n, goal, best)) {
            best = nearestHelper(badSide, goal, best);
        }
        return best;
    }

    private boolean pruning(Node n, Point goal, Node best) {
        if (n.direction == HORIZONTAL) {
            return Math.pow(goal.getX() - n.p.getX(), 2) < Point.distance(best.p, goal);
        } else {
            return Math.pow(goal.getY() - n.p.getY(), 2) < Point.distance(best.p, goal);
        }
    }
}
