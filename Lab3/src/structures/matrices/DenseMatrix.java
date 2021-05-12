package structures.matrices;

public class DenseMatrix implements Matrix {
    final int rows, columns;
    final double[] values;

    public DenseMatrix(int rows, int columns, double... values) {
        assert rows >= 1 && columns >= 1;
        this.rows = rows;
        this.columns = columns;
        this.values = values;
    }

    private void checkPos(int i, int j) {
        assert i >= 0 && i < rows && j >= 0 && j < columns;
    }

    @Override
    public double get(int i, int j) {
        checkPos(i, j);
        return values[i * columns + j];
    }

    @Override
    public void set(int i, int j, double value) {
        checkPos(i, j);
        values[i * columns + j] = value;
    }

    @Override
    public int size() {
        return values.length;
    }
}
