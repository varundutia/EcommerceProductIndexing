package ecommerce.core;

import java.util.ArrayList;
import java.util.List;

import ecommerce.avl.AVLTree;
import ecommerce.bst.BinarySearchTree;
import ecommerce.model.Product;

public class ComparisonRunner {

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

    private static void runSearchesOnBST(BinarySearchTree bst, List<Product> products) {
        int limit = Math.min(1000, products.size());

        for (int i = 0; i < limit; i++) {
            bst.search(products.get(i).getAsin());
        }

        for (int i = 0; i < limit; i++) {
            bst.search("NOT_FOUND_" + i);
        }
    }

    private static void runSearchesOnAVL(AVLTree avl, List<Product> products) {
        int limit = Math.min(1000, products.size());

        for (int i = 0; i < limit; i++) {
            avl.search(products.get(i).getAsin());
        }

        for (int i = 0; i < limit; i++) {
            avl.search("NOT_FOUND_" + i);
        }
    }

    private static List<String> chooseDeleteKeys(List<Product> products, int maxDeletes) {
        List<String> keys = new ArrayList<>();
        int step = Math.max(1, products.size() / Math.max(1, maxDeletes));
        for (int i = 0; i < products.size() && keys.size() < maxDeletes; i += step) {
            keys.add(products.get(i).getAsin());
        }
        return keys;
    }

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

    private static String betterTime(long bstTime, long avlTime, String bstLabel, String avlLabel) {
        if (bstTime < avlTime) {
            return bstLabel + " faster";
        } else if (avlTime < bstTime) {
            return avlLabel + " faster";
        }
        return "Equal";
    }

    private static String betterHeight(int bstHeight, int avlHeight, String bstLabel, String avlLabel) {
        if (bstHeight < avlHeight) {
            return bstLabel + " shorter";
        } else if (avlHeight < bstHeight) {
            return avlLabel + " shorter";
        }
        return "Equal";
    }
}
