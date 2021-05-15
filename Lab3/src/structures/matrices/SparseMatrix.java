package structures.matrices;

public class SparseMatrix extends Matrix {
    @Override
    public SparseMatrix copy() {
        return null;
    }

    @Override
    protected double getImpl(int i, int j) {
        return 0;
    }

    @Override
    protected void setImpl(int i, int j, double value) {

    }

    @Override
    public int rowsCount() {
        return 0;
    }

    @Override
    public int columnsCount() {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }
}