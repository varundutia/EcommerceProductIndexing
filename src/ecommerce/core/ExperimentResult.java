package ecommerce.core;

/**
 * Stores the results of a single tree experiment run.
 * Includes timing metrics, tree height, and final size.
 */
public class ExperimentResult {

    private final String treeType;
    private final String inputType;
    private final int datasetSize;
    private final long insertionTimeNs;
    private final long searchTimeNs;
    private final long deleteTimeNs;
    private final int heightBeforeDelete;
    private final int heightAfterDelete;
    private final int finalSize;

    /**
     * Creates an immutable experiment result.
     *
     * @param treeType           type of tree (BST or AVL)
     * @param inputType          input type (Random or Sorted)
     * @param datasetSize        number of elements used
     * @param insertionTimeNs    insertion time (nanoseconds)
     * @param searchTimeNs       search time (nanoseconds)
     * @param deleteTimeNs       deletion time (nanoseconds)
     * @param heightBeforeDelete tree height before deletions
     * @param heightAfterDelete  tree height after deletions
     * @param finalSize          final number of elements
     */
    public ExperimentResult(String treeType, String inputType, int datasetSize,
                            long insertionTimeNs, long searchTimeNs, long deleteTimeNs,
                            int heightBeforeDelete, int heightAfterDelete, int finalSize) {
        this.treeType = treeType;
        this.inputType = inputType;
        this.datasetSize = datasetSize;
        this.insertionTimeNs = insertionTimeNs;
        this.searchTimeNs = searchTimeNs;
        this.deleteTimeNs = deleteTimeNs;
        this.heightBeforeDelete = heightBeforeDelete;
        this.heightAfterDelete = heightAfterDelete;
        this.finalSize = finalSize;
    }

    /** @return tree type */
    public String getTreeType() { return treeType; }

    /** @return input type */
    public String getInputType() { return inputType; }

    /** @return dataset size */
    public int getDatasetSize() { return datasetSize; }

    /** @return insertion time (ns) */
    public long getInsertionTimeNs() { return insertionTimeNs; }

    /** @return search time (ns) */
    public long getSearchTimeNs() { return searchTimeNs; }

    /** @return delete time (ns) */
    public long getDeleteTimeNs() { return deleteTimeNs; }

    /** @return height before deletion */
    public int getHeightBeforeDelete() { return heightBeforeDelete; }

    /** @return height after deletion */
    public int getHeightAfterDelete() { return heightAfterDelete; }

    /** @return final size after operations */
    public int getFinalSize() { return finalSize; }

    /**
     * Returns a compact string representation.
     */
    @Override
    public String toString() {
        return "ExperimentResult{" +
                "treeType='" + treeType + '\'' +
                ", inputType='" + inputType + '\'' +
                ", datasetSize=" + datasetSize +
                ", insertionTimeNs=" + insertionTimeNs +
                ", searchTimeNs=" + searchTimeNs +
                ", deleteTimeNs=" + deleteTimeNs +
                ", heightBeforeDelete=" + heightBeforeDelete +
                ", heightAfterDelete=" + heightAfterDelete +
                ", finalSize=" + finalSize +
                '}';
    }

    /**
     * Returns a formatted, readable output for console display.
     */
    public String toPrettyString() {
        return "\n==============================\n" +
                "Tree Type         : " + treeType + "\n" +
                "Input Type        : " + inputType + "\n" +
                "Dataset Size      : " + datasetSize + "\n" +
                "Insertion Time    : " + insertionTimeNs + " ns\n" +
                "Search Time       : " + searchTimeNs + " ns\n" +
                "Delete Time       : " + deleteTimeNs + " ns\n" +
                "Height Before Del : " + heightBeforeDelete + "\n" +
                "Height After Del  : " + heightAfterDelete + "\n" +
                "Final Size        : " + finalSize + "\n" +
                "==============================";
    }
}