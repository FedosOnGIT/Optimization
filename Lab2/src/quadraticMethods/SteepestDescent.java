package quadraticMethods;

import methods.DichotMethod;
import methods.Method;
import structures.QuadraticFunction;
import structures.Vector;

public class SteepestDescent implements QuadraticMethod {
    private final Method MinimizingFunction;

    public SteepestDescent() {
        MinimizingFunction = new DichotMethod(0.0001);
    }

    public SteepestDescent(Method MinimizingFunction) {
        this.MinimizingFunction = MinimizingFunction;
    }

    @Override
    public Vector minimum(QuadraticFunction function, Vector point, double epsilon) {
        Vector gradient = function.applyGradient(point);
        while (gradient.length() > epsilon) {
            final Vector helpPoint = point;
            final Vector helpGradient = gradient;
            double alpha = MinimizingFunction.minimum(
                    coefficient -> function.apply(helpPoint.plus(helpGradient.multiply(-coefficient))),
                    0,
                    2 / function.maxEigenValue(),
                    0.00001);
            point = helpPoint.plus(gradient.multiply(-alpha));
            gradient = function.applyGradient(point);
        }
        return point;
    }
}
