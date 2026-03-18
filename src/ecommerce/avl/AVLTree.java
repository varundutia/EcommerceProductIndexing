package ecommerce.avl;

import java.util.ArrayList;
import java.util.List;

import ecommerce.adt.Tree;
import ecommerce.model.Product;

public class AVLTree implements Tree<Product> {
    private AVLNode root;
    private int size;

    public AVLTree() {
        this.root = null;
        this.size = 0;
    }

    public AVLNode getRoot() {
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

    private int height(AVLNode node) {
        return node == null ? 0 : node.getHeight();
    }

    private int getBalance(AVLNode node) {
        return node == null ? 0 : height(node.getLeft()) - height(node.getRight());
    }

    private void updateHeight(AVLNode node) {
        if (node != null) {
            node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));
        }
    }

    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.getLeft();
        AVLNode t2 = x.getRight();

        x.setRight(y);
        y.setLeft(t2);

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.getRight();
        AVLNode t2 = y.getLeft();

        y.setLeft(x);
        x.setRight(t2);

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    @Override
    public void insert(Product product) {
        int oldSize = size;
        root = insert(root, product);
        if (size == oldSize) {
            return;
        }
    }

    private AVLNode insert(AVLNode node, Product product) {
        if (node == null) {
            size++;
            return new AVLNode(product);
        }

        int cmp = compareByAsin(product, node.getProduct());

        if (cmp < 0) {
            node.setLeft(insert(node.getLeft(), product));
        } else if (cmp > 0) {
            node.setRight(insert(node.getRight(), product));
        } else {
            node.setProduct(product);
            return node;
        }

        updateHeight(node);
        int balance = getBalance(node);

        if (balance > 1 && compareByAsin(product, node.getLeft().getProduct()) < 0) {
            return rightRotate(node);
        }

        if (balance < -1 && compareByAsin(product, node.getRight().getProduct()) > 0) {
            return leftRotate(node);
        }

        if (balance > 1 && compareByAsin(product, node.getLeft().getProduct()) > 0) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }

        if (balance < -1 && compareByAsin(product, node.getRight().getProduct()) < 0) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }

        return node;
    }

    @Override
    public Product search(String asin) {
        AVLNode node = searchNode(root, asin);
        return node == null ? null : node.getProduct();
    }

    private AVLNode searchNode(AVLNode node, String asin) {
        AVLNode current = node;

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
        if (search(asin) == null) {
            return false;
        }
        root = delete(root, asin);
        size--;
        return true;
    }

    private AVLNode delete(AVLNode node, String asin) {
        if (node == null) {
            return null;
        }

        int cmp = compareByAsin(asin, node.getProduct().getAsin());

        if (cmp < 0) {
            node.setLeft(delete(node.getLeft(), asin));
        } else if (cmp > 0) {
            node.setRight(delete(node.getRight(), asin));
        } else {
            if (node.getLeft() == null || node.getRight() == null) {
                AVLNode temp = (node.getLeft() != null) ? node.getLeft() : node.getRight();

                if (temp == null) {
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                AVLNode successor = minimumNode(node.getRight());
                node.setProduct(successor.getProduct());
                node.setRight(delete(node.getRight(), successor.getProduct().getAsin()));
            }
        }

        if (node == null) {
            return null;
        }

        updateHeight(node);
        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.getLeft()) >= 0) {
            return rightRotate(node);
        }

        if (balance > 1 && getBalance(node.getLeft()) < 0) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }

        if (balance < -1 && getBalance(node.getRight()) <= 0) {
            return leftRotate(node);
        }

        if (balance < -1 && getBalance(node.getRight()) > 0) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }

        return node;
    }

    public Product minimum() {
        if (root == null) {
            return null;
        }
        return minimumNode(root).getProduct();
    }

    private AVLNode minimumNode(AVLNode node) {
        AVLNode current = node;
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

    private AVLNode maximumNode(AVLNode node) {
        AVLNode current = node;
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current;
    }

    @Override
    public int height() {
        return root == null ? -1 : root.getHeight() - 1;
    }

    @Override
    public List<Product> inorderTraversal() {
        List<Product> products = new ArrayList<>();
        inorderTraversal(root, products);
        return products;
    }

    private void inorderTraversal(AVLNode node, List<Product> products) {
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
