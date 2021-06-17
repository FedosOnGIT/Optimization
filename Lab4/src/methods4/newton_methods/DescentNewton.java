package methods4.newton_methods;

import methods4.one_dim_methods.AbstractMinimizationMethod;
import structures.matrices.Vector;
import structures4.Gradient;
import structures4.Hessian;

import java.util.List;
import java.util.function.Function;

public class DescentNewton extends ClassicNewton {
    protected AbstractMinimizationMethod minimization;

    public DescentNewton(AbstractMinimizationMethod minimization) {
        this.minimization = minimization;
    }

    protected Vector doStep(final Function<Vector, Double> function,
                            final Hessian hessian,
                            final Gradient gradient,
                            final Vector point,
                            final Vector newtonDirection,
                            final Double epsilon) {
        double alpha = minimization.min(function, point, newtonDirection, epsilon);
        rec.set("alpha", alpha);
        return newtonDirection.multiplyThis(alpha);
    }

    @Override
    protected List<String> getHeaders(int size) {
        List<String> headers = super.getHeaders(size);
        headers.add("alpha");
        return headers;
    }
}
