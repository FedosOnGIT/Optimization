package structures;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class Matrix {
    private final int size;
    private final double[][] matrix;
    private final double[] eigenvalues;
    private final double minEigenvalue;
    private final double maxEigenvalue;

    public Matrix() {
        this.size = 1;
        matrix = new double[1][1];
        matrix[0][0] = 1;
        eigenvalues = new double[1];
        eigenvalues[0] = 1;
        minEigenvalue = 1;
        maxEigenvalue = 1;
    }

    public Matrix(final int size) {
        if (size <= 0) {
            throw new SizeException("Size is less than one!");
        }
        this.size = size;
        matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = i == j ? 1 : 0;
            }
        }
        eigenvalues = new double[size];
        IntStream.range(0, size).forEach(i -> eigenvalues[i] = 1);
        minEigenvalue = 1;
        maxEigenvalue = 1;
    }

    public Matrix(final double[][] matrix, final double[] eigenvalues) {
        this.size = matrix.length;
        this.matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            if (matrix[i].length != size) {
                throw new SizeException("Not a matrix!");
            }
            final int index = i;
            IntStream.range(0, size).forEach(position -> this.matrix[index][position] = matrix[index][position]);
        }
        if (eigenvalues.length != size) {
            throw new SizeException("Number of eigenvalues doesn't equals matrix size!");
        }
        this.eigenvalues = eigenvalues;
        minEigenvalue = Arrays.stream(eigenvalues).min().orElseThrow();
        maxEigenvalue = Arrays.stream(eigenvalues).max().orElseThrow();
    }

    public Vector multiply(final Vector vector) {
        if (vector.size() != size) {
            throw new SizeException("Vector size and matrix size are different!");
        }
        Vector result = new Vector(size);
        for (int i = 0; i < size; i++) {
            double coordinate = 0;
            for (int j = 0; j < size; j++) {
                coordinate += vector.getCoordinate(j) * matrix[i][j];
            }
            result.setCoordinate(i, coordinate);
        }
        return result;
    }

    public int size() {
        return size;
    }

    public double getMinEigenvalue() {
        return minEigenvalue;
    }

    public double getMaxEigenvalue() {
        return maxEigenvalue;
    }
}
