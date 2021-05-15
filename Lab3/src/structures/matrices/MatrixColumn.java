package structures.matrices;

public class MatrixColumn extends MatrixVector {
    public MatrixColumn(Matrix matrix, int index) {
        super(matrix, index);
    }

    @Override
    protected double getImpl(int index) {
        return matrix.get(index, this.index);
    }

    @Override
    protected void setImpl(int index, double value) {
        matrix.setImpl(index, this.index, value);
    }

    @Override
    public int size() {
        return matrix.rowsCount();
    }
}
