package methods4.one_dim_methods;

import structures.matrices.Vector;

import java.util.function.Function;

public abstract class AbstractMinimizationMethod implements MinimizationMethod {
    public Double min(final Function<Double, Double> function, double epsilon) {
        return min(function, 0, 10, epsilon);
    }

    public Double min(final Function<Vector, Double> function, final Vector point, final Vector direction, double epsilon) {
        return min(function, point, direction, 0, 3, epsilon);
    }

    public Double min(final Function<Vector, Double> function,
                      final Vector point,
                      final Vector direction,
                      double left, double right,
                      double epsilon) {
        final double norm = direction.norm();
        final Vector normDirection = direction.multiply(1 / norm);
        return min(x -> function.apply(normDirection.multiply(x).addThis(point)), left, right, epsilon/10) / norm;
    }
}
