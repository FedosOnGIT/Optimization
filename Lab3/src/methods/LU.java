package methods;

import structures.matrices.Matrix;
import structures.matrices.ProfileMatrix;
import structures.matrices.Vector;

public class LU extends Method {
    @Override
    public Vector evaluate(final Matrix matrix, final Vector vector) {
        if (matrix instanceof ProfileMatrix) {
            return ((ProfileMatrix) matrix).LU(iterations).solve(vector, iterations);
        }
        throw new IllegalArgumentException("Not profile matrix!");
    }
}
