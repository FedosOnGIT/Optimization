package quadraticMethods;

import structures.Matrix;
import structures.MethodResult;
import structures.QuadraticFunction;
import structures.Vector;

import java.util.ArrayList;

public class ConjugateGradients implements QuadraticMethod {

    @Override
    public MethodResult<Vector> minimum(final QuadraticFunction function, Vector point, final double epsilon) {
        MethodResult<Vector> result = new MethodResult<>();
        Vector gradient = function.applyGradient(point);
        double gradientLength = gradient.length();
        Vector slope = gradient.multiply(-1);
        Matrix A = function.getMatrix();
        while (gradientLength > epsilon) {
            result.add(point);
            Vector help = A.multiply(slope);
            double alpha = gradientLength * gradientLength / (help.multiply(slope));
            point.add(slope.multiply(alpha));
            gradient.add(help.multiply(alpha));
            double newGradientLength = gradient.length();
            double beta = newGradientLength * newGradientLength / (gradientLength * gradientLength);
            slope = slope.multiply(beta).plus(gradient.multiply(-1));
            gradientLength = newGradientLength;
        }
        result.setMinimal(point);
        return result;
    }
}
