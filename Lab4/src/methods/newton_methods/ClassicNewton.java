package methods.newton_methods;

import methods.ConjugateGradients;
import structures.Hessian;
import structures.Gradient;
import structures.matrices.Matrix;
import structures.matrices.Vector;

import java.util.function.Function;

public class ClassicNewton implements FunctionMethod {
    @Override
    public Vector min(final Function<Vector, Double> function,
                      final Hessian getian,
                      final Gradient gradient,
                      final Vector point,
                      final Double epsilon) {
        int index = 0;
        Vector step;
        do {
            Vector antiGradientValue = gradient.apply(point).multiply(-1);
            Matrix getianValue = getian.apply(point);
            Vector p = new ConjugateGradients().evaluate(getianValue, antiGradientValue);
            step = doStep(function, getian, gradient, point, p, epsilon);
            point.addThis(step);
            index++;
        } while (step.norm() > epsilon);
        return point;
    }

    protected Vector doStep(final Function<Vector, Double> function,
                            final Hessian getian,
                            final Gradient gradient,
                            final Vector point,
                            final Vector newtonDirection,
                            final Double epsilon) {
        return newtonDirection;
    }
}
