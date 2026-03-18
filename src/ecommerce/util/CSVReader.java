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

public class CSVReader {

    private CSVReader() {
        // utility class
    }

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

    public static List<Product> shuffledCopy(List<Product> products, long seed) {
        List<Product> copy = new ArrayList<>(products);
        Collections.shuffle(copy, new Random(seed));
        return copy;
    }

    public static List<Product> sortedByAsinCopy(List<Product> products) {
        List<Product> copy = new ArrayList<>(products);
        copy.sort(Comparator.comparing(Product::getAsin));
        return copy;
    }

    public static List<Product> firstN(List<Product> products, int n) {
        if (products == null) {
            return new ArrayList<>();
        }

        if (n <= 0) {
            return new ArrayList<>();
        }

        int end = Math.min(n, products.size());
        return new ArrayList<>(products.subList(0, end));
    }

    private static String clean(String value) {
        if (value == null) {
            return "";
        }
        return value.trim();
    }

    private static double parseDouble(String value) {
        try {
            String cleaned = clean(value)
                    .replace("$", "")
                    .replace(",", "");

            if (cleaned.isEmpty()) {
                return 0.0;
            }

            return Double.parseDouble(cleaned);
        } catch (Exception e) {
            return 0.0;
        }
    }

    private static int parseInt(String value) {
        try {
            String cleaned = clean(value).replace(",", "");

            if (cleaned.isEmpty()) {
                return 0;
            }

            return Integer.parseInt(cleaned);
        } catch (Exception e) {
            return 0;
        }
    }

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