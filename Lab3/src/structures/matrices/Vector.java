package structures.matrices;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public class Vector {
    private double[] values;
    private int size;

    private void init(double... values) {
        this.values = values;
        size = values.length;
    }

    public Vector(double... values) {
        init(values);
    }

    public Vector(int size) {
        this(new double[size]);
    }

    public double get(int index) {
        assert size > index && index >= 0;
        return values[index];
    }

    public void set(int index, double value) {
        assert size > index && index >= 0;
        values[index] = value;
    }

    private Vector apply(IntFunction<Double> function) {
        for (int i = 0; i < size; i++) {
            values[i] = function.apply(i);
        }
        return this;
    }

    public Vector plus(Vector other) {
        if (size > other.size) {
            init(Arrays.copyOf(values, other.size));
        }
        return apply(i -> values[i] + other.values[i]);
    }

    public Vector multiply(double alpha) {
        return apply(i -> values[i] * alpha);
    }

    public double norm() {
        return Math.sqrt(IntStream.range(0, size).mapToDouble(i -> values[i] * values[i]).sum());
    }

    public Vector normalize() {
        double norm = norm();
        return apply(i -> values[i] / norm);
    }

    public int size() {
        return size;
    }
}
