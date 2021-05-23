package structures.matrices;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class DenseMatrix extends FileReadableMatrix {
    int size;
    double[][] values;

    public DenseMatrix(double[][] values) {
        checkIsSquare(values);
        this.size = values.length;
        this.values = values;
    }

    public DenseMatrix(Path source) throws IOException {
        this(readToDense(source));
    }

    public DenseMatrix(List<Diagonal> diagonals) {
        for (Diagonal diag : diagonals) {
            int n = Math.abs(diag.getNumber()) + diag.getVector().size();
            if (values == null) {
                values = new double[n][n];
                size = n;
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
        return size;
    }

    @Override
    public int columnsCount() {
        return size;
    }

    @Override
    public DenseMatrix copy() {
        double[][] values = new double[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(this.values[i], 0, values[i], 0, size);
        }
        return new DenseMatrix(values);
    }
}
