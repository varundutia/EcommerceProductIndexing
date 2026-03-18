package ecommerce.core;

import java.util.ArrayList;
import java.util.List;

import ecommerce.bst.BinarySearchTree;
import ecommerce.model.Product;

public class BSTExperimentRunner {

    private BSTExperimentRunner() {
        // utility class
    }

    public static ExperimentResult runExperiment(List<Product> products, String inputType) {
        if (products == null) {
            throw new IllegalArgumentException("Products list cannot be null.");
        }

        BinarySearchTree bst = new BinarySearchTree();

        long insertionStart = System.nanoTime();
        for (Product product : products) {
            bst.insert(product);
        }
        long insertionEnd = System.nanoTime();

        long searchStart = System.nanoTime();
        runSearches(bst, products);
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
                insertionEnd - insertionStart,
                searchEnd - searchStart,
                deleteEnd - deleteStart,
                heightBeforeDelete,
                heightAfterDelete,
                bst.size()
        );
    }

    private static void runSearches(BinarySearchTree bst, List<Product> products) {
        int limit = Math.min(1000, products.size());

        for (int i = 0; i < limit; i++) {
            bst.search(products.get(i).getAsin());
        }

        for (int i = 0; i < limit; i++) {
            bst.search("NOT_FOUND_" + i);
        }
    }

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