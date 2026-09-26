package com.insureflow.dsa;

import com.insureflow.entity.Policy;
import java.util.ArrayList;
import java.util.List;

/**
 * DSA Implementation: Binary Search Tree (BST)
 * Concept: Tree data structure where left subtree node key < root key <= right subtree node key.
 * Time Complexity: Average O(log N) search/insert, Worst Case O(N) if unbalanced.
 * Use Case: Fast searching of policies by policy number key and traversal in-order (sorted order).
 */
public class PolicyBST {

    public static class BSTNode {
        private Policy policy;
        private BSTNode left;
        private BSTNode right;

        public BSTNode(Policy policy) {
            this.policy = policy;
            this.left = null;
            this.right = null;
        }

        public Policy getPolicy() { return policy; }
        public BSTNode getLeft() { return left; }
        public BSTNode getRight() { return right; }
    }

    private BSTNode root;

    public PolicyBST() {
        this.root = null;
    }

    public void insert(Policy policy) {
        if (policy == null || policy.getPolicyNumber() == null) return;
        root = insertRec(root, policy);
    }

    private BSTNode insertRec(BSTNode root, Policy policy) {
        if (root == null) {
            return new BSTNode(policy);
        }

        int cmp = policy.getPolicyNumber().compareToIgnoreCase(root.policy.getPolicyNumber());
        if (cmp < 0) {
            root.left = insertRec(root.left, policy);
        } else if (cmp > 0) {
            root.right = insertRec(root.right, policy);
        } else {
            root.policy = policy; // update duplicate key
        }
        return root;
    }

    public Policy search(String policyNumber) {
        if (policyNumber == null) return null;
        return searchRec(root, policyNumber);
    }

    private Policy searchRec(BSTNode root, String policyNumber) {
        if (root == null) return null;

        int cmp = policyNumber.compareToIgnoreCase(root.policy.getPolicyNumber());
        if (cmp == 0) {
            return root.policy;
        } else if (cmp < 0) {
            return searchRec(root.left, policyNumber);
        } else {
            return searchRec(root.right, policyNumber);
        }
    }

    public List<Policy> inOrderTraversal() {
        List<Policy> result = new ArrayList<>();
        inOrderRec(root, result);
        return result;
    }

    private void inOrderRec(BSTNode node, List<Policy> result) {
        if (node != null) {
            inOrderRec(node.left, result);
            result.add(node.policy);
            inOrderRec(node.right, result);
        }
    }

    public BSTNode getRoot() {
        return root;
    }
}
