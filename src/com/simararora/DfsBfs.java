package com.simararora;

import java.util.*;

public class DfsBfs {

    public static void main(String[] args) {
        Node _70 = new Node(70);
        Node _60 = new Node(60);
        Node _50 = new Node(50);
        _50.adjacentNodes.add(_70);
        _60.adjacentNodes.add(_70);
        Node _20 = new Node(20);
        Node _30 = new Node(30);
        Node _10 = new Node(10);
        _20.adjacentNodes.add(_50);
        _20.adjacentNodes.add(_60);
        _20.adjacentNodes.add(_30);
        _20.adjacentNodes.add(_10);
        _30.adjacentNodes.add(_60);
        _30.adjacentNodes.add(_30);
        Node _40 = new Node(40);
        _40.adjacentNodes.add(_20);
        _40.adjacentNodes.add(_10);

        new DfsBfs().execute(_40, 70);
    }

    private void execute(Node source, int destinationId) {
        System.out.println(dfs(source, destinationId));
        System.out.println(bfs(source, destinationId));
    }

    private boolean dfs(Node source, int destinationId) {
        return hasPathDfs(source, destinationId, new HashSet<>());
    }

    private boolean hasPathDfs(Node source, int destinationId, Set<Node> visitedNodes) {
        if (visitedNodes.contains(source)) {
            return false;
        }
        visitedNodes.add(source);
        if (source.id == destinationId) {
            return true;
        }
        for (Node child : source.adjacentNodes) {
            if (hasPathDfs(child, destinationId, visitedNodes))
                return true;
        }
        return false;
    }

    private boolean bfs(Node source, int destinationId) {
        Queue<Node> nextToVisitNodes = new LinkedList<>();
        nextToVisitNodes.add(source);
        Set<Node> visitedNodes = new HashSet<>();
        while (!nextToVisitNodes.isEmpty()) {
            Node node = nextToVisitNodes.remove();
            if (node.id == destinationId) {
                return true;
            }
            if (!visitedNodes.contains(node)) {
                visitedNodes.add(node);
                nextToVisitNodes.addAll(node.adjacentNodes);
            }
        }
        return false;
    }


    private static class Node {
        private int id;
        private List<Node> adjacentNodes = new LinkedList<>();

        Node(int id) {
            this.id = id;
        }
    }
}
