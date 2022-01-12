package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Assert;
import org.junit.Test;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayHeapMinPQTest {
    private static Random r = new Random(500);
    private static ExtrinsicMinPQ timeAPQ;
    private static ExtrinsicMinPQ timeNPQ;

    public static ArrayHeapMinPQ discHeap() {
        ArrayHeapMinPQ discPQ = new ArrayHeapMinPQ();
        discPQ.add("f", 6.0);
        discPQ.add("h", 8.0);
        discPQ.add("d", 4.0);
        discPQ.add("b", 2.0);
        discPQ.add("c", 3.0);
        discPQ.add("e", 5.0);
        return discPQ;
    }

    @Test
    public void testDiscHeap() {
        ArrayHeapMinPQ apq = discHeap();
        assertEquals("size doesn't match", 6, apq.size());
        assertEquals("getSmallest is erroring", "b", apq.getSmallest());
        assertTrue("contains d", apq.contains("d"));
        Assert.assertFalse("doesn't contain z", apq.contains("z"));
        assertEquals("b", apq.removeSmallest());
        assertEquals("c", apq.removeSmallest());
        apq.changePriority("d", 10.0);
        assertEquals("e", apq.getSmallest());
    }

    /** Generate a random minHeap of size N. No repeating items. */
    private List<ExtrinsicMinPQ> randomHeap(int N) {
        List<ExtrinsicMinPQ> heaps = new ArrayList<>();
        ArrayHeapMinPQ apq = new ArrayHeapMinPQ();
        NaiveMinPQ npq = new NaiveMinPQ();
        HashSet<Integer> items = new HashSet<>();
        for (int i = 0; i < N; i++) {
            int item = r.nextInt();
            while (items.contains(item)) {
                item = r.nextInt();
            }
            items.add(item);
            double priority = r.nextDouble();
            apq.add(item, priority);
            npq.add(item, priority);
        }
        heaps.add(apq);
        heaps.add(npq);
        return heaps;
    }

    @Test
    public void sanityRemoveSmallest() {
        ArrayHeapMinPQ apq = new ArrayHeapMinPQ();
        NaiveMinPQ npq = new NaiveMinPQ();
        double priority = 1.0;
        for (int i = 10; i > 0; i--) {
            apq.add(i, priority);
            npq.add(i, priority);
            priority -= 0.1;
        }
        assertEquals("get 1", npq.getSmallest(), apq.getSmallest());
        assertEquals("remove 1", npq.removeSmallest(), apq.removeSmallest());
        assertEquals("remove2", npq.removeSmallest(), apq.removeSmallest());
        apq.changePriority(5, 8);
        npq.changePriority(5, 8);
        apq.changePriority(3, 5.5);
        npq.changePriority(3, 5.5);
        assertEquals("remove3", npq.removeSmallest(), apq.removeSmallest());
        assertEquals("get2", npq.getSmallest(), apq.getSmallest());
    }

    /** Tests randomHeap. */
    @Test
    public void generate30() {
        List<ExtrinsicMinPQ> heaps = randomHeap(30);
        ExtrinsicMinPQ apq = heaps.get(0);
        ExtrinsicMinPQ npq = heaps.get(1);
    }

    @Test
    public void basic1000000() {
        List<ExtrinsicMinPQ> heaps = randomHeap(1000000);
        ExtrinsicMinPQ apq = heaps.get(0);
        ExtrinsicMinPQ npq = heaps.get(1);

        assertEquals("get smallest", npq.getSmallest(), apq.getSmallest());
        assertEquals("size", npq.size(), apq.size());
        assertTrue(npq.contains(999) == apq.contains(999));
    }

    private void testRemoveSmallestN(int N) {
        List<ExtrinsicMinPQ> heaps = randomHeap(N);
        ExtrinsicMinPQ apq = heaps.get(0);
        ExtrinsicMinPQ npq = heaps.get(1);
        for (int i = 0; i < N; i++) {
            int smallNpq = (Integer) npq.removeSmallest();
            int smallApq = (Integer) apq.removeSmallest();
            assertEquals(smallNpq, smallApq);
        }
    }

    @Test
    public void testRemoveSmallest10000() {
        testRemoveSmallestN(10000);
    }

    @Test
    public void testContainsRemoveSmallest() {
        setTimeHeap(100000);
        for (int i = 0; i < 1000; i++) {
            int randInt = r.nextInt();
            assertTrue(timeNPQ.contains(randInt) == timeAPQ.contains(randInt));
            assertEquals("getsmallest", timeNPQ.getSmallest(), timeAPQ.getSmallest());
            for (int j = 0; j < 100; j++) {
                assertEquals(timeNPQ.removeSmallest(), timeAPQ.removeSmallest());
            }
        }
    }

    /** Timed tests */
    private void setTimeHeap(int N) {
        List<ExtrinsicMinPQ> heaps = randomHeap(N);
        timeAPQ = heaps.get(0);
        timeNPQ = heaps.get(1);
    }

    private static void printTimingTable(List<Integer> N, List<Double> times,
                                         List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < N.size(); i += 1) {
            int n = N.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", n, time, opCount, timePerOp);
        }
    }

    private static void printTimingTableL(List<Integer> N, List<Long> times,
                                         List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < N.size(); i += 1) {
            int n = N.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", n, time, opCount, timePerOp);
        }
    }

    @Test
    public void timeAdd() {
        List<Integer> N = new ArrayList<>();
        for (int n = 31250; n <= 1000000; n *= 2) {
            N.add(n);
        }
        ArrayList<Double> timesAPQ = new ArrayList<>();
        ArrayList<Double> timesNPQ = new ArrayList<>();

        for (int i = 0; i < N.size(); i++) {
            HashSet<Integer> items = new HashSet<>();
            ArrayHeapMinPQ apq = new ArrayHeapMinPQ();
            NaiveMinPQ npq = new NaiveMinPQ();

            Stopwatch swAPQ = new Stopwatch();
            for (int j = 0; j < N.get(i); j++) {
                int item = r.nextInt();
                while (items.contains(item)) {
                    item = r.nextInt();
                }
                items.add(item);
                double priority = r.nextDouble();

                apq.add(item, priority);
            }
            double timeInSecondsAPQ = swAPQ.elapsedTime();
            timesAPQ.add(timeInSecondsAPQ);

            items.clear();
            Stopwatch swNPQ = new Stopwatch();
            for (int j = 0; j < N.get(i); j++) {
                int item = r.nextInt();
                while (items.contains(item)) {
                    item = r.nextInt();
                }
                items.add(item);
                double priority = r.nextDouble();
                npq.add(item, priority);
            }
            double timeInSecondsNPQ = swNPQ.elapsedTime();
            timesNPQ.add(timeInSecondsNPQ);
        }
        printTimingTable(N, timesAPQ, N);
        printTimingTable(N, timesNPQ, N);
    }

    @Test
    public void timeContains() {
        setTimeHeap(100000);
        List<Integer> N = new ArrayList<>();
        for (int n = 3125; n <= 100000; n *= 2) {
            N.add(n);
        }
        ArrayList<Double> timesAPQ = new ArrayList<>();
        ArrayList<Double> timesNPQ = new ArrayList<>();

        for (int i = 0; i < N.size(); i++) {
            Stopwatch swAPQ = new Stopwatch();
            for (int j = 0; j < N.get(i); j++) {
                timeAPQ.contains(r.nextInt());
            }
            double timeInSecondsAPQ = swAPQ.elapsedTime();
            timesAPQ.add(timeInSecondsAPQ);

            Stopwatch swNPQ = new Stopwatch();
            for (int j = 0; j < N.get(i); j++) {
                timeNPQ.contains(r.nextInt());
            }
            double timeInSecondsNPQ = swNPQ.elapsedTime();
            timesNPQ.add(timeInSecondsNPQ);
        }
        printTimingTable(N, timesAPQ, N);
        printTimingTable(N, timesNPQ, N);
    }

    @Test
    public void timeGetSmallest() {
        List<Integer> N = new ArrayList<>();
        for (int n = 3125; n <= 100000; n *= 2) {
            N.add(n);
        }
        ArrayList<Long> timesAPQ = new ArrayList<>();
        int ops = 1000;
        List<Integer> opCounts = Collections.nCopies(N.size(), ops);

        for (int i = 0; i < N.size(); i++) {
            setTimeHeap(N.get(i));
            long timeElapsed = 0;
            for (int count = 0; count < ops; count++) {
                long start = System.currentTimeMillis();
                timeAPQ.getSmallest();
                long end = System.currentTimeMillis();
                timeElapsed = timeElapsed + (end - start) / 1000;

                for (int remove = 0; remove < N.get(i) / ops - 1; remove++) {
                    timeAPQ.removeSmallest();
                }
            }
            timesAPQ.add(timeElapsed);
        }
        printTimingTableL(N, timesAPQ, opCounts);
    }

    @Test
    public void simpleGetSmallest() {
        setTimeHeap(1000000);

        long timeElapsed = 0;
        for (int op = 0; op < 1000; op++) {
            long start = System.currentTimeMillis();
            timeAPQ.getSmallest();
            long end = System.currentTimeMillis();
            timeElapsed = timeElapsed + (end - start);

            for (int remove = 0; remove < 1000; remove++) {
                timeAPQ.removeSmallest();
            }
        }

        System.out.println("Ended with: " + timeAPQ.size() + " Time taken: " + timeElapsed);
    }

    @Test
    public void timeRemoveSmallestAPQ() {

        List<Integer> N = new ArrayList<>();
        for (int n = 31250; n <= 2000000; n *= 2) {
            N.add(n);
        }
        ArrayList<Double> timesAPQ = new ArrayList<>();
        int ops = 1000;
        List<Integer> opCounts = Collections.nCopies(N.size(), ops);

        for (int i = 0; i < N.size(); i++) {
            setTimeHeap(N.get(i));
            Stopwatch swAPQ = new Stopwatch();
            for (int j = 0; j < N.get(i); j++) {
                timeAPQ.removeSmallest();
            }
            double timeInSecondsAPQ = swAPQ.elapsedTime();
            timesAPQ.add(timeInSecondsAPQ);
        }
        printTimingTable(N, timesAPQ, N);
    }

    @Test
    public void timeRemoveSmallestNPQ() {
        List<Integer> N = new ArrayList<>();
        for (int n = 3125; n <= 100000; n *= 2) {
            N.add(n);
        }
        ArrayList<Double> timesNPQ = new ArrayList<>();

        for (int i = 0; i < N.size(); i++) {
            setTimeHeap(N.get(i));
            Stopwatch swNPQ = new Stopwatch();
            for (int j = 0; j < N.get(i); j++) {
                timeNPQ.removeSmallest();
            }
            double timeInSecondsAPQ = swNPQ.elapsedTime();
            timesNPQ.add(timeInSecondsAPQ);
        }
        printTimingTable(N, timesNPQ, N);
    }

    @Test
    public void testChangePriority() {
        HashSet<Integer> keys = new HashSet<>();
        int[] items = new int[100000];
        NaiveMinPQ npq = new NaiveMinPQ();
        ArrayHeapMinPQ apq = new ArrayHeapMinPQ();

        for (int i = 0; i < 100000; i++) {
            int item = r.nextInt();
            while (keys.contains(item)) {
                item = r.nextInt();
            }
            keys.add(item);
            double priority = r.nextDouble();
            items[i] = item;
            apq.add(item, priority);
            npq.add(item, priority);
        }

        for (int i = 0; i < 100; i++) {
            assertEquals(npq.getSmallest(), apq.getSmallest());
            for (int j = i; j < 100000; j += 1000) {
                double randomP = r.nextDouble();
                apq.changePriority(items[j], randomP);
                npq.changePriority(items[j], randomP);
            }
        }
    }

    @Test
    public void timeAPQChangePriority() {
        List<Integer> N = new ArrayList<>();
        for (int n = 31250; n <= 1000000; n *= 2) {
            N.add(n);
        }
        ArrayList<Double> timesAPQ = new ArrayList<>();
        int ops = 1000;
        List<Integer> opCounts = Collections.nCopies(N.size(), ops);

        for (int i = 0; i < N.size(); i++) {
            HashSet<Integer> keys = new HashSet<>();
            int[] items = new int[N.get(i)];
            ArrayHeapMinPQ apq = new ArrayHeapMinPQ();

            for (int j = 0; j < N.get(i); j++) {
                int item = r.nextInt();
                while (keys.contains(item)) {
                    item = r.nextInt();
                }
                keys.add(item);
                double priority = r.nextDouble();
                items[j] = item;
                apq.add(item, priority);
            }

            int factor = N.get(i) / ops - 1;
            Stopwatch sw = new Stopwatch();
            for (int count = 0; count < ops; count++) {
                apq.changePriority(items[count * factor], r.nextDouble());
            }
            double timeElapsed = sw.elapsedTime();
            timesAPQ.add(timeElapsed);
        }
        printTimingTable(N, timesAPQ, opCounts);
    }

    @Test
    public void timeNPQChangePriority() {
        List<Integer> N = new ArrayList<>();
        for (int n = 31250; n <= 1000000; n *= 2) {
            N.add(n);
        }
        ArrayList<Double> timesAPQ = new ArrayList<>();
        int ops = 1000;
        List<Integer> opCounts = Collections.nCopies(N.size(), ops);

        for (int i = 0; i < N.size(); i++) {
            HashSet<Integer> keys = new HashSet<>();
            int[] items = new int[N.get(i)];
            NaiveMinPQ apq = new NaiveMinPQ();

            for (int j = 0; j < N.get(i); j++) {
                int item = r.nextInt();
                while (keys.contains(item)) {
                    item = r.nextInt();
                }
                keys.add(item);
                double priority = r.nextDouble();
                items[j] = item;
                apq.add(item, priority);
            }

            int factor = N.get(i) / ops - 1;
            Stopwatch sw = new Stopwatch();
            for (int count = 0; count < ops; count++) {
                apq.changePriority(items[count * factor], r.nextDouble());
            }
            double timeElapsed = sw.elapsedTime();
            timesAPQ.add(timeElapsed);
        }
        printTimingTable(N, timesAPQ, opCounts);
    }
}
