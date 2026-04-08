package ecommerce.core;

import java.util.ArrayList;
import java.util.List;

import ecommerce.avl.AVLTree;
import ecommerce.bst.BinarySearchTree;
import ecommerce.model.Product;

/**
 * Executes and compares BST and AVL Tree experiments.
 */
public class ComparisonRunner {

    /**
     * Runs a full experiment on a Binary Search Tree.
     *
     * @param products  dataset of products
     * @param inputType input type (Random or Sorted)
     * @return experiment result for BST
     */
    public static ExperimentResult runBSTExperiment(List<Product> products, String inputType) {
        BinarySearchTree bst = new BinarySearchTree();

        long insertStart = System.nanoTime();
        for (Product product : products) {
            bst.insert(product);
        }
        long insertEnd = System.nanoTime();

        long searchStart = System.nanoTime();
        runSearchesOnBST(bst, products);
        long searchEnd = System.nanoTime();

        int heightBeforeDelete = bst.height();

        List<String> deleteKeys = chooseDeleteKeys(products, 1000);

        long deleteStart = System.nanoTime();
        for (String asin : deleteKeys) {
            bst.delete(asin);
        }
        long deleteEnd = System.nanoTime();

        int heightAfterDelete = bst.height();

        return new ExperimentResult(
                "BST",
                inputType,
                products.size(),
                insertEnd - insertStart,
                searchEnd - searchStart,
                deleteEnd - deleteStart,
                heightBeforeDelete,
                heightAfterDelete,
                bst.size()
        );
    }

    /**
     * Runs a full experiment on an AVL Tree.
     *
     * @param products  dataset of products
     * @param inputType input type (Random or Sorted)
     * @return experiment result for AVL
     */
    public static ExperimentResult runAVLExperiment(List<Product> products, String inputType) {
        AVLTree avl = new AVLTree();

        long insertStart = System.nanoTime();
        for (Product product : products) {
            avl.insert(product);
        }
        long insertEnd = System.nanoTime();

        long searchStart = System.nanoTime();
        runSearchesOnAVL(avl, products);
        long searchEnd = System.nanoTime();

        int heightBeforeDelete = avl.height();

        List<String> deleteKeys = chooseDeleteKeys(products, 1000);

        long deleteStart = System.nanoTime();
        for (String asin : deleteKeys) {
            avl.delete(asin);
        }
        long deleteEnd = System.nanoTime();

        int heightAfterDelete = avl.height();

        return new ExperimentResult(
                "AVL",
                inputType,
                products.size(),
                insertEnd - insertStart,
                searchEnd - searchStart,
                deleteEnd - deleteStart,
                heightBeforeDelete,
                heightAfterDelete,
                avl.size()
        );
    }

    /**
     * Executes search operations on BST.
     */
    private static void runSearchesOnBST(BinarySearchTree bst, List<Product> products) {
        int limit = Math.min(1000, products.size());

        for (int i = 0; i < limit; i++) {
            bst.search(products.get(i).getAsin());
        }

        for (int i = 0; i < limit; i++) {
            bst.search("NOT_FOUND_" + i);
        }
    }

    /**
     * Executes search operations on AVL Tree.
     */
    private static void runSearchesOnAVL(AVLTree avl, List<Product> products) {
        int limit = Math.min(1000, products.size());

        for (int i = 0; i < limit; i++) {
            avl.search(products.get(i).getAsin());
        }

        for (int i = 0; i < limit; i++) {
            avl.search("NOT_FOUND_" + i);
        }
    }

    /**
     * Selects ASIN keys for deletion testing.
     */
    private static List<String> chooseDeleteKeys(List<Product> products, int maxDeletes) {
        List<String> keys = new ArrayList<>();
        int step = Math.max(1, products.size() / Math.max(1, maxDeletes));

        for (int i = 0; i < products.size() && keys.size() < maxDeletes; i += step) {
            keys.add(products.get(i).getAsin());
        }

        return keys;
    }

    /**
     * Prints formatted comparison between BST and AVL results.
     */
    public static void printComparison(ExperimentResult bstResult, ExperimentResult avlResult) {
        System.out.println(bstResult.toPrettyString());
        System.out.println(avlResult.toPrettyString());

        System.out.println("\n========== COMPARISON SUMMARY ==========");
        System.out.println("Insertion: " + betterTime(bstResult.getInsertionTimeNs(), avlResult.getInsertionTimeNs(), "BST", "AVL"));
        System.out.println("Search   : " + betterTime(bstResult.getSearchTimeNs(), avlResult.getSearchTimeNs(), "BST", "AVL"));
        System.out.println("Delete   : " + betterTime(bstResult.getDeleteTimeNs(), avlResult.getDeleteTimeNs(), "BST", "AVL"));
        System.out.println("Height   : " + betterHeight(bstResult.getHeightBeforeDelete(), avlResult.getHeightBeforeDelete(), "BST", "AVL"));
        System.out.println("========================================");
    }

    /**
     * Compares execution times.
     */
    private static String betterTime(long bstTime, long avlTime, String bstLabel, String avlLabel) {
        if (bstTime < avlTime) return bstLabel + " faster";
        if (avlTime < bstTime) return avlLabel + " faster";
        return "Equal";
    }

    /**
     * Compares tree heights.
     */
    private static String betterHeight(int bstHeight, int avlHeight, String bstLabel, String avlLabel) {
        if (bstHeight < avlHeight) return bstLabel + " shorter";
        if (avlHeight < bstHeight) return avlLabel + " shorter";
        return "Equal";
    }
}