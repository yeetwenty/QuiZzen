package com.example.quizzen.flashcards;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * An AVL tree for flashcards that will be used in searching flashcard sets.
 * This is a Self-balancing binary search tree.
 * @author Jiazhe Lin
 * @version 2.5
 */
public class FlashCardSetAVLTree<K extends Comparable<K>,V> {
    private FlashCardSetAVLNode<K, V> root;
    public void insert(K key, V value) {
        root = insertNode(root, key, value);
    }

    private FlashCardSetAVLNode<K, V> insertNode(FlashCardSetAVLNode<K, V> node, K key, V value) {
        if (node == null) {
            return new FlashCardSetAVLNode<>(key, value);
        }

        if (key.compareTo(node.getKey()) < 0) {
            node.setLeft(insertNode(node.getLeft(), key, value));
        } else if (key.compareTo(node.getKey()) > 0) {
            node.setRight(insertNode(node.getRight(), key, value));
        } else {
            // Key already exists, update the value
            node.setValue(value);
            return node;
        }

        // Update height
        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));

        // Perform balance check and rotations
        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1) {
            if (key.compareTo(node.getLeft().getKey()) < 0) {
                node = rightRotate(node);
            } else {
                node.setLeft(leftRotate(node.getLeft()));
                node = rightRotate(node);
            }
        } else if (balanceFactor < -1) {
            if (key.compareTo(node.getRight().getKey()) > 0) {
                node = leftRotate(node);
            } else {
                node.setRight(rightRotate(node.getRight()));
                node = leftRotate(node);
            }
        }

        return node;
    }

    public FlashCardSetAVLNode<K, V> rightRotate(FlashCardSetAVLNode<K, V> node) {
        FlashCardSetAVLNode<K, V> newRoot = node.getLeft();
        FlashCardSetAVLNode<K, V> temp = newRoot.getRight();

        newRoot.setRight(node);
        node.setLeft(temp);

        // Update heights
        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));
        newRoot.setHeight(1 + Math.max(getHeight(newRoot.getLeft()), getHeight(newRoot.getRight())));

        if (node == root) {
            root = newRoot;
        }

        return newRoot;
    }

    public FlashCardSetAVLNode<K, V> leftRotate(FlashCardSetAVLNode<K, V> node) {
        FlashCardSetAVLNode<K, V> newRoot = node.getRight();
        FlashCardSetAVLNode<K, V> temp = newRoot.getLeft();

        newRoot.setLeft(node);
        node.setRight(temp);

        // Update heights
        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));
        newRoot.setHeight(1 + Math.max(getHeight(newRoot.getLeft()), getHeight(newRoot.getRight())));

        if (node == root) {
            root = newRoot;
        }

        return newRoot;
    }



    public FlashCardSetAVLNode<K, V> getRoot() {
        return root;
    }

    public int getHeight(FlashCardSetAVLNode<K, V> node) {
        if (node == null) {
            return 0;
        }
        return node.getHeight();
    }

    public int getBalanceFactor(FlashCardSetAVLNode<K, V> node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.getLeft()) - getHeight(node.getRight());
    }


    public V get(K key) {
        FlashCardSetAVLNode<K, V> node = getNode(root, key);
        if (node != null) {
            return node.getValue();
        }
        return null;
    }

    private FlashCardSetAVLNode<K, V> getNode(FlashCardSetAVLNode<K, V> node, K key) {
        if (node == null || key.compareTo(node.getKey()) == 0) {
            return node;
        }

        if (key.compareTo(node.getKey()) < 0) {
            return getNode(node.getLeft(), key);
        } else {
            return getNode(node.getRight(), key);
        }
    }

    /**
     * This is an important method use to perform search activity in flashcards
     * @param query
     * @return Fuzzy matches in the AVLTree of flashcards
     */
    public FlashCardSetAVLNode<K, V> search(String query) {
        List<String> queryTokens = Tokenizer.tokenize(query);
        return search(root, queryTokens);
    }

    private FlashCardSetAVLNode<K, V> search(FlashCardSetAVLNode<K, V> node, List<String> queryTokens) {
        if (node == null) {
            return null;
        }

        if (isMatch(node.getKey().toString(), queryTokens)) {
            return node;
        }

        int compareResult = fuzzyCompare(queryTokens.get(0), node.getKey().toString());
        if (compareResult < 0) {
            return search(node.getLeft(), queryTokens);
        } else if (compareResult > 0) {
            return search(node.getRight(), queryTokens);
        } else {
            FlashCardSetAVLNode<K, V> result = search(node.getLeft(), queryTokens);
            if (result == null) {
                result = search(node.getRight(), queryTokens);
            }
            return result;
        }
    }

    private boolean isMatch(String s, List<String> queryTokens) {
        s = s.toLowerCase(); // Convert the key to lowercase
        for (String token : queryTokens) {
            if (!s.contains(token.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    /**
    * This is fuzzy compare method that measures the closeness of one string to the other
     */

    private int fuzzyCompare(String s1, String s2) {
        String[] tokens1 = s1.split("\\s+");
        String[] tokens2 = s2.split("\\s+");
        int score1 = 0;
        int score2 = 0;
        for (String token : tokens1) {
            if (s2.contains(token)) {
                score1++;
            }
        }
        for (String token : tokens2) {
            if (s1.contains(token)) {
                score2++;
            }
        }
        return Integer.compare(score2, score1);
    }

}
