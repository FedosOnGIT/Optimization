package methods4.newton_methods;

import methods.Gauss;
import methods4.AbstractMethod;
import methods.ConjugateGradients;
import structures4.Hessian;
import structures4.Gradient;
import structures.matrices.Vector;

import java.util.function.Function;

public class ClassicNewton extends AbstractMethod {
    @Override
    public Vector minImpl(final Function<Vector, Double> function,
                      final Hessian hessian,
                      final Gradient gradient,
                      final Vector point,
                      final Double epsilon) {
        ConjugateGradients conjugateGradients = new ConjugateGradients();
        Vector step;
        do {
            Vector p = conjugateGradients.evaluate(hessian.apply(point), gradient.apply(point).multiply(-1));
            step = doStep(function, hessian, gradient, point, p, epsilon);
            point.addThis(step);
            recordData(point, function);
        } while (step.norm() > epsilon);
        return point;
    }

    protected Vector doStep(final Function<Vector, Double> function,
                            final Hessian hessian,
                            final Gradient gradient,
                            final Vector point,
                            final Vector newtonDirection,
                            final Double epsilon) {
        return newtonDirection;
    }
}
