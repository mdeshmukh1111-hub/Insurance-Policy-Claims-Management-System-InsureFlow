package com.insureflow.dsa;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * DSA Implementation: Priority Queue / Max Heap
 * Concept: Complete Binary Tree maintained in array form satisfying Heap Property.
 * Time Complexity: Insert O(log N), Extract-Max O(log N), Peek O(1).
 * Use Case: Prioritizing insurance claims based on severity, claim amount, risk score, and waiting time.
 */
public class CustomPriorityQueue<T> {

    private final List<T> heap;
    private final Comparator<T> comparator;

    public CustomPriorityQueue(Comparator<T> comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }

    public void insert(T item) {
        heap.add(item);
        siftUp(heap.size() - 1);
    }

    public T extractMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority Queue is empty!");
        }
        T max = heap.get(0);
        T last = heap.remove(heap.size() - 1);
        if (!isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }
        return max;
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority Queue is empty!");
        }
        return heap.get(0);
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (comparator.compare(heap.get(index), heap.get(parentIndex)) > 0) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    private void siftDown(int index) {
        int maxIndex = index;
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;

        if (leftChild < heap.size() && comparator.compare(heap.get(leftChild), heap.get(maxIndex)) > 0) {
            maxIndex = leftChild;
        }

        if (rightChild < heap.size() && comparator.compare(heap.get(rightChild), heap.get(maxIndex)) > 0) {
            maxIndex = rightChild;
        }

        if (index != maxIndex) {
            swap(index, maxIndex);
            siftDown(maxIndex);
        }
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    public List<T> toSortedList() {
        List<T> copy = new ArrayList<>(heap);
        copy.sort(comparator.reversed());
        return copy;
    }
}
