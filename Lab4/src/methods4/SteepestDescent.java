package methods4;

import methods4.one_dim_methods.AbstractMinimizationMethod;
import methods4.one_dim_methods.MinimizationMethod;
import structures.matrices.Vector;
import structures4.Gradient;
import structures4.Hessian;

import java.util.List;
import java.util.function.Function;

public class SteepestDescent extends AbstractMethod {
    protected AbstractMinimizationMethod minimization;
    protected double alpha = Double.NaN;

    public SteepestDescent(AbstractMinimizationMethod minimization) {
        this.minimization = minimization;
    }

    @Override
    protected List<String> getHeaders(int size) {
        List<String> headers = super.getHeaders(size);
        headers.add("alpha");
        return headers;
    }

    @Override
    protected Vector minImpl(Function<Vector, Double> function, Hessian hessian, Gradient gradient, Vector point, Double epsilon) {
        Vector gradientAtPoint;
        while (iterations <= MAX_ITERATIONS_CNT && (gradientAtPoint = gradient.apply(point)).norm() > epsilon) {
            final Vector finalGradient = gradientAtPoint;
            alpha = minimization.min(x -> function.apply(point.subtract(finalGradient.multiply(x))), epsilon);
            rec.set("alpha", alpha);
            point.subtractThis(gradientAtPoint.multiply(alpha));
            recordData(point, function);
            ++iterations;
        }
        return point;
    }
}
