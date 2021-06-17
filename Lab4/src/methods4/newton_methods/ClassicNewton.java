package methods4.newton_methods;

import methods.ConjugateGradients;
import methods4.AbstractMethod;
import structures.matrices.Vector;
import structures4.Gradient;
import structures4.Hessian;

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
            ++iterations;
            Vector p = conjugateGradients.evaluate(hessian.apply(point), gradient.apply(point).multiply(-1));
            step = doStep(function, hessian, gradient, point, p, epsilon);
            point.addThis(step);
            recordData(point, function);
        } while (step.norm() > epsilon && iterations <= MAX_ITERATIONS_CNT);
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
