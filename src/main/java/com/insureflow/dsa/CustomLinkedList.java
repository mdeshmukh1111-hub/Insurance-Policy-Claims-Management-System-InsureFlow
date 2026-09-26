package com.insureflow.dsa;

import java.util.ArrayList;
import java.util.List;

/**
 * DSA Implementation: Linked List (Singly Linked List)
 * Concept: Sequential node-based collection where each node links to the next node.
 * Time Complexity: Insert at head/tail O(1), Search O(N).
 * Use Case: Maintaining sequential audit history, policy event logs, and claim status timeline.
 */
public class CustomLinkedList<T> {

    public static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }

        public T getData() { return data; }
        public Node<T> getNext() { return next; }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public CustomLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public List<T> toList() {
        List<T> list = new ArrayList<>();
        Node<T> current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
