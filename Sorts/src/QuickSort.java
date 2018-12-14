import java.util.Arrays;

/**
 * This quicksort uses the traditional implementation of two pointers, one on each side of the partition, and swapping if needed
 * as the pointers converge.
 */
public class QuickSort {

    public static <T extends Comparable<T>> T[] quickSort(T[] data) {
        if (data.length < 2) return data;
        return quickSort(data, 0, data.length);
    }

    private static <T extends Comparable<T>> T[] quickSort(T[] data, int lo, int hi) {
        if (hi - 1 <= lo) return data;
        T key = data[lo];
        int i = lo + 1;
        int j = hi - 1;
        while (i <= j) {
            while (i < hi && data[i].compareTo(key) <= 0) i++;
            while (j > lo && data[j].compareTo(key) > 0) j--;
            if (i < j) swap(data, i, j);
        }
        swap(data, lo, j);
        quickSort(data, lo, j);
        quickSort(data, j + 1, hi);
        return data;
    }

    private static <T> T[] swap(T[] data, int i, int j) {
        T temp = data[i];
        data[i] = data[j];
        data[j] = temp;
        return data;
    }

    public static void main(String[] args) {
        Integer[] numbers = new Integer[]{1, 2, 3, 1};
        numbers = quickSort(numbers);
        System.out.println(Arrays.toString(numbers));
    }
}
