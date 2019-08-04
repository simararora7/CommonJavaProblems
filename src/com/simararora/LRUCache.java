package com.simararora;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {

    public static void main(String[] args) {
        LRUCache<String, String> cache = new LRUCache<>(3);
        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");
        cache.put("4", "4");
        cache.get("2");
        cache.get("5");
        cache.put("4", "40");
    }

    private Map<K, Node> lookupMap;
    private Node headerNode;
    private Node tailNode;
    private int maxSize;
    private int currentSize;

    private LRUCache(int maxSize) {
        this.maxSize = maxSize;
        this.currentSize = 0;

        headerNode = new Node();
        tailNode = new Node();
        headerNode.isHead = true;
        tailNode.isTail = true;
        headerNode.previous = null;
        headerNode.next = tailNode;
        tailNode.previous = headerNode;
        tailNode.next = null;

        lookupMap = new HashMap<>();
    }

    void put(K key, V value) {
        if (lookupMap.containsKey(key)) {
            log("Entry Already Present In Cache");
            Node node = lookupMap.get(key);
            node.value = value;
            moveNodeTpFront(node);
        } else {
            log("New Entry Created In Cache");
            Node newNode = new Node();
            newNode.key = key;
            newNode.value = value;
            addNodeToFront(newNode);
            lookupMap.put(key, newNode);
            currentSize++;
            if (currentSize == maxSize + 1) {
                log("Cache Overflow");
                removeFromTail();
            }
        }
        logCache();
    }

    V get(K key) {
        try {
            if (lookupMap.containsKey(key)) {
                log("Cache Hit");
                Node node = lookupMap.get(key);
                moveNodeTpFront(node);
                return node.value;
            }
            log("Cache Miss");
            return null;
        } finally {
            logCache();
        }
    }

    private void moveNodeTpFront(Node node) {
        log("Moving Cache Entry To Front");
        removeNode(node);
        addNodeToFront(node);
    }

    private void removeFromTail() {
        log("Removing Least Used Node");
        Node nodeToRemove = tailNode.previous;
        removeNode(nodeToRemove);
        lookupMap.remove(nodeToRemove.key);
    }

    private void addNodeToFront(Node node) {
        node.previous = headerNode;
        node.next = headerNode.next;
        headerNode.next.previous = node;
        headerNode.next = node;
    }

    private void removeNode(Node node) {
        node.previous.next = node.next;
        node.next.previous = node.previous;
    }

    private void log(String message) {
        System.out.println(message);
    }

    private void logCache() {
        Node node = headerNode;
        while (node.next != null) {
            if (!node.isHead && !node.isTail)
                System.out.print("(" + node.key + ", " + node.value + ")  ");
            node = node.next;
        }
        System.out.println();
    }

    private class Node {
        private K key;
        private V value;
        private Node next;
        private Node previous;
        private boolean isHead;
        private boolean isTail;
    }
}
