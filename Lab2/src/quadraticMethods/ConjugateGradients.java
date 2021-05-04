package quadraticMethods;

import structures.Matrix;
import structures.QuadraticFunction;
import structures.QuadraticMethodResult;
import structures.Vector;

import static methods.Method.MAX_ITER;

public class ConjugateGradients implements QuadraticMethod {

    @Override
    public QuadraticMethodResult minimum(final QuadraticFunction function, Vector point, final double epsilon) {
        QuadraticMethodResult result = new QuadraticMethodResult(function);
        Vector gradient = function.applyGradient(point);
        double gradientLength = gradient.length();
        Vector slope = gradient.multiply(-1);
        Matrix A = function.getMatrix();
        for (int i = 0; i < MAX_ITER && gradientLength > epsilon; ++i) {
            result.add(point);
            Vector help = A.multiply(slope);
            double alpha = gradientLength * gradientLength / (help.multiply(slope));
            point = point.plus(slope.multiply(alpha));
            gradient.add(help.multiply(alpha));
            double newGradientLength = gradient.length();
            double beta = newGradientLength * newGradientLength / (gradientLength * gradientLength);
            slope = slope.multiply(beta).plus(gradient.multiply(-1));
            gradientLength = newGradientLength;
        }
        result.add(point);
        return result;
    }
}
