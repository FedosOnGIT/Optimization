package methods4.quasi_methods;

import methods4.AbstractMethod;
import methods4.one_dim_methods.AbstractMinimizationMethod;
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
    private final AbstractMinimizationMethod minimization;

    public QuasiMethod(AbstractMinimizationMethod minimization) {
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
        do {
            prevX = curX;
            curW = gradient.apply(prevX).multiply(-1);
            deltaW = curW.subtract(prevW);
            G = nextG(G, deltaW, deltaX, iterations, point.size());
            final Vector p = G.multiply(curW);
            final Vector finalPrevX = prevX;
            double alpha = minimization.min(x ->
                    function.apply(finalPrevX.add(p.multiply(x))),
                    epsilon / p.norm() / 2);
            curX = prevX.add(p.multiply(alpha));
            recordData(curX, function);
            deltaX = curX.subtract(prevX);
            ++iterations;
        } while (deltaX.norm() > epsilon && iterations <= MAX_ITERATIONS_CNT);
        return curX;
    }

    protected Matrix nextG(Matrix G, Vector deltaW, Vector deltaX, long iteration, int size) {
        if (iteration == 0 || iteration % G.rowsCount() == 0) {
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
