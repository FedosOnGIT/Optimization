package structures.generators;

import structures.matrices.DenseMatrix;
import structures.matrices.Matrix;
import structures.matrices.Vector;

import java.util.stream.IntStream;

public abstract class AbstractGenerator implements Generator {
    private Matrix matrix;
    private Vector vector, exactSolution;

    protected abstract double[][] generateMatrix(int n, double[][] matrix);

    @Override
    public void generate(int n) {
        assert n > 0;
        matrix = new DenseMatrix(generateMatrix(n, new double[n][n]));
        exactSolution = new Vector(IntStream.range(1, n + 1).asDoubleStream().boxed());
        vector = Matrix.multiply(matrix, exactSolution);
    }

    @Override
    public void reset() {
        matrix = null;
        vector = null;
        exactSolution = null;
    }

    @Override
    public Matrix getGeneratedMatrix() {
        return matrix;
    }

    @Override
    public Vector getGeneratedVector() {
        return vector;
    }

    @Override
    public Vector getGeneratedExactSolution() {
        return exactSolution;
    }
}
