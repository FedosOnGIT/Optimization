package structures;

import java.util.Arrays;
import java.util.stream.IntStream;

public class DiagonalMatrix extends Matrix{
    private final int size;
    private final double[] matrix;
    private final double minEigenvalue;
    private final double maxEigenvalue;

    public DiagonalMatrix(int size) {
        if (size <= 0) {
            throw new SizeException("Size must be more than zero!");
        }
        this.size = size;
        matrix = new double[size];
        IntStream.range(0, size).forEach(i -> matrix[i] = 1);
        minEigenvalue = 1;
        maxEigenvalue = 1;
    }

    public DiagonalMatrix(double[] matrix) {
        this.size = matrix.length;
        this.matrix = matrix;
        minEigenvalue = Arrays.stream(matrix).min().orElseThrow();
        maxEigenvalue = Arrays.stream(matrix).max().orElseThrow();
    }

    public Vector multiply(Vector vector) {
        if (size != vector.size()) {
            throw new SizeException("Vector and matrix size are not equal!");
        }
        Vector answer = new Vector(size);
        for (int i = 0; i < size; i++) {
            answer.setCoordinate(i, vector.getCoordinate(i) * matrix[i]);
        }
        return answer;
    }

    @Override
    public double getMinEigenvalue() {
        return minEigenvalue;
    }

    @Override
    public double getMaxEigenvalue() {
        return maxEigenvalue;
    }
}
