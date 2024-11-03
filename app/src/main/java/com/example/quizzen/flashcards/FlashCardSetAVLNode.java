package com.example.quizzen.flashcards;

/**
 * AVLTreeNode class
 * @author Jiazhe Lin
 * @version 1.5
 */
public class FlashCardSetAVLNode<K, V> {
    private K key;
    private V value;
    private FlashCardSetAVLNode<K, V> left;
    private FlashCardSetAVLNode<K, V> right;
    private int height;

    public FlashCardSetAVLNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.height = 1;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public FlashCardSetAVLNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(FlashCardSetAVLNode<K, V> left) {
        this.left = left;
    }

    public FlashCardSetAVLNode<K, V> getRight() {
        return right;
    }

    public void setRight(FlashCardSetAVLNode<K, V> right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}