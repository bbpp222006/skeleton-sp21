package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import timingtest.AList;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {

        AListNoResizing<Integer> simple_alist = new AListNoResizing<>();
        BuggyAList<Integer> buggy_alist = new BuggyAList<>();

        int[] test_array = {1, 2, 3};
        for (int i = 0; i < test_array.length; i++) {
            simple_alist.addLast(test_array[i]);
            buggy_alist.addLast(test_array[i]);
        }

        for (int i = 0; i < test_array.length; i++) {
            assertEquals(simple_alist.getLast(), buggy_alist.getLast());
        }

    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                correct.addLast(randVal);
                broken.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1 && correct.size() > 0) {
                // getLast
                int p = correct.getLast();
                assertEquals(correct.getLast(), broken.getLast());
                System.out.println("getLast(" + p + ")");

            } else if (operationNumber == 2 && correct.size() > 0) {
                // removeLast

                int c = correct.removeLast();
                int b = broken.removeLast();
                assertEquals(c,b);
                assertEquals(correct.size(), broken.size());
                System.out.println("getLast(" + c+"=="+b + ")");

            }


        }
    }
}
