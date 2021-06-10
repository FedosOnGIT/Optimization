package methods;

import minimizationMethods.MinimizationMethod;
import structures.Hessian;
import structures.Gradient;
import structures.matrices.Vector;

import java.util.function.Function;

public class DescentNewton extends ClassicNewton {
    protected final MinimizationMethod minimization;

    public DescentNewton(final MinimizationMethod minimization) {
        this.minimization = minimization;
    }

    protected Vector doStep(final Function<Vector, Double> function,
                            final Hessian getian,
                            final Gradient gradient,
                            final Vector point,
                            final Vector newtonDirection,
                            final Double epsilon) {
        return newtonDirection.multiplyThis(
                minimization.min(
                        l -> function.apply(newtonDirection.multiply(l).addThis(point)),
                        0,
                        1, // TODO check bounds (maybe -10:10)
                        epsilon));
    }
}
