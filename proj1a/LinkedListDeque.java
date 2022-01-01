/**
 * Create a deque from linked lists of generic type T.
 * @author Helen Rhee on 9/10/2020
 */
public class LinkedListDeque<T> {
    /**
     * Nested class StuffNode is the internal data structure.
     * LinkedListDeque will be a layer of abstraction between
     * the user and StuffNode.
     */
    private class StuffNode {

        /** StuffNode prev is the previous node in the linked list. */
        private StuffNode prev;

        /** Item stores the value of the StuffNode. */
        private T item;

        /** StuffNode next is the next node in the linked list. */
        private StuffNode next;

        /**
         * Constructs a StuffNode that can hold generic type T.
         * @param p points to the previous StuffNode.
         * @param i item of generic type T of StuffNode.
         * @param n points to the next StuffNode
         */
        private StuffNode(StuffNode p, T i, StuffNode n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    /** Sentinel is a placeholder node that keeps track of
     * the front and back of the deque. */
    private StuffNode sentinel;

    /** Keeps track of the size of the deque. */
    private int size;

    /**
     * Creates a T deque.
     */
    public LinkedListDeque() {
        sentinel = new StuffNode(sentinel, null, sentinel);
        size = 0;
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }
//
//    public LinkedListDeque(T x) {
//        sentinel = new StuffNode(sentinel, x, sentinel);
//    }

    /**
     * @param x Adds T x to the front of the deque.
     */
    public void addFirst(T x) {
        StuffNode node = new StuffNode(sentinel, x, sentinel.next);
        sentinel.next.prev = node;
        sentinel.next = node;
        size += 1;
    }

    /**
     * Adds item x to the end of the list.
     * @param x of generic type T
     */
    public void addLast(T x) {
        StuffNode node = new StuffNode(sentinel.prev, x, sentinel);
        sentinel.prev.next = node;
        sentinel.prev = node;
        size += 1;
    }

    /**
     * Checks if deque is empty or not.
     * @return boolean true if deque is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the deque.
     * @return int size of deque.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Print out a new line after all items.
     */
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println(" ");
    }

    /**
     * Removes the first T of the deque.
     * @return removed T at the front of the deque.
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            T first = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return first;
        }
    }

    /**
     * Removes an item from the end of the list.
     * @return T item from the end of the deque.
     */
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            T last = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return last;
        }
    }

    /**
     * Gets the ith item of the list.
     * @param i int i index.
     * @return T of the deque at index i.
     */
    public T get(int i) {
        if (i >= size) {
            return null;
        }
        StuffNode p = sentinel;
        for (int idx = 0; idx <= i; idx++) {
            p = p.next;
        }
        return p.item;
    }

    /**
     * Returns item i of the deque recursively.
     * @param i int i indicates index of the item.
     * @return item at index i
     */
    public T getRecursive(int i) {
        if (i >= size) {
            return null;
        } else {
            return helper(sentinel, i);
        }
    }

    /**
     * Helper function for getRecursive() that recursively sorts through list.
     * @param lst First StuffNode of the linked list that will be sorted.
     * @param i int index of the list
     * @return returns T item located at index i
     */
    private T helper(StuffNode lst, int i) {
        if (i == 0) {
            StuffNode loop = lst;
            return loop.next.item;
        } else {
            StuffNode loop = lst;
            return helper(loop.next, (i - 1));
        }
    }
}
