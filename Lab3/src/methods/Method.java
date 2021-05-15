package methods;

import statistics.Statistics;
import structures.matrices.Matrix;
import structures.matrices.Vector;

public abstract class Method {
    private final static double EPS = 1e-14;

    protected final Statistics stat;

    public Method(Statistics stat) {
        this.stat = stat;
    }

    public abstract Vector evaluate(Matrix matrix, Vector vector);

    protected boolean isZero(double value) {
        return Math.abs(value) < EPS;
    }

    public Statistics getStat() {
        return stat;
    }
}
