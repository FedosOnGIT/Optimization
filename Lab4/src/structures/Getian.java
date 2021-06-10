package structures;

import structures.matrices.*;

import java.util.function.Function;
import java.util.stream.IntStream;

public class Getian implements Function<Vector, Matrix> {
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

    @Override
    public Matrix apply(Vector vector) {
        return new DenseMatrix(
                (double[][]) IntStream.range(0, size).
                        mapToObj(i -> IntStream.range(0, size).
                                mapToDouble(j -> matrix[i][j].apply(vector)).
                                toArray())
                        .toArray()
        );
    }
}
