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
        try (var reader = Files.newBufferedReader(source)) {
            String line;
            while ((line = reader.readLine()) != null) {
                Diagonal diag = FileReadable.parseDiagonal(line);
                int n = Math.abs(diag.getNumber()) + diag.getVector().size();
                if (values == null) {
                    rows = columns = n;
                    values = new double[n][n];
                }
                int i = 0, j = 0, k = 0;
                if (diag.getNumber() > 0) {
                    j = diag.getNumber();
                } else {
                    i = -diag.getNumber();
                }
                while (i < n && j < n) {
                    values[i++][j++] = diag.getVector().get(k++);
                }
            }
        }
    }
}
