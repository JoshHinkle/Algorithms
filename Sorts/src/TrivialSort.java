import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class TrivialSort {

    private TrivialSort() {
    }

    /**
     * Selection Sort:
     * Iterating through each position i...n-1, replace the ith element with the smallest in the array
     */
    public static Comparable[] selectionSort(Comparable[] input) {
        for (int i = 0; i < input.length; i++) {
            int j = findMin(input, i);
            swap(input, i, j);
        }
        return input;
    }

    private static int findMin(Comparable[] input, int startingIndex) {
        int minIndex = startingIndex;
        for (int i = startingIndex + 1; i < input.length; i++) {
            if (input[i].compareTo(input[minIndex]) < 0) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    private static void swap(Comparable[] input, int i, int j) {
        Comparable aux = input[i];
        input[i] = input[j];
        input[j] = aux;
    }

    public static Comparable[] insertionSort(Comparable[] input) {
        int j;
        for (int i = 1; i < input.length; i++) {
            j = i;
            while (j > 0) {
                if (input[j - 1].compareTo(input[j]) > 0) {
                    swap(input, j, j - 1);
                    j--;
                } else break;
            }
        }
        return input;
    }

    public static boolean isSorted(Comparable[] input) {
        for (int i = 0; i < input.length - 1; i++) {
            if (input[i].compareTo(input[i + 1]) > 0) return false;
        }
        return true;
    }


    public static void main(String[] args) {
        Integer[] ints = new Integer[10];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = StdRandom.uniform(100);
        }
        System.out.println(Arrays.toString(ints));
        Long startTime = System.nanoTime();
        selectionSort(ints);
        Long endTime = System.nanoTime();
        System.out.println(Arrays.toString(ints));
        System.out.println((endTime - startTime) / 1000000);
        System.out.println(isSorted(ints) ? "Successfully Sorted!" : "booo not sorted");

    }
}
