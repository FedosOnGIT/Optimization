package methods.newton_methods;

import structures.Hessian;
import structures.Gradient;
import structures.matrices.Vector;

import java.util.function.Function;

public interface FunctionMethod {
    Vector min(final Function<Vector, Double> function,
               final Hessian getian,
               final Gradient gradient,
               Vector point,
               final Double epsilon);
}
