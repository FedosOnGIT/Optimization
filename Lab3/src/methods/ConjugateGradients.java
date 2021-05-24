package methods;

import structures.matrices.Matrix;
import structures.matrices.Vector;

public class ConjugateGradients extends Method {
    @Override
    public Vector evaluate(Matrix matrix, Vector vector) {
        int size = vector.size();
        Vector point = new Vector(size);
        Vector difference = vector.add(matrix.multiply(point).multiply(-1));
        iterations += size * 7L;
        Vector helper = difference.copy();
        double length = vector.norm();
        iterations += size;
        while (difference.norm() / length > 1e-8) {
            Vector multi = matrix.multiply(helper);
            iterations += size * 7L;
            double scalar = difference.scalar(difference);
            iterations += size;
            double alpha = scalar / multi.scalar(helper);
            iterations += size + 1;
            point = point.add(helper.multiply(alpha));
            iterations += size;
            difference = difference.add(multi.multiply(-alpha));
            iterations += size;
            double beta = difference.scalar(difference) / scalar;
            iterations += size + 1;
            helper = difference.add(helper.multiply(beta));
            iterations += size;
        }
        return point;
    }
}
