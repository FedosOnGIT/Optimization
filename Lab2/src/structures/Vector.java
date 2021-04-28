package structures;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Vector {
    /**
     * An array of {@link Double} vector's coo
     */
    private final double[] coordinates;

    public Vector(final double[] coordinates) {
        this.coordinates = coordinates;
    }

    public Vector(final Vector other) {
        coordinates = Arrays.copyOf(other.coordinates, other.size());
    }

    public Vector(final int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size is less than one!");
        }
        coordinates = new double[size];
        Arrays.fill(coordinates, 0);
    }

    public int size() {
        return coordinates.length;
    }

    public double getCoordinate(final int index) {
        if (index >= size()) {
            throw new IllegalArgumentException("Out of vector size");
        }
        return coordinates[index];
    }

    public void setCoordinate(final int index, final double value) {
        if (index >= size()) {
            throw new IllegalArgumentException("Out of vector size");
        }
        coordinates[index] = value;
    }

    public void add(Vector other) {
        sizeAssert(other);
        for (int i = 0; i < size(); ++i) {
            coordinates[i] += other.getCoordinate(i);
        }
    }

    public void extension(double coefficient) {
        for (int i = 0; i < size(); ++i) {
            coordinates[i] *= coefficient;
        }
    }

    public Vector plus(Vector other) {
        sizeAssert(other);
        Vector result = new Vector(this);
        result.add(other);
        return result;
    }

    public Vector multiply(double coefficient) {
        Vector result = new Vector(size());
        result.add(this);
        result.extension(coefficient);
        return result;
    }

    public Double multiply(Vector other) {
        sizeAssert(other);
        double answer = 0;
        for (int i = 0; i < size(); i++) {
            answer += other.getCoordinate(i) * getCoordinate(i);
        }
        return answer;
    }

    public double length() {
        return Math.sqrt(
                Arrays
                    .stream(coordinates)
                    .map((x) -> x*x)
                    .sum()
        );
    }

    public Vector normalise() {
        double length = length();
        if (length != 0) {
            for (int i = 0; i < size(); i++) {
                coordinates[i] /= length;
            }
        }
        return this;
    }

    @Override
    public String toString() {
        return Arrays.stream(coordinates).mapToObj(Double::toString).collect(Collectors.joining(","));
    }

    private void sizeAssert(Vector other) {
        if (other.size() != size()) {
            throw new IllegalArgumentException("Vectors have different size");
        }
    }
}
