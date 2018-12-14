import java.util.Arrays;
import java.util.Random;

/**
 * This quicksort uses a slightly different approach from the other. Insetad of converging pointers,
 * just keep track of the farthest right smaller-than-key value and swap it as you come across more.
 */
public class QuickSort2 {

    public static <T extends Comparable<T>> void quickSort(T[] data) {
        shuffle(data);
        quickSort(data, 0, data.length);
    }

    private static <T extends Comparable<T>> void quickSort(T[] data, int lo, int hi) {
        if (lo >= hi - 1) return;
        int divider = partition(data, lo, hi);
        quickSort(data, lo, divider);
        quickSort(data, divider + 1, hi);
    }

    private static <T extends Comparable<T>> int partition(T[] data, int lo, int hi) {
        T key = data[hi - 1];
        int wall = lo;
        for (int i = lo; i < hi - 1; i++) {
            if (data[i].compareTo(key) <= 0) {
                swap(data, wall++, i);
            }
        }
        swap(data, hi - 1, wall);
        return wall;
    }

    private static void shuffle(Object[] data) {
        Random r = new Random();
        for (int i = 1; i < data.length; i++) {
            int index = r.nextInt(i + 1);
            swap(data, index, i);
        }
    }

    private static void swap(Object[] data, int i, int j) {
        Object temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public static void main(String[] args) {
        Integer[] numbers = new Integer[]{-19, -20};
        quickSort(numbers);
        System.out.println(Arrays.toString(numbers));
    }
}
