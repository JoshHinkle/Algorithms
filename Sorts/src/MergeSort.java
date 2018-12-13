import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class MergeSort {
    private MergeSort() {
    }

    public static Comparable[] mergeSort(Comparable[] input) {
        Comparable[] aux = new Comparable[input.length];
        return mergeSort(input, aux, 0, input.length);
    }

    private static Comparable[] mergeSort(Comparable[] input, Comparable[] aux, int start, int end) {
        if (start == end - 1) return new Comparable[]{input[start]};
        int mid = start + (end - start) / 2 + ((end - start) % 2 == 0 ? 0 : 1);
        mergeSort(input, aux, start, mid);
        mergeSort(input, aux, mid, end);
        return merge(input, aux, start, mid, end);
    }

    private static Comparable[] merge(Comparable[] input, Comparable[] aux, int start, int mid, int end) {
        for (int i = start; i < end; i++) {
            aux[i] = input[i];
        }
        int i = start;
        int j = mid;
        for (int k = start; k < end; k++) {
            if (i >= mid) input[k] = aux[j++];
            else if (j >= end) input[k] = aux[i++];
            else if (aux[i].compareTo(aux[j]) <= 0) input[k] = aux[i++];
            else input[k] = aux[j++];
        }
        return input;
    }


    public static Comparable[] bottomUpMergeSort(Comparable[] input) {
        Comparable[] aux = new Comparable[input.length];
        for (int step = 1; step < input.length; step *= 2) {
            for (int start = 0; start < input.length; start += 2 * step) {
                merge(input, aux, start, start + step, Math.min(start + (step * 2), input.length));
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
        Integer[] ints = new Integer[20];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = StdRandom.uniform(100);
        }
        System.out.println(Arrays.toString(ints));
        Long startTime = System.nanoTime();
        bottomUpMergeSort(ints);
        Long endTime = System.nanoTime();
        System.out.println(Arrays.toString(ints));
        System.out.println((endTime - startTime) / 1000000);
        System.out.println(isSorted(ints) ? "Successfully Sorted!" : "booo not sorted");
    }
}
