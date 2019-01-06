import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MaxHeapTest {

    @org.junit.Test
    public void buildHeap() {
        Integer[] data = {1, 2, 3, 4};
        MaxHeap<Integer> maxHeap = new MaxHeap<>(data);
        int a = maxHeap.removeMax();
        int b = maxHeap.removeMax();
        int c = maxHeap.removeMax();
        int d = maxHeap.removeMax();

        assertEquals(4, a);
        assertEquals(3, b);
        assertEquals(2, c);
        assertEquals(1, d);
    }

    @org.junit.Test
    public void add() {

        Integer[] data = {1, 2, 3, 4};
        MaxHeap<Integer> maxHeap = new MaxHeap<>(data);
        int a = maxHeap.removeMax();
        int b = maxHeap.removeMax();
        int c = maxHeap.removeMax();
        int d = maxHeap.removeMax();

        assertEquals(4, a);
        assertEquals(3, b);
        assertEquals(2, c);
        assertEquals(1, d);

        assertNull(maxHeap.removeMax());

        maxHeap.add(20);
        maxHeap.add(10);
        maxHeap.add(30);
        maxHeap.add(100);
        maxHeap.add(500);

        maxHeap.add(7);
        a = maxHeap.removeMax();
        b = maxHeap.removeMax();
        c = maxHeap.removeMax();
        d = maxHeap.removeMax();

        assertEquals(500, a);
        assertEquals(100, b);
        assertEquals(30, c);
        assertEquals(20, d);


    }

}