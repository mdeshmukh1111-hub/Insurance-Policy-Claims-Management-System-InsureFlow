package com.insureflow.dsa;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * DSA Implementation: Queue (FIFO - First In First Out)
 * Concept: Array/LinkedList based queue supporting enqueue, dequeue, and peek operations.
 * Time Complexity: Enqueue O(1), Dequeue O(1), Peek O(1).
 * Use Case: FIFO queue for processing pending insurance claims in order of submission.
 */
public class CustomQueue<T> {

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> front;
    private Node<T> rear;
    private int size;

    public CustomQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (rear == null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty!");
        }
        T data = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return data;
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty!");
        }
        return front.data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public List<T> toList() {
        List<T> result = new ArrayList<>();
        Node<T> current = front;
        while (current != null) {
            result.add(current.data);
            current = current.next;
        }
        return result;
    }
}
