import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Random;

public class TestArrayDequeGold {
    private final Random rand = new Random(32);

    @Test
    public void randomTest() {
        String error = "";
        StudentArrayDeque<Integer> sadInt = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> adsInt = new ArrayDequeSolution<>();
        for (int i = 0; i < 1000; i++) {
            int r = rand.nextInt();
            if (r % 2 == 0) {
                sadInt.addFirst(r);
                adsInt.addFirst(r);
                error += "addFirst(" + r + ") \n";
            } else {
                sadInt.addLast(r);
                adsInt.addLast(r);
                error += "addLast(" + r + ") \n";
            }
        }

        while (sadInt.size() > 2) {
            int r = rand.nextInt();
            Integer actual;
            Integer expected;
            if (r % 2 == 0) {
                actual = sadInt.removeFirst();
                expected = adsInt.removeFirst();
                error += "removeFirst(" + r + ") \n";
            } else {
                actual = sadInt.removeLast();
                expected = adsInt.removeLast();
                error += "removeLast(" + r + ") \n";
            }
            assertEquals(error, expected, actual);

        }
    }
}
