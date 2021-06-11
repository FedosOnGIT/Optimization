package methods4;

import structures4.Hessian;
import structures4.Gradient;
import structures.matrices.Vector;

import java.util.function.Function;

public interface Method {
    Vector min(final Function<Vector, Double> function,
               final Hessian hessian,
               final Gradient gradient,
               Vector point,
               final Double epsilon);
}
