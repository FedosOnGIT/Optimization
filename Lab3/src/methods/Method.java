package methods;

import structures.matrices.Matrix;
import structures.matrices.Vector;

public abstract class Method {
    private final static double EPS = 1e-14;

    protected long iterations = 0;

    public abstract Vector evaluate(Matrix matrix, Vector vector);

    protected boolean isZero(double value) {
        return Math.abs(value) < EPS;
    }

    public long getIterations() {
        return iterations;
    }
}
