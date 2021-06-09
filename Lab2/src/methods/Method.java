package methods;

import structures.LinearMethodResult;

import java.util.function.Function;

public interface
Method {
    int MAX_ITER = 16000;
    LinearMethodResult minimum(Function<Double, Double> function, double start, double end, double epsilon);
}
