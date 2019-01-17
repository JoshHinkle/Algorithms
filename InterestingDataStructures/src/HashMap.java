// Assume hashCode function;
public class HashMap<K, V> {

    private Entry<K, V>[] data;
    private double loadFactor;
    private int size;

    private static final double defaultLoadFactor = 0.75;
    private static final int defaultCapacity = 16;

    public HashMap() {
        this(defaultCapacity, defaultLoadFactor);
    }

    public HashMap(int capacity) {
        this(capacity, defaultLoadFactor);
    }

    public HashMap(double loadFactor) {
        this(defaultCapacity, loadFactor);
    }

    public HashMap(int capacity, double loadFactor) {
        this.size = 0;
        this.loadFactor = loadFactor;
        capacity = (int) Math.pow(2, Math.ceil(Math.log(capacity) / Math.log(2)));
        data = new Entry[capacity];
    }

    public V put(K key, V value) {
        int bucket = getBucket(key);
        Entry<K, V> currentNode = data[bucket];
        while (currentNode != null) {
            if (currentNode.key.equals(key)) {
                V oldValue = currentNode.value;
                currentNode.value = value;
                return oldValue;
            }
            currentNode = currentNode.next;
        }
        currentNode = new Entry(key, value);
        currentNode.next = data[bucket];
        data[bucket] = currentNode;
        if (++size > loadFactor * data.length) resizeUp();
        return null;
    }

    public V get(K key) {
        int bucket = getBucket(key);
        Entry<K, V> currentNode = data[bucket];
        while (currentNode != null) {
            if (currentNode.key.equals(key)) {
                return currentNode.value;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    public V remove(K key) {
        int bucket = getBucket(key);
        Entry<K, V> currentNode = data[bucket];
        Entry<K, V> previousNode = null;
        while (currentNode != null) {
            if (currentNode.key.equals(key)) {
                size--;
                if (previousNode == null) {
                    // This is the first node
                    data[bucket] = currentNode.next;
                } else {
                    previousNode.next = currentNode.next;
                }
                return currentNode.value;
            }
            currentNode = currentNode.next;
        }
        return null;

    }

    public int size() {
        return size;
    }

    private int getBucket(K key) {
        return key.hashCode() & data.length - 1;
    }

    private void resizeUp() {
        Entry<K, V>[] replacementData = new Entry[data.length * 2];
        Entry<K, V>[] oldData = data;
        size = 0;
        data = replacementData;
        for (Entry<K, V> entry : oldData) {
            put(entry.key, entry.value);
        }
    }

    public static class Entry<Q, T> {
        private Q key;
        private T value;
        private Entry next;

        public Entry(Q key, T value) {
            this.key = key;
            this.value = value;
        }

        public void setNext(Entry next) {
            this.next = next;
        }
    }
}
