package structures.matrices;

import structures.FileReadable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Vector extends Tuple implements FileReadable {
    private final Double[] values;

    public Vector(Double... values) {
        this.values = values;
    }

    public Vector(int size) {
        this(new Double[size]);
    }

    public Vector(Stream<Double> values) {
        this(values.toArray(Double[]::new));
    }

    public Vector(DoubleStream values) {
        this(values.boxed());
    }

    public Vector(List<Double> values) {
        this(values.stream());
    }

    public Vector(Path source) throws IOException {
        try (var reader = Files.newBufferedReader(source)) {
            values = Arrays.stream(reader.readLine().split(" "))
                    .map(FileReadable::clearElement)
                    .map(Double::parseDouble)
                    .toArray(Double[]::new);
        }
    }

    @Override
    protected double getImpl(final int index) {
        return values[index];
    }

    @Override
    protected void setImpl(final int index, final double value) {
        values[index] = value;
    }

    @Override
    public int size() {
        return values.length;
    }

    @Override
    public Vector copy() {
        return new Vector(Arrays.copyOf(values, values.length));
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Arrays.equals(values, vector.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }

    @Override
    public Vector add(Tuple other) {
        return (Vector) super.add(other);
    }
}
