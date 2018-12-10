/**
 * Randomized Queue - Dequeues a random element in constant time.
 * <p>
 * Used N sized array with autosizing
 * Array based Stack. Instead of dequeuing from the right, just take a random number i, take that one, and replace i w/ the last element
 * For Iterator: Shuffle, then iterate 0...n
 */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int STARTING_LENGTH = 10;
    private int n; // Index of the next addition and number of elements in array
    private Object[] array;


    public RandomizedQueue() {
        array = new Object[STARTING_LENGTH];
        n = 0;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void enqueue(Item data) {
        if (data == null) throw new IllegalArgumentException("Cannot add a null element");
        array[n++] = data;
        if (n == array.length) growArray();

    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int randomIndex = StdRandom.uniform(n);
        Item element = (Item) array[randomIndex];
        array[randomIndex] = array[n - 1];
        array[n - 1] = null;
        n--;
        if (n < array.length / 4 && array.length < STARTING_LENGTH) shrinkArray();
        return element;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int randomIndex = StdRandom.uniform(n);
        return (Item) array[randomIndex];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current;
        private final int[] permutation;

        RandomizedQueueIterator() {
            current = 0;
            permutation = StdRandom.permutation(n);
        }

        @Override
        public boolean hasNext() {
            return current < permutation.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return (Item) array[permutation[current++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private void growArray() {
        Object[] newArray = new Object[array.length * 2];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        this.array = newArray;
    }

    private void shrinkArray() {
        Object[] newArray = new Object[array.length / 2];
        for (int i = 0; i < n; i++) {
            newArray[i] = array[i];
        }
        this.array = newArray;
    }

}
