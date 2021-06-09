package methods;

import structures.Getian;
import structures.Gradient;
import structures.Type;
import structures.matrices.Vector;

import java.util.function.Function;

public interface Method {
    Vector minimal(final Function<Vector, Double> function,
                   final Getian getian,
                   final Gradient gradient,
                   Vector point,
                   final Type type,
                   final Double epsilon);
}
