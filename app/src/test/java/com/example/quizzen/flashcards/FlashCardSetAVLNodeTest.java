package com.example.quizzen.flashcards;

import org.junit.Test;
import static org.junit.Assert.*;

public class FlashCardSetAVLNodeTest {

    @Test
    public void testGettersAndSetters() {
        // Create a node with key "apple" and value 1
        FlashCardSetAVLNode<String, Integer> node = new FlashCardSetAVLNode<>("apple", 1);

        // Test getKey() and getValue()
        assertEquals("apple", node.getKey());
        assertEquals(1, (int) node.getValue());

        // Test setKey() and setValue()
        node.setKey("banana");
        node.setValue(2);
        assertEquals("banana", node.getKey());
        assertEquals(2, (int) node.getValue());

        // Test getLeft() and setLeft()
        FlashCardSetAVLNode<String, Integer> leftNode = new FlashCardSetAVLNode<>("cherry", 3);
        node.setLeft(leftNode);
        assertEquals(leftNode, node.getLeft());

        // Test getRight() and setRight()
        FlashCardSetAVLNode<String, Integer> rightNode = new FlashCardSetAVLNode<>("date", 4);
        node.setRight(rightNode);
        assertEquals(rightNode, node.getRight());

        // Test getHeight() and setHeight()
        node.setHeight(2);
        assertEquals(2, node.getHeight());
    }
}
