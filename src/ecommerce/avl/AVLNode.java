package ecommerce.avl;

import ecommerce.model.Product;

/**
 * Represents a node in an AVL Tree.
 * Each node stores a Product, child references, and its height.
 * The height is used to maintain balance during insertions and deletions.
 */
public class AVLNode {

    private Product product;
    private AVLNode left;
    private AVLNode right;
    private int height;

    /**
     * Creates a new AVL node with initial height 1.
     *
     * @param product product to store in the node
     */
    public AVLNode(Product product) {
        this.product = product;
        this.left = null;
        this.right = null;
        this.height = 1;
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
    public AVLNode getLeft() {
        return left;
    }

    /** Sets left child. */
    public void setLeft(AVLNode left) {
        this.left = left;
    }

    /** @return right child */
    public AVLNode getRight() {
        return right;
    }

    /** Sets right child. */
    public void setRight(AVLNode right) {
        this.right = right;
    }

    /**
     * Returns node height.
     * Height is used to compute balance factor.
     */
    public int getHeight() {
        return height;
    }

    /** Updates node height. */
    public void setHeight(int height) {
        this.height = height;
    }
}