public class MaxHeap<T extends Comparable> {

    private T[] data;
    private int size;

    public MaxHeap(T[] originalData) {
        for (T t : originalData) {
            if (t == null) {
                throw new IllegalArgumentException("Array must be full before building heap");
            }
        }
        this.data = originalData;
        this.size = data.length;
        resizeUp();
        buildHeap();
    }

    public MaxHeap() {
        this.data = (T[]) new Comparable[10];
        this.size = 0;
        buildHeap();
    }

    private void buildHeap() {
        for (int i = size / 2; i >= 0; i--) {
            sink(i);
        }
    }

    public void add(T item) {
        if (size == data.length) {
            resizeUp();
        }
        data[size] = item;
        swim(size);
        size++;
    }

    public T removeMax() {
        if (size == 0) {
            return null;
        }
        size--;
        swap(0, size);
        sink(0);
        if (size < data.length / 4) resizeDown();
        return data[size];
    }

    public T peek() {
        return data[0];
    }

    private void resizeUp() {
        T[] dataReplacement = (T[]) new Comparable[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            dataReplacement[i] = data[i];
        }
        data = dataReplacement;
    }

    private void resizeDown() {
        T[] dataReplacement = (T[]) new Comparable[data.length / 2];
        for (int i = 0; i < dataReplacement.length; i++) {
            dataReplacement[i] = data[i];
        }
        data = dataReplacement;
    }

    private void sink(int index) {
        int leftChild = leftChild(index);
        int rightChild = rightChild(index);
        boolean smallerThanLeft = leftChild < size && data[index].compareTo(data[leftChild]) < 0;
        boolean smallerThanRight = rightChild < size && data[index].compareTo(data[rightChild]) < 0;
        if (smallerThanLeft && smallerThanRight) {
            int maxChildIndex = data[leftChild].compareTo(data[rightChild]) >= 0 ? leftChild : rightChild;
            swap(index, maxChildIndex);
            sink(maxChildIndex);
        } else if (smallerThanLeft) {
            swap(index, leftChild);
            sink(leftChild);
        } else if (smallerThanRight) {
            swap(index, rightChild);
            sink(rightChild);
        }
    }


    private void swim(int index) {
        if (index > 0) {
            int parent = parent(index);
            if (data[index].compareTo(data[parent]) > 0) {
                swap(index, parent);
                swim(parent);
            }
        }
    }

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private void swap(int i, int j) {
        T temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
}
