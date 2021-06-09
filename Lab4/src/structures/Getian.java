package structures;

import structures.matrices.*;

import java.util.function.Function;
import java.util.stream.IntStream;

public class Getian {
    private final Function<Vector, Double>[][] matrix;
    private final int size;

    @SafeVarargs
    public Getian(final Function<Vector, Double>[]... functions) {
        size = functions.length;
        for (Function<Vector, Double>[] line : functions) {
            if (line.length != size) {
                throw new IllegalArgumentException("Not a matrix!");
            }
        }
        matrix = functions;
    }

    public Matrix apply(final Vector input, final Type type) {
        double[][] values = (double[][]) IntStream.range(0, size).
                mapToObj(i -> IntStream.range(0, size).
                        mapToDouble(j -> matrix[i][j].apply(input)).
                        toArray())
                .toArray();
        if (type == Type.PROFILE) {
            return new ProfileMatrix(values);
        } else if (type == Type.SPARSE) {
            return new SparseMatrix(values);
        } else {
            return new DenseMatrix(values);
        }
    }
}
