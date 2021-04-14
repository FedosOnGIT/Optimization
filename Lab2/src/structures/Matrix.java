package structures;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Matrix extends AbstractMatrix{
    private final double[][] matrix;
    private final double[] eigenvalues;


    public Matrix(final int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size is less than one!");
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
                throw new IllegalArgumentException("Not a matrix!");
            }
            final int index = i;
            IntStream.range(0, size).forEach(position -> this.matrix[index][position] = matrix[index][position]);
        }
        if (eigenvalues.length != size) {
            throw new IllegalArgumentException("Number of eigenvalues doesn't equals matrix size!");
        }
        this.eigenvalues = eigenvalues;
        minEigenvalue = Arrays.stream(eigenvalues).min().orElseThrow();
        maxEigenvalue = Arrays.stream(eigenvalues).max().orElseThrow();
    }

    @Override
    public Vector multiply(final Vector vector) {
        if (vector.size() != size) {
            throw new IllegalArgumentException("Vector size and matrix size are different!");
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
}
