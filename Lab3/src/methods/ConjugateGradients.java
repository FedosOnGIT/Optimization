package methods;

import statistics.ConjugateGradientsStatistics;
import statistics.Statistics;
import structures.matrices.Matrix;
import structures.matrices.Vector;

public class ConjugateGradients extends Method {
    public ConjugateGradients() {
        super(new ConjugateGradientsStatistics());
    }

    @Override
    public Vector evaluate(Matrix matrix, Vector vector) {
        return null;
    }

    @Override
    public ConjugateGradientsStatistics getStat() {
        return ((ConjugateGradientsStatistics) super.getStat());
    }
}
