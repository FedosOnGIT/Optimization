package methods;

import structures.matrices.Matrix;
import structures.matrices.Vector;

public class ConjugateGradients extends Method {
    @Override
    public Vector evaluate(Matrix matrix, Vector vector) {
        int size = vector.size();
        Vector point = new Vector(size);
        Vector difference = (Vector) vector.plus(matrix.multiply(point).extension(-1));
        iterations += (long) size * size;
        Vector helper = difference.copy();
        double length = vector.norm();
        iterations += size;
        while (difference.norm() / length > 1e-7) {
            Vector multi = matrix.multiply(helper);
            iterations += (long) size * size;
            double scalar = difference.scalar(difference);
            iterations += size;
            double alpha = scalar / multi.scalar(helper);
            iterations += size + 1;
            point = (Vector) point.plus(helper.extension(alpha));
            iterations += size;
            difference = (Vector) difference.plus(multi.extension(-alpha));
            iterations += size;
            double beta = difference.scalar(difference) / scalar;
            iterations += size + 1;
            helper = (Vector) difference.plus(helper.extension(beta));
            iterations += size;
        }
        return point;
    }
}
