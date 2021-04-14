package quadraticMethods;

import structures.MatrixMinimal;
import structures.QuadraticFunction;
import structures.Vector;

public class ConjugateGradients implements QuadraticMethod {

    @Override
    public Vector minimum(final QuadraticFunction function, Vector point, final double epsilon) {
        Vector gradient = function.applyGradient(point);
        double gradientLength = gradient.length();
        Vector slope = gradient.multiply(-1);
        MatrixMinimal A = function.getMatrix();
        while (gradientLength > epsilon) {
            Vector help = A.multiply(slope);
            double alpha = gradientLength * gradientLength / (help.multiply(slope));
            point.add(slope.multiply(alpha));
            gradient.add(help.multiply(alpha));
            double newGradientLength = gradient.length();
            double beta = newGradientLength * newGradientLength / (gradientLength * gradientLength);
            slope = slope.multiply(beta).plus(gradient.multiply(-1));
            gradientLength = newGradientLength;
        }
        return point;
    }
}
