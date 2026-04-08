package ecommerce.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Utility class for averaging benchmark results across multiple runs.
 */
public class AverageRunner {

    private AverageRunner() {
        // utility class
    }

    /**
     * Computes the average values from a list of experiment results.
     *
     * @param results list of results to average
     * @return averaged experiment result
     */
    public static ExperimentResult averageResults(List<ExperimentResult> results) {
        if (results == null || results.isEmpty()) {
            throw new IllegalArgumentException("Results list cannot be null or empty.");
        }

        String treeType = results.get(0).getTreeType();
        String inputType = results.get(0).getInputType();
        int datasetSize = results.get(0).getDatasetSize();

        long totalInsertionTime = 0L;
        long totalSearchTime = 0L;
        long totalDeleteTime = 0L;
        long totalHeightBeforeDelete = 0L;
        long totalHeightAfterDelete = 0L;
        long totalFinalSize = 0L;

        for (ExperimentResult result : results) {
            totalInsertionTime += result.getInsertionTimeNs();
            totalSearchTime += result.getSearchTimeNs();
            totalDeleteTime += result.getDeleteTimeNs();
            totalHeightBeforeDelete += result.getHeightBeforeDelete();
            totalHeightAfterDelete += result.getHeightAfterDelete();
            totalFinalSize += result.getFinalSize();
        }

        int count = results.size();

        return new ExperimentResult(
                treeType,
                inputType + " (Average of " + count + " runs)",
                datasetSize,
                totalInsertionTime / count,
                totalSearchTime / count,
                totalDeleteTime / count,
                (int) (totalHeightBeforeDelete / count),
                (int) (totalHeightAfterDelete / count),
                (int) (totalFinalSize / count)
        );
    }

    /**
     * Runs the same experiment multiple times and returns the average result.
     *
     * @param runs number of runs
     * @param experimentSupplier supplier that executes the experiment
     * @return averaged experiment result
     */
    public static ExperimentResult runAverage(int runs, Supplier<ExperimentResult> experimentSupplier) {
        if (runs <= 0) {
            throw new IllegalArgumentException("Number of runs must be greater than 0.");
        }
        if (experimentSupplier == null) {
            throw new IllegalArgumentException("Experiment supplier cannot be null.");
        }

        List<ExperimentResult> results = new ArrayList<>();

        for (int i = 0; i < runs; i++) {
            results.add(experimentSupplier.get());
        }

        return averageResults(results);
    }
}