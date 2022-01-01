/**
 * @author Helen Rhee on 9/16/2020
 * @param <T> Interface for Deque that takes in generic type T.
 */
public interface Deque<T> {
    /** Adds @param item of type T to front of deque.*/
    void addFirst(T item);

    /** Adds @param item of type T to end of deque. */
    void addLast(T item);

    /** Checks if deque is empty and @return boolean type. */
    default boolean isEmpty() {
        return size() == 0;
    }

    /** Returns @return int length of the deque. */
    int size();

    /** Prints the deque. */
    void printDeque();

    /** Removes the first item of the deque.
     * @return T returns the item. */
    T removeFirst();

    /** Removes the last item of the deque.
     * @return returns it. */
    T removeLast();

    /** Gets the Ith item of the deque and returns it.
     * @param index int
     * @return T item
     */
    T get(int index);

}
