package methods4;

import structures.matrices.Vector;
import structures4.Gradient;
import structures4.Hessian;
import structures4.Recorder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Vladislav Gusev (vladislav.sg@yandex.ru)
 */
public abstract class AbstractMethod implements Method {
    public final static long MAX_ITERATIONS_CNT = 100_000;
    protected Recorder rec;
    protected long iterations;

    @Override
    public Vector min(Function<Vector, Double> function, Hessian hessian, Gradient gradient, Vector point, Double epsilon) {
        rec = new Recorder(getHeaders(point.size()));
        iterations = 0;
        recordData(point, function);
        return minImpl(function, hessian, gradient, point.copy(), epsilon);
    }

    protected void recordData(Vector point, Function<Vector, Double> function) {
        rec.newIter();
        for (int i = 0; i < point.size(); i++) {
            rec.set(i, point.get(i));
        }
        rec.set("f", function.apply(point));
    }

    protected List<String> getHeaders(int size) {
        List<String> headers = IntStream.rangeClosed(1, size)
                .mapToObj(i -> "x" + i)
                .collect(Collectors.toList());
        headers.add("f");
        return headers;
    }

    public Recorder getRecorder() {
        return rec;
    }

    protected abstract Vector minImpl(Function<Vector, Double> function, Hessian getian, Gradient gradient, Vector point, Double epsilon);
}
