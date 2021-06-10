package minimizationMethods;

import structures.matrices.Vector;

import java.util.function.Function;

public interface MinimizationMethod {
    Double min(final Function<Double, Double> function, double start, double end, double epsilon);
}
