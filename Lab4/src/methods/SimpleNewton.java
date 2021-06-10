package methods;

import structures.Getian;
import structures.Gradient;
import structures.matrices.Matrix;
import structures.matrices.Vector;

import java.util.function.Function;

public class SimpleNewton implements Method {
    @Override
    public Vector min(final Function<Vector, Double> function,
                      final Getian getian,
                      final Gradient gradient,
                      Vector point,
                      final Double epsilon) {
        Vector next = new Vector(point.size());
        int index = 0;
        do {
            if (index != 0) {
                point = next.copy();
            }
            Vector antiGradientValue = (Vector) gradient.apply(point).multiply(-1);
            Matrix getianValue = getian.apply(point);
            Vector p = new ConjugateGradients().evaluate(getianValue, antiGradientValue);
            next = p.add(point);
            index++;
        } while (next.subtract(point).norm() > epsilon);
        return next;
    }
}
