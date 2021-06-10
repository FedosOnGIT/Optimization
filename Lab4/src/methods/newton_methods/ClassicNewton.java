package methods.newton_methods;

import methods.AbstractMethod;
import methods.ConjugateGradients;
import methods.Method;
import structures.Hessian;
import structures.Gradient;
import structures.Recorder;
import structures.matrices.Matrix;
import structures.matrices.Vector;

import java.util.function.Function;

public class ClassicNewton extends AbstractMethod {
    @Override
    public Vector min(final Function<Vector, Double> function,
                      final Hessian hessian,
                      final Gradient gradient,
                      final Vector point,
                      final Double epsilon) {
        initRecorder(point.size());
        writeVector(point);
        Vector step;
        do {
            Vector antiGradientValue = gradient.apply(point).multiply(-1);
            Matrix hessianValue = hessian.apply(point);
            Vector p = new ConjugateGradients().evaluate(hessianValue, antiGradientValue);
            step = doStep(function, hessian, gradient, point, p, epsilon);
            point.addThis(step);
            writeVector(point);
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
