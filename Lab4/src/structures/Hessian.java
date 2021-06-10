package structures;

import structures.matrices.*;

import java.util.function.Function;
import java.util.stream.IntStream;

public class Hessian implements Function<Vector, Matrix> {
    private final Function<Vector, Double>[][] matrix;
    private final int size;

    @SafeVarargs
    public Hessian(final Function<Vector, Double>[]... functions) {
        size = functions.length;
        for (Function<Vector, Double>[] line : functions) {
            if (line.length != size) {
                throw new IllegalArgumentException("Not a matrix!");
            }
        }
        matrix = functions;
    }

    @Override
    public Matrix apply(Vector vector) {
        double[][] values = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                values[i][j] = matrix[i][j].apply(vector);
            }
        }

        return new DenseMatrix(values);
    }
}
