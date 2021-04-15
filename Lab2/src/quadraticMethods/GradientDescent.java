package quadraticMethods;


import structures.MethodResult;
import structures.QuadraticFunction;
import structures.Vector;

import java.util.ArrayList;

public class GradientDescent implements QuadraticMethod {

    @Override
    public MethodResult<Vector> minimum(final QuadraticFunction function, Vector point, final double epsilon) {
        MethodResult<Vector> result = new MethodResult<>();
        double alpha = 2/(function.minEigenValue() + function.maxEigenValue());
        Vector gradient = function.applyGradient(point);
        while (gradient.length() > epsilon) {
            result.add(point);
            Vector newPoint = point.plus(gradient.multiply(-alpha));
            if (function.apply(newPoint) < function.apply(point)) {
                point = newPoint;
                gradient = function.applyGradient(point);
            } else {
                alpha /= 2;
            }
        }
        result.setMinimal(point);
        return result;
    }
}
