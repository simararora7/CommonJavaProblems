package com.simararora;

import java.util.*;

public class TopologicalSort {

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
        new TopologicalSort().execute(_40);
    }

    private void execute(Node node) {
        Set<Node> visitedNodes = new HashSet<>();
        Stack<Node> sortedNodes = new Stack<>();
        topologicalSort(node, visitedNodes, sortedNodes);

        while (!sortedNodes.isEmpty()) {
            System.out.println(sortedNodes.pop().id);
        }
    }

    private void topologicalSort(Node node, Set<Node> visitedNodes, Stack<Node> sortedNodes) {
        for (Node adjacent: node.adjacentNodes){
            if (!visitedNodes.contains(adjacent)){
                visitedNodes.add(adjacent);
                topologicalSort(adjacent, visitedNodes, sortedNodes);
            }
        }
        sortedNodes.push(node);
    }

    private static class Node {
        private int id;
        private List<Node> adjacentNodes = new LinkedList<>();

        Node(int id) {
            this.id = id;
        }
    }
}
