package structures4;

import structures.matrices.Vector;

import java.util.List;
import java.util.function.Function;

public class Gradient implements Function<Vector, Vector> {
    private final List<Function<Vector, Double>> diffs;

    public Gradient(final List<Function<Vector, Double>> diffs) {
        this.diffs = diffs;
    }

    @Override
    public Vector apply(final Vector input) {
        return new Vector(diffs.stream().mapToDouble(diff -> diff.apply(input)));
    }
}
