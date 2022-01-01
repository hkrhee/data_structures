/**
 * @author Helen Rhee on 9/11/2020
 */
public class ArrayDeque<T> {

    /**
     * Creates an array of items.
     */
    private T[] items;

    /**
     * Keeps track of the number of items in the deque.
     */
    private int size;

    /**
     * The index of where the next first item should be put.
     */
    private int nextFirst;

    /**
     * The index of where the next last item should be put.
     */
    private int nextLast;

    /**
     * Create an empty ArrayDeque.
     */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 0;
        size = 0;
    }

    /**
     * Adds T x to the front of the deque.
     * @param x T
     */
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        while (items[nextFirst] != null && size != items.length) {
            if (nextFirst == 0) {
                nextFirst = items.length;
            }
            nextFirst -= 1;
        }
        items[nextFirst] = x;
        size += 1;
    }

    /**
     * @param x Adds T x to the end of the deque.
     */
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        while (items[nextLast] != null && size != items.length) {
            if (nextLast == items.length - 1) {
                nextLast = -1;
            }
            nextLast += 1;
        }
        items[nextLast] = x;
        size += 1;
    }

    /**
     * Removes the item at the front of the deque.
     *
     * @return T i originally at the front of the deque.
     */
    public T removeFirst() {
        if (size <= 0) {
            return null;
        }
        if (size <= items.length * 0.25) {
            resize(items.length / 2);
        }
        T first = items[nextFirst];
        items[nextFirst] = null;
        nextFirst = (nextFirst + 1) % items.length;
        size -= 1;
        if (size == 0) {
            nextFirst = nextLast;
        }
        return first;
    }

    /**
     * Removes the last item of the deque.
     *
     * @return T i originally at the back of the deque.
     */
    public T removeLast() {
        if (size <= 0) {
            return null;
        }
        if (size <= items.length * 0.25) {
            resize(items.length / 2);
        }
        T last = items[nextLast];
        items[nextLast] = null;
        nextLast = (nextLast - 1 + items.length) % items.length;
        size -= 1;
        if (size == 0) {
            nextLast = nextFirst;
        }
        return last;
    }

    /**
     * Resizes the length of the array based on how many items are in the array.
     *
     * @param capacity Creates a new deque of length int capacity.
     */
    private void resize(int capacity) {
        T[] newlst = (T[]) new Object[capacity];
        if (nextFirst <= nextLast) {
            System.arraycopy(items, nextFirst, newlst, 0, size);
        } else {
            int numcopyf = items.length - nextFirst;
            int numcopyl = nextLast + 1;
            System.arraycopy(items, nextFirst, newlst, 0, numcopyf);
            System.arraycopy(items, 0, newlst, numcopyf, numcopyl);
        }
        items = newlst;
        nextFirst = 0;
        nextLast = size - 1;
    }

    /**
     * Returns the item of the array at index idx.
     *
     * @param idx index of the item wanted
     * @return item of the array
     */
    public T get(int idx) {
        if (idx >= items.length) {
            return null;
        }
        int actualIdx = nextFirst;
        for (int i = 0; i < idx; i++) {
            actualIdx = (actualIdx + 1) % items.length;
        }
        return items[actualIdx];
    }

    /**
     * Returns the length of the array.
     *
     * @return int: length of the array
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the deque is empty.
     *
     * @return boolean true if the deque is empty, false if not.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Prints the items from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println(" ");
    }
}
