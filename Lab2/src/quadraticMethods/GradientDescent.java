package quadraticMethods;


import structures.QuadraticFunction;
import structures.Vector;

public class GradientDescent implements QuadraticMethod {
    @Override
    public Vector minimum(final QuadraticFunction function, Vector point, final double epsilon) {
        double alpha = 2/(function.minEigenValue() + function.maxEigenValue());
        Vector gradient = function.applyGradient(point);
        while (gradient.length() > epsilon) {
            Vector newPoint = point.plus(gradient.multiply(-alpha));
            if (function.apply(newPoint) < function.apply(point)) {
                point = newPoint;
                gradient = function.applyGradient(point);
            } else {
                alpha /= 2;
            }
        }
        return point;
    }
}
