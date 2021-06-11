package methods4.quasi_methods;

import methods4.AbstractMethod;
import methods4.one_dim_methods.MinimizationMethod;
import structures4.Hessian;
import structures4.Gradient;
import structures.matrices.Diagonal;
import structures.matrices.Matrix;
import structures.matrices.SparseMatrix;
import structures.matrices.Vector;

import java.util.Collections;
import java.util.function.Function;
import java.util.stream.IntStream;

public abstract class QuasiMethod extends AbstractMethod {
    private final MinimizationMethod minimization;

    public QuasiMethod(MinimizationMethod minimization) {
        this.minimization = minimization;
    }

    @Override
    public Vector minImpl(Function<Vector, Double> function,
                          Hessian hessian,
                          Gradient gradient,
                          Vector point,
                          Double epsilon) {
        Vector prevX, curX = point, deltaX = null;
        Vector prevW = new Vector(point.size()), curW, deltaW;
        Matrix G = null;
        long iteration = 0;
        do {
            prevX = curX;
            curW = gradient.apply(prevX).multiply(-1);
            deltaW = curW.subtract(prevW);
            G = nextG(G, deltaW, deltaX, iteration, point.size());
            final Vector p = G.multiply(curW);
            final Vector finalPrevX = prevX;
            double alpha = minimization.min(x -> function.apply(finalPrevX.add(p.multiply(x))), 0, 15, epsilon);
            curX = prevX.add(p.multiply(alpha));
            recordData(curX, function);
            deltaX = curX.subtract(prevX);
            ++iteration;
        } while (deltaX.norm() > epsilon);
        return curX;
    }

    protected Matrix nextG(Matrix G, Vector deltaW, Vector deltaX, long iteration, int size) {
        if (iteration == 0) {
            return ONE(size);
        }
        return nextGImpl(G, deltaW, deltaX, iteration);
    }

    protected abstract Matrix nextGImpl(Matrix G, Vector deltaW, Vector deltaX, long iteration);

    protected static Matrix ONE(final int size) {
        return new SparseMatrix(Collections.singletonList(
                new Diagonal(0, new Vector(IntStream.range(0, size).mapToDouble(i -> 1)))
        ));
    }
}
