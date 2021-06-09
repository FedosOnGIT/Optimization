package minimizationMethods;

import structures.matrices.Vector;

import java.util.function.Function;

public interface MinimizationMethod {
    Double minimal(final Function<Double, Double> function, double start, double end, double epsilon);
}
