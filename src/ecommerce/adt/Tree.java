package ecommerce.adt;

import java.util.List;

/**
 * Generic tree interface defining basic operations for searchable trees.
 *
 * @param <T> type of element stored in the tree
 */
public interface Tree<T> {

    /**
     * Inserts an item into the tree.
     *
     * @param item element to insert
     */
    void insert(T item);

    /**
     * Searches for an item by key.
     *
     * @param key search key
     * @return matching element, or null if not found
     */
    T search(String key);

    /**
     * Deletes an item by key.
     *
     * @param key key of element to delete
     * @return true if deletion was successful
     */
    boolean delete(String key);

    /**
     * Returns the number of elements in the tree.
     *
     * @return tree size
     */
    int size();

    /**
     * Checks whether the tree is empty.
     *
     * @return true if tree contains no elements
     */
    boolean isEmpty();

    /**
     * Returns the height of the tree.
     *
     * @return tree height
     */
    int height();

    /**
     * Performs an inorder traversal.
     *
     * @return list of elements in sorted order
     */
    List<T> inorderTraversal();
}