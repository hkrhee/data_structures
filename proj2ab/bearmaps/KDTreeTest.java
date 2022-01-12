package bearmaps;
/**
 * @source: https://www.youtube.com/watch?v=lp80raQvE5c&feature=youtu.be&ab_channel=JoshHug
 * Professor Josh Hug, March 18, 2019 - Writing my randomized tests for nearest
 */

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KDTreeTest {
    private static Random r = new Random(500);

    private static KDTree lecTree() {
        Point a = new Point(2, 3);
        Point z = new Point(4, 2);
        Point b = new Point(4, 2);
        Point c = new Point(4, 5);
        Point d = new Point(3, 3);
        Point e = new Point(1, 5);
        Point f = new Point(4, 4);

        KDTree kd = new KDTree(List.of(a, z, b, c, d, e, f));
        return kd;
    }

    @Test
    /** Example from lec 19 slides. */
    public void lecTestNearest() {
        KDTree kd = lecTree();
        Point actual = kd.nearest(0, 7);
        Point expected = new Point(1, 5);
        assertEquals(expected, actual);
    }

    /** return N Random points. */
    private List<Point> randomPoints(int N) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            double x = r.nextDouble();
            double y = r.nextDouble();
            points.add(new Point(x, y));
        }
        return points;
    }

    public void testGeneral(int numPoints, int numGoals) {
        List<Point> points = randomPoints(numPoints);
        NaivePointSet nps = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        List<Point> goals = randomPoints(numGoals);
        for (Point p: goals) {
            Point expected = nps.nearest(p.getX(), p.getY());
            double distanceExpected = Point.distance(expected, p);
            Point actual = kd.nearest(p.getX(), p.getY());
            double distanceActual = Point.distance(actual, p);
            assertEquals(distanceExpected, distanceActual, 1e-8);
        }
    }

    public void testCompareTime(int numPoints, int numGoals) {
        List<Point> points = randomPoints(numPoints);
        NaivePointSet nps = new NaivePointSet(points);
        KDTree kd = new KDTree(points);
        List<Point> goals = randomPoints(numGoals);

        Stopwatch npsSw = new Stopwatch();
        for (Point p: goals) {
            nps.nearest(p.getX(), p.getY());
        }
        double npsTime = npsSw.elapsedTime();
        System.out.println("NPS Nearest Time: " + npsTime);

        Stopwatch kdSw = new Stopwatch();
        for (Point p: goals) {
            kd.nearest(p.getX(), p.getY());
        }
        double kdTime = kdSw.elapsedTime();
        System.out.println("KDNearest Time: " + kdTime);

        assertTrue("kd tree runtime is too slow.", kdTime / npsTime < 0.1);
    }

    @Test
    public void smallTest() {
        testGeneral(100, 20);
    }

    @Test
    public void testMedium() {
        testGeneral(2000, 2000);
    }

    @Test
    public void testExtreme() {
        testGeneral(5000, 2000);
    }

    @Test
    public void megaExtreme() {
        testGeneral(100000, 10000);
    }

    @Test
    public void testCompareNaiveKD1000() {
        testCompareTime(2000, 500);
    }

    @Test
    public void testCompareNaiveKD100000() {
        testCompareTime(100000, 10000);
    }


    private static void printTimingTable(List<Integer> Ns, List<Double> times,
                                         List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    @Test
    public void testKDConstructor() {
        List<Integer> Ns = new ArrayList<>();
        for (int n = 31250; n <= 2000000; n *= 2) {
            Ns.add(n);
        }
        ArrayList<Double> times = new ArrayList<>();
        for (int i = 0; i < Ns.size(); i++) {
            List<Point> points = randomPoints(Ns.get(i));
            Stopwatch sw = new Stopwatch();
            KDTree kd = new KDTree(points);
            double timeInSeconds = sw.elapsedTime();
            times.add(timeInSeconds);
        }
        printTimingTable(Ns, times, Ns);
    }

    @Test
    public void testKDNearest() {
        List<Integer> N = new ArrayList<>();
        for (int n = 31250; n <= 1000000; n *= 2) {
            N.add(n);
        }
        ArrayList<Double> times = new ArrayList<>();
        int ops = 1000000;
        List<Integer> opCounts = Collections.nCopies(N.size(), ops);
        List<Point> goals = randomPoints(ops);

        for (int i = 0; i < N.size(); i++) {
            List<Point> points = randomPoints(N.get(i));
            KDTree kd = new KDTree(points);

            Stopwatch sw = new Stopwatch();
            for (Point p: goals) {
                kd.nearest(p.getX(), p.getY());
            }
            double timeInSeconds = sw.elapsedTime();
            times.add(timeInSeconds);
        }
        printTimingTable(N, times, opCounts);
    }

    @Test
    public void testNPSNearest() {
        List<Integer> N = new ArrayList<>();
        for (int n = 125; n <= 1000; n *= 2) {
            N.add(n);
        }
        ArrayList<Double> times = new ArrayList<>();
        int ops = 1000000;
        List<Integer> opCounts = Collections.nCopies(N.size(), ops);
        List<Point> goals = randomPoints(ops);

        for (int i = 0; i < N.size(); i++) {
            List<Point> points = randomPoints(N.get(i));
            NaivePointSet nps = new NaivePointSet(points);

            Stopwatch sw = new Stopwatch();
            for (Point p: goals) {
                nps.nearest(p.getX(), p.getY());
            }
            double timeInSeconds = sw.elapsedTime();
            times.add(timeInSeconds);
        }
        printTimingTable(N, times, opCounts);
    }
}
