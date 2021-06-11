package methods4.one_dim_methods;

import java.util.function.Function;

public abstract class AbstractMinimizationMethod implements MinimizationMethod {
    public Double min(final Function<Double, Double> function, double epsilon) {
        return min(function, 0, 15, epsilon);
    }
}
