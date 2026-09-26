package com.insureflow.dsa;

import java.util.*;

/**
 * DSA Implementation: Graph Data Structure & BFS/DFS Traversal
 * Concept: Adjacency list representation of connected insurance entities (Customer -> Policy -> Claim -> Settlement).
 * Time Complexity: BFS/DFS O(V + E) where V is vertices (entities) and E is relationships.
 * Use Case: Analyzing relationship networks, fraud detection, dependency tracing across Customers, Policies, and Claims.
 */
public class InsuranceGraph {

    public static class Node {
        private String id;
        private String label;
        private String type; // CUSTOMER, POLICY, CLAIM, SETTLEMENT

        public Node(String id, String label, String type) {
            this.id = id;
            this.label = label;
            this.type = type;
        }

        public String getId() { return id; }
        public String getLabel() { return label; }
        public String getType() { return type; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(id, node.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    private final Map<String, Node> nodeMap = new HashMap<>();
    private final Map<String, List<String>> adjList = new HashMap<>();

    public void addNode(String id, String label, String type) {
        Node node = new Node(id, label, type);
        nodeMap.put(id, node);
        adjList.putIfAbsent(id, new ArrayList<>());
    }

    public void addEdge(String fromId, String toId) {
        if (adjList.containsKey(fromId) && adjList.containsKey(toId)) {
            if (!adjList.get(fromId).contains(toId)) {
                adjList.get(fromId).add(toId);
            }
        }
    }

    /**
     * Breadth-First Search (BFS) Traversal
     * Explores entity relationships layer-by-layer starting from a root node.
     */
    public List<Node> bfs(String startId) {
        List<Node> result = new ArrayList<>();
        if (!nodeMap.containsKey(startId)) return result;

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(startId);
        visited.add(startId);

        while (!queue.isEmpty()) {
            String currentId = queue.poll();
            result.add(nodeMap.get(currentId));

            for (String neighborId : adjList.getOrDefault(currentId, Collections.emptyList())) {
                if (!visited.contains(neighborId)) {
                    visited.add(neighborId);
                    queue.add(neighborId);
                }
            }
        }
        return result;
    }

    /**
     * Depth-First Search (DFS) Traversal
     * Explores deeply into claim chains before backtracking.
     */
    public List<Node> dfs(String startId) {
        List<Node> result = new ArrayList<>();
        if (!nodeMap.containsKey(startId)) return result;

        Set<String> visited = new HashSet<>();
        dfsRecursive(startId, visited, result);
        return result;
    }

    private void dfsRecursive(String currentId, Set<String> visited, List<Node> result) {
        visited.add(currentId);
        result.add(nodeMap.get(currentId));

        for (String neighborId : adjList.getOrDefault(currentId, Collections.emptyList())) {
            if (!visited.contains(neighborId)) {
                dfsRecursive(neighborId, visited, result);
            }
        }
    }

    public Map<String, Node> getNodeMap() {
        return nodeMap;
    }

    public Map<String, List<String>> getAdjList() {
        return adjList;
    }
}
