package quadraticMethods;

import structures.Matrix;
import structures.QuadraticFunction;
import structures.Vector;

public class ConjugateGradients implements QuadraticMethod {

    @Override
    public Vector minimum(final QuadraticFunction function, Vector point, final double epsilon) {
        Vector gradient = function.applyGradient(point);
        double gradientLength = gradient.length();
        Vector slope = gradient.multiply(-1);
        Matrix A = function.getMatrix();
        while (gradientLength > epsilon) {
            double alpha = gradientLength * gradientLength / (A.multiply(slope).multiply(slope));
            point.add(slope.multiply(alpha));
            gradient = function.applyGradient(point);
            double newGradientLength = gradient.length();
            double beta = newGradientLength * newGradientLength / (gradientLength * gradientLength);
            slope.multiply(beta).plus(gradient.multiply(-1));
            gradientLength = newGradientLength;
        }
        return point;
    }
}
