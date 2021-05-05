package quadraticMethods;

import methods.Method;
import structures.*;

import static methods.Method.MAX_ITER;

public class SteepestDescent implements QuadraticMethod {
    private final Method MinimizingFunction;

    public SteepestDescent(Method MinimizingFunction) {
        this.MinimizingFunction = MinimizingFunction;
    }

    @Override
    public SteepestDescentResult minimum(QuadraticFunction function, Vector point, double epsilon) {
        SteepestDescentResult result = new SteepestDescentResult(function);
        Vector gradient = function.applyGradient(point);
        for (int i = 0; i < MAX_ITER && gradient.length() > epsilon; ++i) {
            result.add(point);
            final Vector helpPoint = point;
            final Vector helpGradient = gradient;
            MethodResult<Double> linearMinimization = MinimizingFunction.minimum(
                    coefficient -> function.apply(helpPoint.plus(helpGradient.multiply(-coefficient))),
                    0, 1,
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
