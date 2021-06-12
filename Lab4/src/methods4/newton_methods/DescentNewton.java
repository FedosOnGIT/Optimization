package methods4.newton_methods;

import methods4.one_dim_methods.AbstractMinimizationMethod;
import methods4.one_dim_methods.MinimizationMethod;
import structures4.Hessian;
import structures4.Gradient;
import structures.matrices.Vector;

import java.util.List;
import java.util.function.Function;

public class DescentNewton extends ClassicNewton {
    protected AbstractMinimizationMethod minimization;
    protected double alpha = Double.NaN;

    public DescentNewton(AbstractMinimizationMethod minimization) {
        this.minimization = minimization;
    }

    protected Vector doStep(final Function<Vector, Double> function,
                            final Hessian hessian,
                            final Gradient gradient,
                            final Vector point,
                            final Vector newtonDirection,
                            final Double epsilon) {
        alpha = minimization.min(x ->
                function.apply(
                    newtonDirection.multiply(x).addThis(point)),
                    epsilon / newtonDirection.norm() / 2);
        return newtonDirection.multiplyThis(alpha);
    }

    @Override
    protected List<String> getHeaders(int size) {
        List<String> headers = super.getHeaders(size);
        headers.add("alpha");
        return headers;
    }

    @Override
    protected void recordData(Vector point, Function<Vector, Double> function) {
        super.recordData(point, function);
        rec.set("alpha", alpha);
    }
}
