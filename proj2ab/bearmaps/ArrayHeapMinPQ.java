package bearmaps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private class Node {
        T item;
        double priority;

        Node(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }
    }

    private ArrayList<Node> minheap;
    private int size;
    private HashMap<T, Integer> items;

    public ArrayHeapMinPQ() {
        this.minheap = new ArrayList<>();
        minheap.add(new Node(null, Double.NEGATIVE_INFINITY));
        this.size = 0;
        items = new HashMap<>();
    }

    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentException if item is already present.
     * You may assume that item is never null. */
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        int lastIndex = size + 1;
        minheap.add(new Node(item, priority));
        items.put(item, lastIndex);
        swim(lastIndex);
        size += 1;
    }

    private void swim(int k) {
        if (k <= 1) {
            return;
        }
        if (minheap.get(parent(k)).priority > minheap.get(k).priority) {
            items.replace(minheap.get(parent(k)).item, k);
            items.replace(minheap.get(k).item, parent(k));
            Collections.swap(minheap, k, parent(k));
            swim(parent(k));
        }
    }

    private int parent(int k) {
        return k / 2;
    }

    /* Returns true if the PQ contains the given item. */
    public boolean contains(T item) {
        return items.containsKey(item);
    }
    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T getSmallest() {
        if (size <= 0) {
            throw new NoSuchElementException();
        }
        return minheap.get(1).item;
    }
    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T removeSmallest() {
        if (size <= 0) {
            throw new NoSuchElementException();
        }
        Collections.swap(minheap, 1, size);
        items.replace(minheap.get(1).item, 1);
        Node first = minheap.remove(size);
        items.remove(first.item);
        size--;
        sink(1);
        return first.item;
    }

    private void sink(int k) {
        if (k * 2 > size) {
            return;
        }
        if (k * 2 + 1 > size
                || minheap.get(2 * k).priority < minheap.get(2 * k + 1).priority) {
            //if the left child is less than the right child, or right doesn't exist
            if (minheap.get(2 * k).priority < minheap.get(k).priority) {
                items.replace(minheap.get(2 * k).item, k);
                items.replace(minheap.get(k).item, 2 * k);
                Collections.swap(minheap, k, 2 * k);
                sink(2 * k);
            }
        } else {
            // if not, sink to the right
            if (minheap.get(2 * k + 1).priority < minheap.get(k).priority) {
                items.replace(minheap.get(2 * k + 1).item, k);
                items.replace(minheap.get(k).item, 2 * k + 1);
                Collections.swap(minheap, k, 2 * k + 1);
                sink(2 * k + 1);
            }
        }
    }

    /* Returns the number of items in the PQ. */
    public int size() {
        return size;
    }
    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int index = items.get(item);
        minheap.get(index).priority = priority;
        swim(index);
        sink(index);
    }
}
