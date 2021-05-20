package structures.matrices;

import java.io.IOException;
import java.nio.file.Path;

public class DenseMatrix extends FileReadableMatrix {
    final int size;
    final double[][] values;

    public DenseMatrix(double[][] values) {
        checkIsSquare(values);
        this.size = values.length;
        this.values = values;
    }

    public DenseMatrix(Path source) throws IOException {
        this(readToDense(source));
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
