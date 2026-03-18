package ecommerce.core;

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

    public String getTreeType() {
        return treeType;
    }

    public String getInputType() {
        return inputType;
    }

    public int getDatasetSize() {
        return datasetSize;
    }

    public long getInsertionTimeNs() {
        return insertionTimeNs;
    }

    public long getSearchTimeNs() {
        return searchTimeNs;
    }

    public long getDeleteTimeNs() {
        return deleteTimeNs;
    }

    public int getHeightBeforeDelete() {
        return heightBeforeDelete;
    }

    public int getHeightAfterDelete() {
        return heightAfterDelete;
    }

    public int getFinalSize() {
        return finalSize;
    }

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
