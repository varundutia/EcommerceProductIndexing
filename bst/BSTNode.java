package ecommerce.bst;

import ecommerce.model.Product;

public class BSTNode {
    private Product product;
    private BSTNode left;
    private BSTNode right;
    private BSTNode parent;

    public BSTNode(Product product) {
        this.product = product;
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BSTNode getLeft() {
        return left;
    }

    public void setLeft(BSTNode left) {
        this.left = left;
    }

    public BSTNode getRight() {
        return right;
    }

    public void setRight(BSTNode right) {
        this.right = right;
    }

    public BSTNode getParent() {
        return parent;
    }

    public void setParent(BSTNode parent) {
        this.parent = parent;
    }
}
