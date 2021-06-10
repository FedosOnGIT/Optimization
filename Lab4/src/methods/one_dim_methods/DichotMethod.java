package methods.one_dim_methods;

import java.util.function.Function;

public class DichotMethod implements MinimizationMethod {
    private final double delta;

    public DichotMethod(final double delta) {
        this.delta = delta;
    }

    @Override
    public Double min(Function<Double, Double> function, double start, double end, double epsilon) {
        double delta = Math.min(this.delta, epsilon / 10);
        while (end - start > 2 * epsilon) {
            double alpha1 = (end + start - delta) / 2;
            double alpha2 = (end + start + delta) / 2;
            // one = f(point - alpha1 * gradient(point));
            Double one = function.apply(alpha1);
            // two = f(point - alpha2 * gradient(point));
            Double two = function.apply(alpha2);
            if (one < two) {
                end = alpha2;
            } else {
                start = alpha1;
            }
        }
        return (start + end) / 2;
    }
}
