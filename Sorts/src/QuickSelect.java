import java.util.Comparator;
import java.util.Random;

/**
 * Select the nth smallest number from an array.
 * Based on quicksort but has a O(n) runtime ...
 */
public class QuickSelect {

    private QuickSelect() {
    }

    public static <T extends Comparable<T>> T quickSelect(T[] data, int n) {
        if (n > data.length || n < 1 || data.length < 1) throw new IllegalArgumentException();
        if (data.length == 1) return data[0];
        n = n - 1;
        return quickSelect(data, n, T::compareTo);
    }

    public static <T extends Comparable<T>> T quickSelect(T[] data, int n, Comparator<T> comparator) {
        // shuffle
        knuthShuffle(data);
        return quickSelect(data, n, comparator, 0, data.length);
    }

    private static <T extends Comparable<T>> T quickSelect(T[] data, int n, Comparator<T> comparator, int low, int hi) {
        // partition
        // sort smaller ones
        int divider = partition(data, comparator, low, hi);
        if (n > divider) return quickSelect(data, n, comparator, divider + 1, hi);
        if (n < divider) return quickSelect(data, n, comparator, low, divider);
        return data[divider];
    }

    private static <T extends Comparable<T>> int partition(T[] data, Comparator<T> comparator, int low, int hi) {
        T key = data[hi - 1];
        int wall = low;
        for (int i = low; i < hi - 1; i++) {
            if (comparator.compare(data[i], key) <= 0) {
                swap(data, wall++, i);
            }
        }
        swap(data, wall, hi - 1);
        return wall;
    }

    private static void knuthShuffle(Comparable[] data) {
        Random r = new Random();
        for (int i = 1; i < data.length; i++) {
            int j = r.nextInt(i);
            swap(data, i, j);
        }
    }

    private static void swap(Object[] data, int i, int j) {
        Object temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }


}
