package structures.matrices;

public class MatrixRow extends MatrixVector {
    public MatrixRow(Matrix matrix, int index) {
        super(matrix, index);
    }

    @Override
    protected double getImpl(int index) {
        return matrix.get(this.index, index);
    }

    @Override
    protected void setImpl(int index, double value) {
        matrix.setImpl(this.index, index, value);
    }

    @Override
    public int size() {
        return matrix.columnsCount();
    }
}
