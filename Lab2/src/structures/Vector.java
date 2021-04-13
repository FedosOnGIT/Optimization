package structures;

import java.util.List;
import java.util.stream.IntStream;

public class Vector {
    private final double[] coordinates;
    private final int size;

    public Vector(final List<Double> coordinates) {
        this.size = coordinates.size();
        this.coordinates = new double[size];
        IntStream.range(0, coordinates.size()).forEach(i -> this.coordinates[i] = coordinates.get(i));
    }

    private Vector(final int size) {
        this.size = size;
        coordinates = new double[size];
        IntStream.range(0, size).forEach(i -> coordinates[i] = 0);
    }

    public int size() {
        return size;
    }

    public double getCoordinate(final int index) {
        assert index < size;
        return coordinates[index];
    }

    public void setCoordinate(final int index, final double value) {
        assert index < size;
        coordinates[index] = value;
    }

    public void add(Vector other) {
        assert other.size() == size;
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

    public double length() {
        double answer = 0;
        for (int i = 0; i < size; i++) {
            answer += coordinates[i] * coordinates[i];
        }
        return Math.sqrt(answer);
    }

    public void normalise() {
        double length = length();
        for (int i = 0; i < size; i++) {
            coordinates[i] /= length;
        }
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.print(coordinates[i]);
            System.out.print(" ");
        }
    }
}
