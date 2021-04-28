package methods;

import structures.LinearMethodResult;

import java.util.function.Function;

public interface Method {
    LinearMethodResult minimum(Function<Double, Double> function, double start, double end, double epsilon);
}
