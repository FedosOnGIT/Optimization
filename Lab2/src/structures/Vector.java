package structures;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Vector {
    /**
     * An array of {@link Double} vector's coo
     */
    private final double[] coordinates;
    private final int size;

    public Vector(final double[] coordinates) {
        this.size = coordinates.length;
        this.coordinates = new double[size];
        IntStream.range(0, size).forEach(i -> this.coordinates[i] = coordinates[i]);
    }

    public Vector(final int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size is less than one!");
        }
        this.size = size;
        coordinates = new double[size];
        Arrays.fill(coordinates, 0);
    }

    public int size() {
        return size;
    }

    public double getCoordinate(final int index) {
        if (index >= size) {
            throw new IllegalArgumentException("Out of vector size");
        }
        return coordinates[index];
    }

    public void setCoordinate(final int index, final double value) {
        if (index >= size) {
            throw new IllegalArgumentException("Out of vector size");
        }
        coordinates[index] = value;
    }

    public void add(Vector other) {
        if (other.size != size) {
            throw new IllegalArgumentException("Vectors have different size");
        }
        IntStream.range(0, size).forEach(i -> coordinates[i] += other.getCoordinate(i));
    }

    public void extension(double coefficient) {
        IntStream.range(0, size).forEach(i -> coordinates[i] *= coefficient);
    }

    public Vector plus(Vector other) {
        assert other.size() == size;
        Vector result = new Vector(size);
        result.add(this);
        result.add(other);
        return result;
    }

    public Vector multiply(double coefficient) {
        Vector result = new Vector(size);
        result.add(this);
        result.extension(coefficient);
        return result;
    }

    public Double multiply(Vector other) {
        assert other.size() == size;
        double answer = 0;
        for (int i = 0; i < size; i++) {
            answer += other.getCoordinate(i) * getCoordinate(i);
        }
        return answer;
    }

    public double length() {
        double answer = 0;
        for (int i = 0; i < size; i++) {
            answer += coordinates[i] * coordinates[i];
        }
        return Math.sqrt(answer);
    }

    public void normalise() {
        double length = length();
        if (length == 0) {
            throw new IllegalArgumentException("Can't normalize, because vector length is 0!");
        }
        for (int i = 0; i < size; i++) {
            coordinates[i] /= length;
        }
    }

    public String print() {
        StringBuilder vector = new StringBuilder();
        for (int i = 0; i < size; i++) {
            vector.append(coordinates[i]);
            vector.append(" ");
        }
        return vector.toString();
    }
}
