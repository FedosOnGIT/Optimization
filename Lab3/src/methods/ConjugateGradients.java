package methods;

import structures.matrices.Matrix;
import structures.matrices.Vector;

public class ConjugateGradients extends Method {
    @Override
    public Vector evaluate(Matrix matrix, Vector vector) {
        int size = vector.size();
        // Это x. x_0 = 0.
        Vector point = new Vector(size);
        // Это r. r_0 = f - A*x_0.
        Vector difference = vector.copy();
        // Это z. z_0 = r_0.
        Vector helper = difference.copy();
        // ||f||
        double length = vector.norm();
        // Пока ||r_k|| / ||f|| > epsilon.
        iterations = 0L;
        while (difference.norm() / length > 1e-8) {
            // A * z_k-1.
            Vector multi = matrix.multiply(helper);
            // (r_k-1, r_k-1)
            double scalar = difference.scalar(difference);
            // alpha_k формула в пдф.
            double alpha = scalar / multi.scalar(helper);
            // x_k = x_k-1 + alpha_k * z_k-1
            point = point.add(helper.multiply(alpha));
            // r_k = r_k - alpha_k * A * z_k-1
            difference = difference.add(multi.multiply(-alpha));
            // beta_k формула в пдф
            double beta = difference.scalar(difference) / scalar;
            // z_k = r_k + beta_k * z_k-1
            helper = difference.add(helper.multiply(beta));
            ++iterations;
        }
        return point;
    }
}
