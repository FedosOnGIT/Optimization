package quadraticMethods;

import methods.DichotMethod;
import methods.Method;
import structures.AlphaPair;
import structures.MethodResult;
import structures.QuadraticFunction;
import structures.Vector;

import java.util.ArrayList;

public class SteepestDescent implements QuadraticMethod {
    private final Method MinimizingFunction;
    private ArrayList<AlphaPair> alphas;

    public SteepestDescent() {
        MinimizingFunction = new DichotMethod(0.0001);
    }

    public SteepestDescent(Method MinimizingFunction) {
        this.MinimizingFunction = MinimizingFunction;
    }

    @Override
    public MethodResult<Vector> minimum(QuadraticFunction function, Vector point, double epsilon) {
        alphas = new ArrayList<>();
        MethodResult<Vector> result = new MethodResult<>();
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
            alphas.add(new AlphaPair(alpha, linearMinimization.iterations()));
            point = helpPoint.plus(gradient.multiply(-alpha));
            gradient = function.applyGradient(point);
        }
        result.setMinimal(point);
        return result;
    }

    public ArrayList<AlphaPair> getAlphas() {
        return alphas;
    }
}
