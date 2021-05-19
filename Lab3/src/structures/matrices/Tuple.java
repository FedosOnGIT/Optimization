package structures.matrices;

import java.util.function.Function;
import java.util.stream.IntStream;

public abstract class Tuple {
    public abstract Tuple copy();

    protected abstract double getImpl(int index);

    protected abstract void setImpl(int index, double value);

    public abstract int size();

    private void checkIndex(int index) {
        assert index >= 0 && index < size();
    }

    public double get(int index) {
        checkIndex(index);
        return getImpl(index);
    }

    public void set(int index, double value) {
        checkIndex(index);
        setImpl(index, value);
    }

    public void swap(int i, int j) {
        double tmp = get(i);
        set(i, get(j));
        set(j, tmp);
    }

    private Tuple apply(Function<Integer, Double> operationByIndex) {
        int size = size();
        for (int i = 0; i < size; i++) {
            set(i, operationByIndex.apply(i));
        }
        return this;
    }

    public Tuple add(Tuple other) {
        assert size() == other.size();
        return apply(i -> get(i) + other.get(i));
    }

    public Tuple subtract(Tuple other) {
        assert size() == other.size();
        return apply(i -> get(i) - other.get(i));
    }

    public Tuple multiply(double alpha) {
        return apply(i -> get(i) * alpha);
    }

    public double norm() {
        return Math.sqrt(IntStream.range(0, size()).mapToObj(i -> get(i) * get(i)).reduce(0d, Double::sum));
    }

    public Tuple normalize() {
        double norm = norm();
        return apply(i -> get(i) / norm);
    }

    public double scalar(Tuple other) {
        assert size() == other.size();
        return IntStream.range(0, size()).mapToObj(i -> get(i) * other.get(i)).reduce(0d, Double::sum);
    }
}
