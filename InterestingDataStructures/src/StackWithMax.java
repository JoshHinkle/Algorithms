class StackWithMax<T extends Comparable<T>> {

    Node max;
    Node head;

    public StackWithMax() {
    }

    public void push(T data) {
        head = new Node(data).setNext(head);
        if (max == null || data.compareTo(max.data) >= 0) {
            max = new Node(data).setNext(max);
        }
    }

    public T getMax() {
        return max.data;
    }

    public T pop() {
        if (isEmpty()) return null;
        Node oldHead = head;
        head = oldHead.next;
        if (oldHead.data == max.data) {
            max = max.next;
        }
        return oldHead.data;
    }

    public boolean isEmpty() {
        return head == null;
    }


    private class Node {
        T data;
        Node next;

        private Node(T data) {
            this.data = data;
        }

        private Node setNext(Node next) {
            this.next = next;
            return this;
        }
    }
    

}
