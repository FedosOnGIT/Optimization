package structures.matrices;

import java.util.function.Function;
import java.util.stream.IntStream;

public abstract class Tuple {
    protected abstract double getImpl(int index);

    protected abstract void setImpl(int index, double value);

    public abstract Tuple copy();

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

    public Tuple addThis(Tuple other) {
        assert size() == other.size();
        return apply(i -> get(i) + other.get(i));
    }

    public Tuple add(Tuple other) {
        return copy().addThis(other);
    }

    public Tuple subtractThis(Tuple other) {
        assert size() == other.size();
        return apply(i -> get(i) - other.get(i));
    }

    public Tuple subtract(Tuple other) {
        return copy().subtractThis(other);
    }

    public Tuple multiplyThis(double alpha) {
        return apply(i -> get(i) * alpha);
    }

    public Tuple multiply(double alpha) {
        return copy().multiplyThis(alpha);
    }

    public double norm() {
        return Math.sqrt(scalar(this));
    }

    public Tuple normalizeThis() {
        return multiplyThis(1 / norm());
    }

    public Tuple normalize() {
        return copy().normalizeThis();
    }

    public double scalar(Tuple other) {
        assert size() == other.size();
        return IntStream.range(0, size()).mapToObj(i -> get(i) * other.get(i)).reduce(0d, Double::sum);
    }
}
