package structures;

import java.util.stream.IntStream;

public class Vector {
    private final double[] values;
    private final int size;

    public Vector(final double ... values) {
        this.values = values;
        size = values.length;
    }

    private Vector(final int size) {
        values = IntStream.range(0, size).mapToDouble(i -> 0).toArray();
        this.size = size;
    }

    public double get(final int index) {
        assert size > index && index >= 0;
        return values[index];
    }

    public void set(final int index, final double element) {
        assert size > index && index >= 0;
        values[index] = element;
    }

    public static Vector plus(Vector first, Vector second) {
        assert first.size == second.size;
        return new Vector(IntStream.range(0, first.size).mapToDouble(i -> first.values[i] + second.values[i]).toArray());
    }

    public static Vector multiply(Vector vector, double alpha) {
        return new Vector(IntStream.range(0, vector.size).mapToDouble(i -> vector.values[i] * alpha).toArray());
    }

    public double length() {
        return Math.sqrt(IntStream.range(0, size).map(i -> (int) (values[i] * values[i])).sum());
    }

    public void normalize() {
        double length = length();
        IntStream.range(0, size).forEach(i -> values[i] /= length);
    }

    public int size() {
        return size;
    }
}
