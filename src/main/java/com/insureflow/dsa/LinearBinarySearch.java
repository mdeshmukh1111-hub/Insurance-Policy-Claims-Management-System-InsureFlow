package com.insureflow.dsa;

import java.util.List;

/**
 * DSA Implementation: Linear Search & Binary Search
 * Concepts: 
 * 1. Linear Search: Iterates element by element. O(N) time complexity.
 * 2. Binary Search: Divide-and-conquer on sorted list. O(log N) time complexity.
 * Use Case: Fast searching of policy numbers, claim amounts, or customer records.
 */
public class LinearBinarySearch {

    /**
     * Linear search algorithm for finding an element by key.
     * Time Complexity: O(N)
     */
    public static <T> int linearSearch(List<T> list, java.util.function.Function<T, String> keyExtractor, String target) {
        if (list == null || target == null) return -1;
        for (int i = 0; i < list.size(); i++) {
            String val = keyExtractor.apply(list.get(i));
            if (target.equalsIgnoreCase(val)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Binary search algorithm for finding an element in a sorted list.
     * Time Complexity: O(log N)
     * Requirement: List MUST be sorted by key in ascending order.
     */
    public static <T> int binarySearch(List<T> sortedList, java.util.function.Function<T, String> keyExtractor, String target) {
        if (sortedList == null || target == null) return -1;
        int low = 0;
        int high = sortedList.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            String midVal = keyExtractor.apply(sortedList.get(mid));
            int cmp = target.compareToIgnoreCase(midVal);

            if (cmp == 0) {
                return mid; // Target found
            } else if (cmp < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1; // Target not found
    }
}
