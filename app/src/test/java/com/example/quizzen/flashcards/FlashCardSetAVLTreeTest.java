package com.example.quizzen.flashcards;

import static org.junit.Assert.*;

import org.junit.Test;
public class FlashCardSetAVLTreeTest {

    @Test
    public void testInsert() {
        FlashCardSetAVLTree<String, Integer> tree = new FlashCardSetAVLTree<>();
        tree.insert("apple", 1);
        assertEquals(1, (int) tree.getRoot().getValue());
    }

    @Test
    public void testGet() {
        FlashCardSetAVLTree<String, Integer> tree = new FlashCardSetAVLTree<>();
        tree.insert("apple", 1);
        assertEquals(1, (int) tree.get("apple"));
        assertNull(tree.get("banana"));
    }

    @Test
    public void testSearch() {
        FlashCardSetAVLTree<String, Integer> tree = new FlashCardSetAVLTree<>();
        tree.insert("apple", 1);
        tree.insert("banana", 2);
        tree.insert("cherry", 3);
        FlashCardSetAVLNode<String, Integer> result = tree.search("anana");
        assertEquals("banana", result.getKey());
        assertEquals(2, (int) result.getValue());
    }

    @Test
    public void testInsertDuplicate() {
        FlashCardSetAVLTree<String, Integer> tree = new FlashCardSetAVLTree<>();
        tree.insert("apple", 1);
        tree.insert("apple", 2);
        assertEquals(2, (int) tree.getRoot().getValue());
    }

    @Test
    public void testGetHeight() {
        FlashCardSetAVLTree<String, Integer> tree = new FlashCardSetAVLTree<>();
        assertEquals(0, tree.getHeight(null));
        tree.insert("apple", 1);
        assertEquals(1, tree.getHeight(tree.getRoot()));
    }

    @Test
    public void testGetBalanceFactor() {
        FlashCardSetAVLTree<String, Integer> tree = new FlashCardSetAVLTree<>();
        assertEquals(0, tree.getBalanceFactor(null));
        tree.insert("apple", 1);
        assertEquals(0, tree.getBalanceFactor(tree.getRoot()));
        tree.insert("banana", 2);
        assertEquals(-1, tree.getBalanceFactor(tree.getRoot()));
    }

    @Test
    public void testRightRotate() {
        FlashCardSetAVLTree<String, Integer> tree = new FlashCardSetAVLTree<>();
        tree.insert("apple", 1);
        tree.insert("banana", 2);
        tree.insert("cherry", 3);
        tree.insert("date", 4);
        tree.insert("elderberry", 5);
        FlashCardSetAVLNode<String, Integer> root = tree.getRoot();
        tree.rightRotate(root);
        assertEquals("banana", root.getKey());
        assertEquals("apple", root.getLeft().getKey());
        assertEquals("cherry", root.getRight().getKey());
    }

    @Test
    public void testLeftRotate() {
        FlashCardSetAVLTree<String, Integer> tree = new FlashCardSetAVLTree<>();
        tree.insert("elderberry", 5);
        tree.insert("date", 4);
        tree.insert("cherry", 3);
        tree.insert("banana", 2);
        tree.insert("apple", 1);
        FlashCardSetAVLNode<String, Integer> root = tree.getRoot();
        tree.leftRotate(root);
        assertEquals("cherry", root.getKey());
        assertEquals("banana", root.getLeft().getKey());
        assertEquals("elderberry", root.getRight().getKey());
    }

    @Test
    public void testAdvanced() {
        // Test inserting a single node
        FlashCardSetAVLTree<String, Integer> tree = new FlashCardSetAVLTree<>();
        tree.insert("apple", 1);
        assertEquals(1, (int) tree.getRoot().getValue());

        // Test inserting multiple nodes
        tree.insert("banana", 2);
        tree.insert("cherry", 3);
        tree.insert("date", 4);
        tree.insert("elderberry", 5);
        assertEquals(1, (int) tree.getRoot().getValue());
        assertEquals(2, (int) tree.getRoot().getRight().getValue());
        assertEquals(3, (int) tree.getRoot().getRight().getRight().getValue());
        assertEquals(4, (int) tree.getRoot().getRight().getRight().getRight().getValue());
        assertEquals(5, (int) tree.getRoot().getRight().getRight().getLeft().getValue());

        // Test inserting a node with an existing key
        tree.insert("banana", 6);
        assertEquals(6, (int) tree.getRoot().getRight().getValue());
    }
}
