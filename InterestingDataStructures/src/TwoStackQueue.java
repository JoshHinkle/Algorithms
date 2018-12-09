/**
 * Implementation of a queue from 2 stacks
 * Dequeue and Enqueue done in CONSTANT amortized time
 *
 * @param <T> generic data
 *            <p>
 *            Explanation:
 *            Each element is enqueued in constant time as it is simply pushed onto one of the stacks.
 *            Each dequeue is also constant as it is a pop from stack 2.
 *            HOWEVER, if stack 2 is empty, the contents of stack 1 are popped and pushed onto stack 2.
 *            While this operation is linear, each element can only be transferred from stack 1 to stack 2 ONE TIME.
 *            Using amortized analysis, 'pay' for the transfer operation of each element (a pop and push) when the element is first enqueued.
 *            As such, both operations are in (amortized) CONSTANT TIME >.>
 */
public class TwoStackQueue<T> {

    Stack<T> stack1;
    Stack<T> stack2;

    public TwoStackQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void push(T data) {
        stack1.push(data);
    }

    public T pop() {
        if (isEmpty()) return null;
        if (stack2.isEmpty()) {
            transferStacks();
        }
        return stack2.pop();
    }

    private void transferStacks() {
        while (!stack1.isEmpty()) {
            T transferData = stack1.pop();
            stack2.push(transferData);
        }
    }

    /**
     * Return empty if both stacks are empty
     *
     * @return
     */
    public boolean isEmpty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }

    /**
     * Simple stack data structure
     *
     * @param <T> generic data
     */
    private class Stack<T> {
        Node<T> head;

        boolean isEmpty() {
            return head == null;
        }

        T pop() {
            if (head == null) return null;
            Node<T> oldHead = this.head;
            this.head = oldHead.next;
            return oldHead.data;
        }

        void push(T data) {
            Node<T> newHead = new Node<>(data);
            newHead.next = this.head;
            this.head = newHead;
        }

        private class Node<T> {
            private Node next;
            private T data;

            private Node(T data) {
                this.data = data;
            }
        }
    }
}
