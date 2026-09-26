package com.insureflow.dsa;

import java.util.ArrayList;
import java.util.List;

/**
 * DSA Implementation: Hashing (Custom Hash Map)
 * Concept: Demonstrates separate chaining hash table with dynamic bucket array.
 * Time Complexity: Average O(1) for search, insert, and delete; Worst Case O(n) during hash collisions.
 * Use Case: Fast indexing of Policy numbers -> Policy objects and Customer IDs -> Customer objects.
 */
public class CustomHashMap<K, V> {

    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Entry<K, V>[] buckets;
    private int capacity;
    private int size;
    private static final float LOAD_FACTOR = 0.75f;

    @SuppressWarnings("unchecked")
    public CustomHashMap(int initialCapacity) {
        this.capacity = initialCapacity;
        this.buckets = new Entry[capacity];
        this.size = 0;
    }

    public CustomHashMap() {
        this(16);
    }

    private int getBucketIndex(K key) {
        if (key == null) return 0;
        int hashCode = key.hashCode();
        return Math.abs(hashCode) % capacity;
    }

    public void put(K key, V value) {
        int index = getBucketIndex(key);
        Entry<K, V> head = buckets[index];

        while (head != null) {
            if ((key == null && head.key == null) || (key != null && key.equals(head.key))) {
                head.value = value;
                return;
            }
            head = head.next;
        }

        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;

        if ((float) size / capacity >= LOAD_FACTOR) {
            resize();
        }
    }

    public V get(K key) {
        int index = getBucketIndex(key);
        Entry<K, V> head = buckets[index];

        while (head != null) {
            if ((key == null && head.key == null) || (key != null && key.equals(head.key))) {
                return head.value;
            }
            head = head.next;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = capacity * 2;
        Entry<K, V>[] oldBuckets = buckets;
        buckets = new Entry[newCapacity];
        capacity = newCapacity;
        size = 0;

        for (Entry<K, V> head : oldBuckets) {
            while (head != null) {
                put(head.key, head.value);
                head = head.next;
            }
        }
    }

    public List<K> keySet() {
        List<K> keys = new ArrayList<>();
        for (Entry<K, V> head : buckets) {
            while (head != null) {
                keys.add(head.key);
                head = head.next;
            }
        }
        return keys;
    }

    public int size() {
        return size;
    }
}
