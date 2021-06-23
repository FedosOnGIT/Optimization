package structures.matrices;

import structures.FileReadable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Vector extends Tuple implements FileReadable {
    private final Double[] values;

    public Vector(Double... values) {
        this.values = values;
    }

    public Vector(int size) {
        values = new Double[size];
        Arrays.fill(values, 0.);
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
                    .map(FileReadableMatrix::clearElement)
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
        return Arrays.stream(values)
                .map(Objects::toString)
                .collect(Collectors.joining("; ", "(", ")"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
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

    public Matrix multiply(Vector other) {
        double[][] matrix = new double[size()][other.size()];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < other.size(); j++) {
                matrix[i][j] = get(i) * other.get(j);
            }
        }
        return new DenseMatrix(matrix);
    }

    @Override
    public Vector multiplyThis(double alpha) {
        return (Vector) super.multiplyThis(alpha);
    }

    @Override
    public Vector addThis(Tuple other) {
        return (Vector) super.addThis(other);
    }

    @Override
    public Vector subtractThis(Tuple other) {
        return (Vector) super.subtractThis(other);
    }

    @Override
    public Vector subtract(Tuple other) {
        return (Vector) super.subtract(other);
    }

    @Override
    public Vector multiply(double alpha) {
        return (Vector) super.multiply(alpha);
    }
}
