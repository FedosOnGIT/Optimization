package methods;

import minimizationMethods.MinimizationMethod;
import structures.Getian;
import structures.Gradient;
import structures.Type;
import structures.matrices.Matrix;
import structures.matrices.Vector;

import java.util.function.Function;

public class DescentDirection implements Method {
    private final MinimizationMethod minimization;

    public DescentDirection(final MinimizationMethod minimization) {
        this.minimization = minimization;
    }

    @Override
    public Vector minimal(Function<Vector, Double> function, Getian getian, Gradient gradient, Vector point, Type type, Double epsilon) {
        Vector next = new Vector(point.size());
        int index = 0;
        do {
            if (index != 0) {
                point = next;
            }
            Vector antiGradientValue = (Vector) gradient.apply(point).multiply(-1);
            Matrix getianValue = getian.apply(point, type);
            Vector p = new ConjugateGradients().evaluate(getianValue, antiGradientValue);
            if (p.scalar(antiGradientValue) <= 0) {
                p = antiGradientValue.copy();
            }
            Vector finalPoint = point;
            Vector finalP = p;
            double alpha = minimization.minimal(l -> function.apply(finalPoint.add(finalP.multiply(l))), 0, 1, 0.0001);
            next = point.add(p.multiply(alpha));
            index++;
        } while (next.subtract(point).norm() > epsilon);
        return next;
    }
}
