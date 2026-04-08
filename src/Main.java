import java.util.List;
import java.util.Scanner;

import ecommerce.core.AVLExperimentRunner;
import ecommerce.core.AverageRunner;
import ecommerce.core.BSTExperimentRunner;
import ecommerce.core.ComparisonRunner;
import ecommerce.core.ExperimentResult;
import ecommerce.model.Product;
import ecommerce.util.CSVReader;

/**
 * Entry point for the Ecommerce BST vs AVL Tree benchmarking application.
 *
 * <p>This program loads product data from a CSV file and compares the practical
 * performance of a Binary Search Tree (BST) and an AVL Tree under different
 * experimental conditions. The user can choose the dataset size, input order,
 * and execution mode from the console.</p>
 *
 * <p>The benchmark measures:
 * <ul>
 *   <li>Insertion time</li>
 *   <li>Search time</li>
 *   <li>Deletion time</li>
 *   <li>Tree height before and after deletion</li>
 * </ul>
 * </p>
 *
 * <p>Supported experiment modes:
 * <ul>
 *   <li>Single run</li>
 *   <li>Averaged run over 5 iterations</li>
 * </ul>
 * </p>
 *
 * <p>Supported input types:
 * <ul>
 *   <li>Random input</li>
 *   <li>Sorted input</li>
 *   <li>Both</li>
 * </ul>
 * </p>
 */
public class Main {

    /**
     * Runs the ecommerce benchmarking application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        final String RESET = "\u001B[0m";
        final String CYAN = "\u001B[36m";
        final String GREEN = "\u001B[32m";
        final String YELLOW = "\u001B[33m";
        final String PURPLE = "\u001B[35m";
        final String RED = "\u001B[31m";

        System.out.println(CYAN +
                "========================================================");
        System.out.println("         ECOMMERCE BST VS AVL TREE COMPARISON");
        System.out.println("========================================================" + RESET);

        System.out.println(PURPLE +
                "\nBinary Search Tree vs AVL Tree Benchmark" + RESET);

        Scanner scanner = new Scanner(System.in);

        // ===== Dataset Selection =====
        System.out.println(GREEN + "\nDatasets Available:" + RESET);
        System.out.println("1 - 10,000 records");
        System.out.println("2 - 50,000 records");
        System.out.println("3 - 100,000 records");
        System.out.print("Enter dataset choice: ");

        int choice = scanner.nextInt();

        String filePath = "src/data/amazon_100k.csv";
        int datasetSize;

        switch (choice) {
            case 1:
                datasetSize = 10_000;
                break;
            case 2:
                datasetSize = 50_000;
                break;
            case 3:
                datasetSize = 100_000;
                break;
            default:
                System.out.println(RED + "Invalid choice. Defaulting to 10,000." + RESET);
                datasetSize = 10_000;
        }

        // ===== Input Type Selection =====
        System.out.println(YELLOW + "\nInput Order:" + RESET);
        System.out.println("1 - Random Input");
        System.out.println("2 - Sorted Input");
        System.out.println("3 - Both");
        System.out.print("Enter input type: ");

        int inputChoice = scanner.nextInt();

        // ===== Averaging Option =====
        System.out.println(YELLOW + "\nExecution Mode:" + RESET);
        System.out.println("1 - Single Run");
        System.out.println("2 - Averaged (5 runs)");
        System.out.print("Enter mode: ");

        int mode = scanner.nextInt();
        boolean useAverage = (mode == 2);

        // ===== Load Data =====
        List<Product> baseProducts = CSVReader.loadProducts(filePath, datasetSize);

        if (baseProducts.isEmpty()) {
            System.out.println(RED + "\nNo products loaded. Check file path and CSV format." + RESET);
            scanner.close();
            return;
        }

        System.out.println("\nDataset file used: " + filePath);
        System.out.println("Dataset limit applied: " + datasetSize);
        System.out.println("Total products loaded: " + baseProducts.size());
        System.out.println("========================================================");

        // ===== Run Experiments =====
        if (inputChoice == 1 || inputChoice == 3) {
            System.out.println(CYAN + "\n--- RANDOM INPUT EXPERIMENT ---" + RESET);

            List<Product> randomProducts = CSVReader.shuffledCopy(baseProducts, 42L);

            ExperimentResult bstRandomResult;
            ExperimentResult avlRandomResult;

            if (useAverage) {
                bstRandomResult = AverageRunner.runAverage(
                        5,
                        () -> BSTExperimentRunner.runExperiment(randomProducts, "Random")
                );

                avlRandomResult = AverageRunner.runAverage(
                        5,
                        () -> AVLExperimentRunner.runExperiment(randomProducts, "Random")
                );
            } else {
                bstRandomResult = BSTExperimentRunner.runExperiment(randomProducts, "Random");
                avlRandomResult = AVLExperimentRunner.runExperiment(randomProducts, "Random");
            }

            ComparisonRunner.printComparison(bstRandomResult, avlRandomResult);
        }

        if (inputChoice == 2 || inputChoice == 3) {
            System.out.println(CYAN + "\n--- SORTED INPUT EXPERIMENT ---" + RESET);

            List<Product> sortedProducts = CSVReader.sortedByAsinCopy(baseProducts);

            ExperimentResult bstSortedResult;
            ExperimentResult avlSortedResult;

            if (useAverage) {
                bstSortedResult = AverageRunner.runAverage(
                        5,
                        () -> BSTExperimentRunner.runExperiment(sortedProducts, "Sorted")
                );

                avlSortedResult = AverageRunner.runAverage(
                        5,
                        () -> AVLExperimentRunner.runExperiment(sortedProducts, "Sorted")
                );
            } else {
                bstSortedResult = BSTExperimentRunner.runExperiment(sortedProducts, "Sorted");
                avlSortedResult = AVLExperimentRunner.runExperiment(sortedProducts, "Sorted");
            }

            ComparisonRunner.printComparison(bstSortedResult, avlSortedResult);
        }

        System.out.println(GREEN +
                "\n========================================================");
        System.out.println("Benchmark Execution Completed Successfully");
        System.out.println("========================================================" + RESET);

        scanner.close();
    }
}