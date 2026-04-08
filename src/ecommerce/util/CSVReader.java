package ecommerce.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import ecommerce.model.Product;

/**
 * Utility class for reading and processing product data from CSV files.
 *
 * <p>This class provides methods to:
 * <ul>
 *     <li>Load product data from a CSV file</li>
 *     <li>Parse CSV lines with support for quoted values</li>
 *     <li>Generate shuffled and sorted datasets for experimentation</li>
 *     <li>Extract subsets of product data</li>
 * </ul>
 *
 * <p>The CSV format is expected to contain the following columns:
 * asin, title, stars, reviews, price, category_id</p>
 *
 * <p>This class is designed to support benchmarking experiments comparing
 * different tree data structures such as Binary Search Trees and AVL Trees.</p>
 *
 * @author Varun Hameer Dutia
 * @author Shreyas
 * @author Reuban
 */
public class CSVReader {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private CSVReader() {
        // utility class
    }

    /**
     * Loads product data from a CSV file into a list of {@link Product} objects.
     *
     * <p>This method reads the file line by line, skips the header,
     * parses each row safely, and constructs Product objects.
     * Malformed or incomplete rows are ignored.</p>
     *
     * @param filePath the relative or absolute path to the CSV file
     * @param limit    maximum number of products to load (use <= 0 for no limit)
     * @return a list of Product objects
     */
    public static List<Product> loadProducts(String filePath, int limit) {
        List<Product> products = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (line.trim().isEmpty()) {
                    continue;
                }

                List<String> values = parseCSVLine(line);

                if (values.size() < 6) {
                    continue;
                }

                String asin = clean(values.get(0));
                String title = clean(values.get(1));
                double stars = parseDouble(values.get(2));
                int reviews = parseInt(values.get(3));
                double price = parseDouble(values.get(4));
                int categoryId = parseInt(values.get(5));

                if (asin.isEmpty()) {
                    continue;
                }

                products.add(new Product(asin, title, stars, reviews, price, categoryId));

                if (limit > 0 && products.size() >= limit) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }

        return products;
    }

    /**
     * Returns a shuffled copy of the given product list.
     *
     * <p>This is useful for generating randomized input datasets
     * for performance benchmarking.</p>
     *
     * @param products the original product list
     * @param seed     seed value for reproducible shuffling
     * @return a shuffled list of products
     */
    public static List<Product> shuffledCopy(List<Product> products, long seed) {
        List<Product> copy = new ArrayList<>(products);
        Collections.shuffle(copy, new Random(seed));
        return copy;
    }

    /**
     * Returns a sorted copy of the product list based on ASIN.
     *
     * <p>This method is used to simulate worst-case scenarios
     * for Binary Search Trees.</p>
     *
     * @param products the original product list
     * @return a sorted list of products
     */
    public static List<Product> sortedByAsinCopy(List<Product> products) {
        List<Product> copy = new ArrayList<>(products);
        copy.sort(Comparator.comparing(Product::getAsin));
        return copy;
    }

    /**
     * Returns the first N elements from the product list.
     *
     * @param products the original product list
     * @param n        number of elements to extract
     * @return a list containing the first N products
     */
    public static List<Product> firstN(List<Product> products, int n) {
        if (products == null || n <= 0) {
            return new ArrayList<>();
        }

        int end = Math.min(n, products.size());
        return new ArrayList<>(products.subList(0, end));
    }

    /**
     * Cleans a string value by trimming whitespace.
     *
     * @param value input string
     * @return cleaned string or empty string if null
     */
    private static String clean(String value) {
        return value == null ? "" : value.trim();
    }

    /**
     * Safely parses a string into a double value.
     *
     * <p>Removes currency symbols and commas before parsing.
     * Returns 0.0 if parsing fails.</p>
     *
     * @param value string value
     * @return parsed double value
     */
    private static double parseDouble(String value) {
        try {
            String cleaned = clean(value)
                    .replace("$", "")
                    .replace(",", "");

            return cleaned.isEmpty() ? 0.0 : Double.parseDouble(cleaned);
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * Safely parses a string into an integer value.
     *
     * <p>Removes commas and returns 0 if parsing fails.</p>
     *
     * @param value string value
     * @return parsed integer value
     */
    private static int parseInt(String value) {
        try {
            String cleaned = clean(value).replace(",", "");
            return cleaned.isEmpty() ? 0 : Integer.parseInt(cleaned);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Parses a CSV line into individual values, handling quoted fields.
     *
     * <p>This method ensures that commas inside quoted strings
     * do not break the parsing logic.</p>
     *
     * @param line CSV line
     * @return list of parsed values
     */
    private static List<String> parseCSVLine(String line) {
        List<String> values = new ArrayList<>();

        if (line == null || line.isEmpty()) {
            return values;
        }

        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (ch == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (ch == ',' && !inQuotes) {
                values.add(current.toString());
                current.setLength(0);
            } else {
                current.append(ch);
            }
        }

        values.add(current.toString());
        return values;
    }
}