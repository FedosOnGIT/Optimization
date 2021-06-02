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
        iterations += size * 7L;
        // Это z. z_0 = r_0.
        Vector helper = difference.copy();
        // ||f||
        double length = vector.norm();
        iterations += size;
        // Пока ||r_k|| / ||f|| > epsilon.
        while (difference.norm() / length > 1e-8) {
            // A * z_k-1.
            Vector multi = matrix.multiply(helper);
            iterations += size * 7L;
            // (r_k-1, r_k-1)
            double scalar = difference.scalar(difference);
            iterations += size;
            // alpha_k формула в пдф.
            double alpha = scalar / multi.scalar(helper);
            iterations += size + 1;
            // x_k = x_k-1 + alpha_k * z_k-1
            point = point.add(helper.multiply(alpha));
            iterations += size;
            // r_k = r_k - alpha_k * A * z_k-1
            difference = difference.add(multi.multiply(-alpha));
            iterations += size;
            // beta_k формула в пдф
            double beta = difference.scalar(difference) / scalar;
            iterations += size + 1;
            // z_k = r_k + beta_k * z_k-1
            helper = difference.add(helper.multiply(beta));
            iterations += size;
        }
        return point;
    }
}
