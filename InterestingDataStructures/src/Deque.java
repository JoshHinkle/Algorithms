import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Basic Deque implementation (Iterable from front to back)
 */
public class Deque<Item> implements Iterable<Item> {

    private final Node<Item> head;
    private int size;

    public Deque() {
        head = new Node<Item>(null);
        head.next = head;
        head.previous = head;
    }

    public void addFirst(Item data) {
        if (data == null) throw new IllegalArgumentException();
        size++;
        head.next = new Node<>(data).setNext(head.next).setPrevious(head);
        head.next.next.setPrevious(head.next);
    }

    public void addLast(Item data) {
        if (data == null) throw new IllegalArgumentException();
        size++;
        head.previous = new Node<>(data).setNext(head).setPrevious(head.previous);
        head.previous.previous.setNext(head.previous);
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        size--;
        Node<Item> returnNode = head.next;
        head.next = returnNode.next.setPrevious(head);
        return returnNode.data;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        size--;
        Node<Item> returnNode = head.previous;
        head.previous = returnNode.previous.setNext(head);
        return returnNode.data;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return head.next == head;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator(head);
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> node;

        private DequeIterator(Node<Item> node) {
            this.node = node;
        }

        @Override
        public boolean hasNext() {
            return node.next != head;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            node = node.next;
            return node.data;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node<T> {
        private final T data;
        private Node<T> next;
        private Node<T> previous;

        private Node(T data) {
            this.data = data;
        }

        private Node<T> setNext(Node<T> nextNode) {
            this.next = nextNode;
            return this;
        }

        private Node<T> setPrevious(Node<T> previousNode) {
            this.previous = previousNode;
            return this;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        deque.addLast(2);
        deque.addLast(1);
        for (Integer i : deque) {
            System.out.println(i);
        }
        deque.removeLast();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
    }

}
