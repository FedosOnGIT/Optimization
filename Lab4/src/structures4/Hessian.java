package structures4;

import structures.matrices.DenseMatrix;
import structures.matrices.Matrix;
import structures.matrices.Vector;

import java.util.List;
import java.util.function.Function;

public class Hessian implements Function<Vector, Matrix> {
    private final List<List<Function<Vector, Double>>> diffs;

    public Hessian(final List<List<Function<Vector, Double>>> diffs) {
        this.diffs = diffs;
    }

    @Override
    public Matrix apply(Vector vector) {
        int size = diffs.size();
        double[][] values = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                values[i][j] = diffs.get(i).get(j).apply(vector);
            }
        }
        return new DenseMatrix(values);
    }
}
