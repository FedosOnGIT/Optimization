package methods;

import structures.matrices.LUMatrix;
import structures.matrices.Matrix;
import structures.matrices.Vector;

public class LU extends Method {
    @Override
    public Vector evaluate(final Matrix matrix, final Vector vector) {
        return new LUMatrix(matrix).solve(vector);
    }
}
