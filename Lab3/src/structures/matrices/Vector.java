package structures.matrices;

import java.util.Arrays;
import java.util.stream.Stream;

public class Vector extends Tuple {
    private final double[] values;

    public Vector(double... values) {
        assert values.length > 0;
        this.values = values;
    }

    public Vector(int size) {
        this(new double[size]);
    }

    public Vector(Double... values) {
        this.values = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            this.values[i] = values[i];
        }
    }

    public Vector(Stream<Double> values) {
        this(values.toArray(Double[]::new));
    }

    @Override
    public Vector copy() {
        return new Vector(Arrays.copyOf(values, values.length));
    }

    @Override
    protected double getImpl(int index) {
        return values[index];
    }

    @Override
    protected void setImpl(int index, double value) {
        values[index] = value;
    }

    @Override
    public int size() {
        return values.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }
}
