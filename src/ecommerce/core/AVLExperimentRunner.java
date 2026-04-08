package ecommerce.core;

import java.util.ArrayList;
import java.util.List;

import ecommerce.avl.AVLTree;
import ecommerce.model.Product;

/**
 * Runs benchmark experiments for the AVL Tree implementation.
 */
public class AVLExperimentRunner {

    private AVLExperimentRunner() {
        // utility class
    }

    /**
     * Runs insertion, search, and deletion experiments on an AVL Tree.
     *
     * @param products  list of products to test
     * @param inputType input order type (e.g. Random or Sorted)
     * @return experiment result containing timing and height metrics
     */
    public static ExperimentResult runExperiment(List<Product> products, String inputType) {
        if (products == null) {
            throw new IllegalArgumentException("Products list cannot be null.");
        }

        AVLTree avl = new AVLTree();

        long insertionStart = System.nanoTime();
        for (Product product : products) {
            avl.insert(product);
        }
        long insertionEnd = System.nanoTime();

        long searchStart = System.nanoTime();
        runSearches(avl, products);
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
                insertionEnd - insertionStart,
                searchEnd - searchStart,
                deleteEnd - deleteStart,
                heightBeforeDelete,
                heightAfterDelete,
                avl.size()
        );
    }

    /**
     * Performs successful and unsuccessful search operations on the AVL Tree.
     *
     * @param avl      AVL tree instance
     * @param products product list used for generating search keys
     */
    private static void runSearches(AVLTree avl, List<Product> products) {
        int limit = Math.min(1000, products.size());

        for (int i = 0; i < limit; i++) {
            avl.search(products.get(i).getAsin());
        }

        for (int i = 0; i < limit; i++) {
            avl.search("NOT_FOUND_" + i);
        }
    }

    /**
     * Selects a subset of product ASINs for deletion experiments.
     *
     * @param products    product list
     * @param maxDeletes  maximum number of ASINs to choose
     * @return list of ASIN keys for deletion
     */
    private static List<String> chooseDeleteKeys(List<Product> products, int maxDeletes) {
        List<String> keys = new ArrayList<>();

        if (products.isEmpty() || maxDeletes <= 0) {
            return keys;
        }

        int step = Math.max(1, products.size() / maxDeletes);

        for (int i = 0; i < products.size() && keys.size() < maxDeletes; i += step) {
            keys.add(products.get(i).getAsin());
        }

        return keys;
    }
}