package ecommerce.bst;

import java.util.ArrayList;
import java.util.List;

import ecommerce.adt.Tree;
import ecommerce.model.Product;

public class BinarySearchTree implements Tree<Product> {
    private BSTNode root;
    private int size;

    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    public BSTNode getRoot() {
        return root;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    private int compareByAsin(Product p1, Product p2) {
        return p1.getAsin().compareTo(p2.getAsin());
    }

    private int compareByAsin(String asin1, String asin2) {
        return asin1.compareTo(asin2);
    }

    @Override
    public void insert(Product product) {
        BSTNode newNode = new BSTNode(product);

        if (root == null) {
            root = newNode;
            size++;
            return;
        }

        BSTNode current = root;
        BSTNode parent = null;

        while (current != null) {
            parent = current;
            int cmp = compareByAsin(product, current.getProduct());

            if (cmp < 0) {
                current = current.getLeft();
            } else if (cmp > 0) {
                current = current.getRight();
            } else {
                current.setProduct(product);
                return;
            }
        }

        newNode.setParent(parent);

        if (compareByAsin(product, parent.getProduct()) < 0) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }

        size++;
    }

    @Override
    public Product search(String asin) {
        BSTNode node = searchNode(asin);
        return node == null ? null : node.getProduct();
    }

    private BSTNode searchNode(String asin) {
        BSTNode current = root;

        while (current != null) {
            int cmp = compareByAsin(asin, current.getProduct().getAsin());

            if (cmp == 0) {
                return current;
            } else if (cmp < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }

        return null;
    }

    @Override
    public boolean delete(String asin) {
        BSTNode nodeToDelete = searchNode(asin);

        if (nodeToDelete == null) {
            return false;
        }

        deleteNode(nodeToDelete);
        size--;
        return true;
    }

    private void deleteNode(BSTNode node) {
        if (node.getLeft() == null && node.getRight() == null) {
            transplant(node, null);
        } else if (node.getLeft() == null) {
            transplant(node, node.getRight());
        } else if (node.getRight() == null) {
            transplant(node, node.getLeft());
        } else {
            BSTNode successor = minimumNode(node.getRight());

            if (successor.getParent() != node) {
                transplant(successor, successor.getRight());
                successor.setRight(node.getRight());
                if (successor.getRight() != null) {
                    successor.getRight().setParent(successor);
                }
            }

            transplant(node, successor);
            successor.setLeft(node.getLeft());
            if (successor.getLeft() != null) {
                successor.getLeft().setParent(successor);
            }
        }
    }

    private void transplant(BSTNode u, BSTNode v) {
        if (u.getParent() == null) {
            root = v;
        } else if (u == u.getParent().getLeft()) {
            u.getParent().setLeft(v);
        } else {
            u.getParent().setRight(v);
        }

        if (v != null) {
            v.setParent(u.getParent());
        }
    }

    public Product minimum() {
        if (root == null) {
            return null;
        }
        return minimumNode(root).getProduct();
    }

    private BSTNode minimumNode(BSTNode node) {
        BSTNode current = node;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    public Product maximum() {
        if (root == null) {
            return null;
        }
        return maximumNode(root).getProduct();
    }

    private BSTNode maximumNode(BSTNode node) {
        BSTNode current = node;
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current;
    }

    @Override
    public int height() {
        return height(root);
    }

    private int height(BSTNode node) {
        if (node == null) {
            return -1;
        }

        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());

        return 1 + Math.max(leftHeight, rightHeight);
    }

    @Override
    public List<Product> inorderTraversal() {
        List<Product> products = new ArrayList<>();
        inorderTraversal(root, products);
        return products;
    }

    private void inorderTraversal(BSTNode node, List<Product> products) {
        if (node != null) {
            inorderTraversal(node.getLeft(), products);
            products.add(node.getProduct());
            inorderTraversal(node.getRight(), products);
        }
    }

    public void printInorderLimited(int limit) {
        List<Product> products = inorderTraversal();
        int count = Math.min(limit, products.size());

        for (int i = 0; i < count; i++) {
            System.out.println(products.get(i));
        }
    }
}
