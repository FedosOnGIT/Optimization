package structures;

import java.util.Arrays;

public class DiagonalMatrix extends AbstractMatrix{
    private final double[] elements;

    public DiagonalMatrix(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be more than zero!");
        }
        this.size = size;
        elements = new double[size];
        Arrays.fill(elements, 1);
        minEigenvalue = 1;
        maxEigenvalue = 1;
    }

    public DiagonalMatrix(double[] matrix) {
        this.size = matrix.length;
        this.elements = matrix;
        minEigenvalue = Arrays.stream(matrix).min().orElseThrow();
        maxEigenvalue = Arrays.stream(matrix).max().orElseThrow();
    }

    @Override
    public Vector multiply(Vector vector) {
        if (size != vector.size()) {
            throw new IllegalArgumentException("Vector and matrix size are not equal!");
        }
        Vector answer = new Vector(size);
        for (int i = 0; i < size; i++) {
            answer.setCoordinate(i, vector.getCoordinate(i) * elements[i]);
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
