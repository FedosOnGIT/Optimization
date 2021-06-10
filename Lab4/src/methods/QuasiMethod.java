package methods;

import minimizationMethods.MinimizationMethod;
import structures.Getian;
import structures.Gradient;
import structures.matrices.Diagonal;
import structures.matrices.Matrix;
import structures.matrices.SparseMatrix;
import structures.matrices.Vector;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

public class QuasiMethod implements Method {
    private final MinimizationMethod minimization;

    public QuasiMethod(final MinimizationMethod minimization) {
        this.minimization = minimization;
    }

    @Override
    public Vector min(Function<Vector, Double> function, Getian getian, Gradient gradient, Vector point, Double epsilon) {
        Double[] line = new Double[point.size()];
        Arrays.fill(line, 1.0);
        Matrix G = new SparseMatrix(Collections.singletonList(new Diagonal(0, new Vector(line))));
        Vector w = (Vector) gradient.apply(point).multiply(-1);
        Vector p = w.copy();
        Vector finalPoint = point;
        Vector finalP = p;
        Double alpha = minimization.min(l -> function.apply(finalPoint.add(finalP.multiply(l))), 0, 1, 0.0001);
        Vector next = point.add(p.multiply(alpha));
        Vector deltaX = (Vector) next.subtract(point);
        while (deltaX.norm() > epsilon) {
            point = next.copy();
            Vector wHelp = (Vector) gradient.apply(point).multiply(-1);
            Vector deltaW = (Vector) w.subtract(wHelp);
            w = wHelp.copy();
            Vector v = G.multiply(deltaW);
            G = G.
                    subtract(deltaX.multiply(deltaX).
                            multiply(1 / deltaW.scalar(deltaX))).
                    subtract(v.multiply(v).
                            multiply(1 / v.scalar(deltaW)));
            p = G.multiply(w);
            Vector finalPoint1 = point;
            Vector finalP1 = p;
            alpha = minimization.min(l -> function.apply(finalPoint1.add(finalP1.multiply(l))), 0, 1, 0.0001);
            next = point.add(p.multiply(alpha));
            deltaX = (Vector) next.subtract(point);
        }
        return next;
    }
}
