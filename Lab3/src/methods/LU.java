package methods;

import launcher.Statistics;
import structures.matrices.Matrix;
import structures.matrices.ProfileMatrix;
import structures.matrices.Vector;

public class LU extends Method {
    private final Statistics stat = new Statistics();
    @Override
    public Vector evaluate(final Matrix matrix, final Vector vector) {
        stat.reset();
        if (matrix instanceof ProfileMatrix) {
            return ((ProfileMatrix) matrix).LU(stat).solve(vector, stat);
        }
        throw new IllegalArgumentException("Not profile matrix!");
    }

    @Override
    public long getIterations() {
        return stat.getIterations();
    }
}
