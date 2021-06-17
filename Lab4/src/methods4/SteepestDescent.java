package methods4;

import methods4.one_dim_methods.AbstractMinimizationMethod;
import structures.matrices.Vector;
import structures4.Gradient;
import structures4.Hessian;

import java.util.List;
import java.util.function.Function;

public class SteepestDescent extends AbstractMethod {
    protected AbstractMinimizationMethod minimization;

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
        Vector antiGradient;
        while (iterations <= MAX_ITERATIONS_CNT &&
                (antiGradient = gradient.apply(point).multiplyThis(-1)).norm() > epsilon) {
            double alpha = minimization.min(function, point, antiGradient, 0, 15, epsilon);
            rec.set("alpha", alpha);
            point.addThis(antiGradient.multiply(alpha));
            recordData(point, function);
            ++iterations;
        }
        return point;
    }
}
