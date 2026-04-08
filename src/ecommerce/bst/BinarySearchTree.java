package ecommerce.bst;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ecommerce.adt.Tree;
import ecommerce.model.Product;

/**
 * Implementation of a Binary Search Tree (BST) for storing Product objects.
 * <p>
 * The tree is ordered by product ASIN, ensuring:
 * - Left subtree contains smaller ASINs
 * - Right subtree contains larger ASINs
 * <p>
 * Average time complexity: O(log n)
 * Worst-case (sorted input): O(n)
 */
public class BinarySearchTree implements Tree<Product> {

    private BSTNode root;
    private int size;

    /** Constructs an empty BST. */
    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    /** @return root node of the tree */
    public BSTNode getRoot() {
        return root;
    }

    /** @return number of elements in the tree */
    @Override
    public int size() {
        return size;
    }

    /** @return true if tree is empty */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /** Compares two products by ASIN. */
    private int compareByAsin(Product p1, Product p2) {
        return p1.getAsin().compareTo(p2.getAsin());
    }

    /** Compares two ASIN strings. */
    private int compareByAsin(String asin1, String asin2) {
        return asin1.compareTo(asin2);
    }

    /**
     * Inserts a product into the BST.
     * If a product with the same ASIN exists, it is replaced.
     */
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

    /**
     * Searches for a product by ASIN.
     *
     * @param asin product identifier
     * @return matching product or null if not found
     */
    @Override
    public Product search(String asin) {
        BSTNode node = searchNode(asin);
        return node == null ? null : node.getProduct();
    }

    /** Internal search returning node reference. */
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

    /**
     * Deletes a product by ASIN.
     *
     * @return true if deletion was successful
     */
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

    /** Handles BST deletion cases. */
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

    /** Replaces subtree rooted at u with subtree rooted at v. */
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

    /** @return minimum product (smallest ASIN) */
    public Product minimum() {
        return root == null ? null : minimumNode(root).getProduct();
    }

    private BSTNode minimumNode(BSTNode node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    /** @return maximum product (largest ASIN) */
    public Product maximum() {
        return root == null ? null : maximumNode(root).getProduct();
    }

    private BSTNode maximumNode(BSTNode node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }

    /**
     * Computes tree height iteratively using level-order traversal.
     * Empty tree has height -1.
     */
    @Override
    public int height() {
        if (root == null) {
            return -1;
        }

        Queue<BSTNode> queue = new LinkedList<>();
        queue.add(root);

        int height = -1;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            height++;

            for (int i = 0; i < levelSize; i++) {
                BSTNode current = queue.poll();

                if (current.getLeft() != null) {
                    queue.add(current.getLeft());
                }

                if (current.getRight() != null) {
                    queue.add(current.getRight());
                }
            }
        }

        return height;
    }

    /**
     * Performs inorder traversal (sorted output).
     */
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

    /**
     * Prints first N elements in sorted order.
     */
    public void printInorderLimited(int limit) {
        List<Product> products = inorderTraversal();
        int count = Math.min(limit, products.size());

        for (int i = 0; i < count; i++) {
            System.out.println(products.get(i));
        }
    }
}