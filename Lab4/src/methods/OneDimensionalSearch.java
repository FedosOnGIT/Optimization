package methods;

import minimizationMethods.MinimizationMethod;
import structures.Getian;
import structures.Gradient;
import structures.Type;
import structures.matrices.Matrix;
import structures.matrices.Vector;

import java.util.function.Function;

public class OneDimensionalSearch implements Method {
    private final MinimizationMethod minimization;

    public OneDimensionalSearch(final MinimizationMethod minimization) {
        this.minimization = minimization;
    }

    @Override
    public Vector minimal(Function<Vector, Double> function, Getian getian, Gradient gradient, Vector point, Type type, Double epsilon) {
        Vector next = new Vector(point.size());
        int index = 0;
        do {
            if (index != 0) {
                point = next.copy();
            }
            Vector antiGradientValue = (Vector) gradient.apply(point).multiply(-1);
            Matrix getianValue = getian.apply(point, type);
            Vector p = new ConjugateGradients().evaluate(getianValue, antiGradientValue);
            Vector finalPoint = point;
            Double alpha = minimization.minimal(l -> function.apply(finalPoint.add(p.multiply(l))), 0, 1, 0.0001);
            next = point.add(p.multiply(alpha));
            index++;
        } while (next.subtract(point).norm() > epsilon);
        return next;
    }
}
