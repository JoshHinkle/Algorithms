import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Gets the mth smallest element in an array.
 * Implementation uses a maxheap backed by a priority queue.
 * Can easily find nth largest by flipping comparator.
 * n*lg(m) runtime. -> If m << n approx. O(n)
 */
public class HeapSelect {
    private HeapSelect() {
    }

    public static <T extends Comparable<T>> T heapSelect(T[] data, int n) {
        return heapSelect(data, n, T::compareTo);
    }

    public static <T extends Comparable<T>> T heapSelect(T[] data, int n, Comparator<T> comparator) {
        if (n > data.length || data == null || n < 1) throw new IllegalArgumentException();
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) throw new IllegalArgumentException();
        }
        if (data.length == 1) return data[0];
        MaxHeap<T> minHeap = new MaxHeap<>(n, (a, b) -> -1 * comparator.compare(a, b));
        for (int i = 0; i < data.length; i++) {
            minHeap.add(data[i]);
        }
        return minHeap.poll();
    }


    private static class MaxHeap<T> extends PriorityQueue<T> {

        int maxCapacity;
        Comparator<T> comparator;

        private MaxHeap(int capacity, Comparator<T> comparator) {
            super(capacity, comparator);
            maxCapacity = capacity;
            this.comparator = comparator;
        }

        @Override
        public boolean add(T t) {
            if (super.size() < maxCapacity) {
                return super.add(t);
            } else if (comparator.compare(t, super.peek()) > 0) {
                super.remove();
                return this.add(t);
            } else {
                return false;
            }
        }
    }

}
