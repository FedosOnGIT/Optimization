package quadraticMethods;


import structures.QuadraticFunction;
import structures.QuadraticMethodResult;
import structures.Vector;

import static methods.Method.MAX_ITER;

public class GradientDescent implements QuadraticMethod {

    @Override
    public QuadraticMethodResult minimum(final QuadraticFunction function, Vector point, final double epsilon) {
        QuadraticMethodResult result = new QuadraticMethodResult(function);
        double alpha = 2/(function.minEigenValue() + function.maxEigenValue());
        Vector gradient = function.applyGradient(point);
        for (int i = 0; i < MAX_ITER && gradient.length() > epsilon; ++i) {
            result.add(point);
            Vector newPoint = point.plus(gradient.multiply(-alpha));
            if (function.apply(newPoint) < function.apply(point)) {
                point = newPoint;
                gradient = function.applyGradient(point);
            } else {
                alpha /= 2;
            }
        }
        result.add(point);
        return result;
    }
}
