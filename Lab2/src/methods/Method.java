package methods;

import structures.MethodResult;
import structures.QuadraticFunction;
import structures.Vector;

import java.util.function.Function;

public interface Method {
    MethodResult<Double> minimum(Function<Double, Double> function, double start, double end, double epsilon);
}
