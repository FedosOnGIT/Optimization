package structures.matrices;

import structures.FileReadable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DenseMatrix extends Matrix implements FileReadable {
    int rows, columns;
    double[][] values;

    public DenseMatrix(double[][] values) {
        checkIsMatrix(values);
        this.rows = values.length;
        this.columns = values[0].length;
        this.values = values;
    }

    private static void checkIsMatrix(double[][] values) {
        assert values.length > 0;
        int columns = values[0].length;
        for (int i = 1; i < values.length; i++) {
            assert columns == values[i].length;
        }
    }

    @Override
    public DenseMatrix copy() {
        double[][] values = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            if (columns >= 0) {
                System.arraycopy(this.values[i], 0, values[i], 0, columns);
            }
        }
        return new DenseMatrix(values);
    }

    @Override
    protected double getImpl(int i, int j) {
        return values[i][j];
    }

    @Override
    protected void setImpl(int i, int j, double value) {
        values[i][j] = value;
    }

    @Override
    public int rowsCount() {
        return rows;
    }

    @Override
    public int columnsCount() {
        return columns;
    }

    @Override
    public int size() {
        return values.length;
    }

    public DenseMatrix(Path source) throws IOException {
        this(readToDense(source));
    }
}
