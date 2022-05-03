package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


/** Performs some basic linked list tests. */
public class ArrayDequeTest {

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void rand_add_remove_Test() {
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        LinkedListDeque<Integer> lld = new LinkedListDeque<Integer>();
        for (int j= 0; j < 100; j += 1){

            for (int i = 0; i < 100; i += 1) {
                int operationNumber = StdRandom.uniform(0, 2);
                if (operationNumber == 0) {
                    // addLast
                    int randVal = StdRandom.uniform(0, 100);
                    ad.addLast(randVal);
                    lld.addLast(randVal);
                    System.out.println("addLast(" + randVal + ")");
                } else if (operationNumber == 1) {
                    // addfirst
                    int randVal = StdRandom.uniform(0, 100);
                    ad.addFirst(randVal);
                    lld.addFirst(randVal);
                    System.out.println("addfirst(" + randVal + ")");
                }
            }
            System.out.println("开始测试"+j);
            for (int i = 0; i < 100; i += 1) {
                int operationNumber = StdRandom.uniform(0, 2);
                if (operationNumber == 0) {
                    // addLast
                    assertEquals(ad.removeFirst(),lld.removeFirst());
                } else if (operationNumber == 1) {
                    // addfirst
                    assertEquals(ad.removeLast(),lld.removeLast());
                }
            }
        }
    }


    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void rand_get_Test() {
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        LinkedListDeque<Integer> lld = new LinkedListDeque<Integer>();

        for (int i = 0; i < 100; i += 1) {
            int operationNumber = StdRandom.uniform(0, 2);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                ad.addLast(randVal);
                lld.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // addfirst
                int randVal = StdRandom.uniform(0, 100);
                ad.addFirst(randVal);
                lld.addFirst(randVal);
                System.out.println("addfirst(" + randVal + ")");
            }
        }
        for (int i = 0; i < 100; i += 1) {
            int randVal = StdRandom.uniform(0, 100);
            assertEquals(ad.get(randVal),lld.get(randVal));
            System.out.println("get(" + ad.get(randVal) + ")");
        }


    }
    @Test

    public void addIsEmptySizeTest() {


        ArrayDeque<String> ad1 = new ArrayDeque<String>();

        assertTrue("A newly initialized LLDeque should be empty", ad1.isEmpty());
        ad1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, ad1.size());
        assertFalse("ad1 should now contain 1 item", ad1.isEmpty());

        ad1.addLast("middle");
        assertEquals(2, ad1.size());

        ad1.addLast("back");
        assertEquals(3, ad1.size());

        System.out.println("Printing out deque: ");
        ad1.printDeque();

    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {


        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        // should be empty
        assertTrue("ad1 should be empty upon initialization", ad1.isEmpty());

        ad1.addFirst(10);
        // should not be empty
        assertFalse("ad1 should contain 1 item", ad1.isEmpty());

        ad1.removeFirst();
        // should be empty
        assertTrue("ad1 should be empty after removal", ad1.isEmpty());

    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {


        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(3);

        ad1.removeLast();
        ad1.removeFirst();
        ad1.removeLast();
        ad1.removeFirst();

        int size = ad1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);

    }

    @Test
    /* Check if you can create ArrayDeques with different parameterized types*/
    public void multipleParamTest() {


        ArrayDeque<String>  ad1 = new ArrayDeque<String>();
        ArrayDeque<Double>  ad2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> ad3 = new ArrayDeque<Boolean>();

        ad1.addFirst("string");
        ad2.addFirst(3.14159);
        ad3.addFirst(true);

        String s = ad1.removeFirst();
        double d = ad2.removeFirst();
        boolean b = ad3.removeFirst();

    }

    @Test
    /* check if null is return when removing from an empty ArrayDeque. */
    public void emptyNullReturnTest() {


        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, ad1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, ad1.removeLast());


    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigADequeTest() {


        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            ad1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) ad1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) ad1.removeLast(), 0.0);
        }


    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void getDequeTest() {

        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 100; i++) {
            ad1.addLast(i);
        }

        for (int i = 0; i < 50; i++) {
            assertEquals("Should have the same value", i, (double) ad1.get(i), 0.0);
        }



    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void copy_d_test() {

        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        for (int i = 0; i < 100; i++) {
            ad.addLast(i);
        }

        ArrayDeque<Integer> ad_target = new ArrayDeque<Integer>(ad);

        for (int i = 0; i < 100; i++) {
            assertEquals("Should have the same value", ad.removeFirst(), ad_target.get(i), 0.0);
        }



    }
}
