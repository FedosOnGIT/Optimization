package structures;

import java.util.ArrayList;
import java.util.function.Function;

public class QuadraticFunction implements Function<Vector, Double> {
    private final int size;
    private final Function<Vector, Double> function;
    private final ArrayList<Double> eigenvalues;
    private final Function<Vector, Vector> gradient;

    public QuadraticFunction(final int size,
                             final Function<Vector, Double> function,
                             final ArrayList<Double> eigenvalues,
                             final Function<Vector, Vector> gradient) throws NotConvexFunctionException {
        assert eigenvalues.size() == size;
        this.function = function;
        this.size = size;
        this.eigenvalues = eigenvalues;
        this.eigenvalues.sort(Double::compare);
        this.gradient = gradient;
        if (eigenvalues.get(0) <= 0) {
            throw new NotConvexFunctionException("Function is not convex!");
        }
    }

    public Double apply(final Vector variable) {
        assert variable.size() == size;
        return function.apply(variable);
    }

    public int size() {
        return size;
    }

    public double minEigenValue() {
        return eigenvalues.get(0);
    }

    public double maxEigenValue() {
        return eigenvalues.get(size - 1);
    }

    public Vector applyGradient(final Vector variable) {
        return gradient.apply(variable);
    }
}
