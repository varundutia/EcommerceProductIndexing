package ecommerce.avl;

import ecommerce.model.Product;

public class AVLNode {
    private Product product;
    private AVLNode left;
    private AVLNode right;
    private int height;

    public AVLNode(Product product) {
        this.product = product;
        this.left = null;
        this.right = null;
        this.height = 1;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public AVLNode getLeft() {
        return left;
    }

    public void setLeft(AVLNode left) {
        this.left = left;
    }

    public AVLNode getRight() {
        return right;
    }

    public void setRight(AVLNode right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
