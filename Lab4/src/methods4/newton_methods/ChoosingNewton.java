package methods4.newton_methods;

import methods4.one_dim_methods.AbstractMinimizationMethod;
import methods4.one_dim_methods.MinimizationMethod;
import structures4.Hessian;
import structures4.Gradient;
import structures.matrices.Vector;

import java.util.function.Function;

public class ChoosingNewton extends DescentNewton {

    public ChoosingNewton(final AbstractMinimizationMethod minimization) {
        super(minimization);
    }

    protected Vector doStep(final Function<Vector, Double> function,
                            final Hessian hessian,
                            final Gradient gradient,
                            final Vector point,
                            Vector newtonDirection,
                            final Double epsilon) {
        Vector gradientAtPoint = gradient.apply(point);
        if (newtonDirection.scalar(gradientAtPoint) >= 0) {
            newtonDirection = gradientAtPoint.multiplyThis(-1);
        }
        return super.doStep(function, hessian, gradient, point, newtonDirection, epsilon);
    }
}
