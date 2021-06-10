package methods.quasi_methods;

import methods.newton_methods.FunctionMethod;
import methods.one_dim_methods.MinimizationMethod;
import structures.Hessian;
import structures.Gradient;
import structures.matrices.Diagonal;
import structures.matrices.Matrix;
import structures.matrices.SparseMatrix;
import structures.matrices.Vector;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

public abstract class QuasiMethod implements FunctionMethod {
    private final MinimizationMethod minimization;

    public QuasiMethod(final MinimizationMethod minimization) {
        this.minimization = minimization;
    }

    @Override
    public Vector min(Function<Vector, Double> function,
                      Hessian getian, Gradient gradient,
                      Vector point,
                      Double epsilon) {
        Double[] line = new Double[point.size()];
        Arrays.fill(line, 1.0);
        Matrix G = new SparseMatrix(Collections.singletonList(new Diagonal(0, new Vector(line))));
        Vector w = gradient.apply(point).multiply(-1);
        Vector p = w.copy();
        Vector finalPoint = point;
        Vector finalP = p;
        Double alpha = minimization.min(
                            l -> function.apply(finalPoint.add(finalP.multiply(l))),
                        0,
                        1,
                        epsilon/10);
        Vector next = point.add(p.multiply(alpha));
        Vector deltaX = next.subtract(point);
        while (deltaX.norm() > epsilon) {
            point = next.copy();
            Vector wHelp = gradient.apply(point).multiply(-1);
            Vector deltaW = w.subtract(wHelp);
            w = wHelp.copy();
            G = nextG(G, deltaW, deltaX);
            p = G.multiply(w);
            Vector finalPoint1 = point;
            Vector finalP1 = p;
            alpha = minimization.min(l -> function.apply(finalPoint1.add(finalP1.multiply(l))), 0, 1, epsilon/10);
            next = point.add(p.multiply(alpha));
            deltaX = next.subtract(point);
        }
        return next;
    }

    protected abstract Matrix nextG(final Matrix G, final Vector deltaW, final Vector deltaX);
}
