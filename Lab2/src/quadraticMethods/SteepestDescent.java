package quadraticMethods;

import methods.Method;
import structures.*;

public class SteepestDescent implements QuadraticMethod {
    private final Method MinimizingFunction;

    public SteepestDescent(Method MinimizingFunction) {
        this.MinimizingFunction = MinimizingFunction;
    }

    @Override
    public SteepestDescentResult minimum(QuadraticFunction function, Vector point, double epsilon) {
        SteepestDescentResult result = new SteepestDescentResult(function);
        Vector gradient = function.applyGradient(point);
        while (gradient.length() > epsilon) {
            result.add(point);
            final Vector helpPoint = point;
            final Vector helpGradient = gradient;
            MethodResult<Double> linearMinimization = MinimizingFunction.minimum(
                    coefficient -> function.apply(helpPoint.plus(helpGradient.multiply(-coefficient))),
                    0,
                    2 / function.maxEigenValue(),
                    0.00001);
            double alpha = linearMinimization.getMinimal();
            result.addAlpha(new AlphaPair(alpha, linearMinimization.iterations()));
            point = helpPoint.plus(gradient.multiply(-alpha));
            gradient = function.applyGradient(point);
        }
        result.add(point);
        return result;
    }
}
