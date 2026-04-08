package ecommerce.bst;

import ecommerce.model.Product;

/**
 * Represents a node in a Binary Search Tree.
 * Each node stores a Product and references to its children and parent.
 */
public class BSTNode {

    private Product product;
    private BSTNode left;
    private BSTNode right;
    private BSTNode parent;

    /**
     * Creates a new BST node with the given product.
     *
     * @param product product to store in the node
     */
    public BSTNode(Product product) {
        this.product = product;
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    /** @return stored product */
    public Product getProduct() {
        return product;
    }

    /** Sets the product value. */
    public void setProduct(Product product) {
        this.product = product;
    }

    /** @return left child */
    public BSTNode getLeft() {
        return left;
    }

    /** Sets left child. */
    public void setLeft(BSTNode left) {
        this.left = left;
    }

    /** @return right child */
    public BSTNode getRight() {
        return right;
    }

    /** Sets right child. */
    public void setRight(BSTNode right) {
        this.right = right;
    }

    /** @return parent node */
    public BSTNode getParent() {
        return parent;
    }

    /** Sets parent node. */
    public void setParent(BSTNode parent) {
        this.parent = parent;
    }
}