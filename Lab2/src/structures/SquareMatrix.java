package structures;

import java.util.Arrays;

public class SquareMatrix extends AbstractMatrix{
    private final double[][] matrix;

    public SquareMatrix(final int size) {
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
        minEigenvalue = 1;
        maxEigenvalue = 1;
    }

    public SquareMatrix(final double[][] matrix, final double[] eigenvalues) {
        this.size = matrix.length;
        if (Arrays.stream(matrix).noneMatch(arr -> arr.length == size)) {
            throw new SizeException("Not a square matrix!");
        }
        this.matrix = Arrays.copyOf(matrix, size);
        if (eigenvalues.length != size) {
            throw new SizeException("Number of eigenvalues doesn't equals matrix size!");
        }
        minEigenvalue = Arrays.stream(eigenvalues).min().orElseThrow();
        maxEigenvalue = Arrays.stream(eigenvalues).max().orElseThrow();
    }

    @Override
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
}
