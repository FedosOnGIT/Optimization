package structures;

import structures.matrices.Vector;

import java.util.function.Function;
import java.util.stream.IntStream;

public class Gradient implements Function<Vector, Vector> {
    private final Function<Vector, Double>[] vector;
    private final int size;

    @SafeVarargs
    public Gradient(final Function<Vector, Double>... functions) {
        vector = functions;
        size = functions.length;
    }

    @Override
    public Vector apply(final Vector input) {
        return new Vector(IntStream.range(0, size).mapToDouble(i -> vector[i].apply(input)));
    }
}
