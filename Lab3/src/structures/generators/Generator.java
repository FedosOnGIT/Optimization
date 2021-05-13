package structures.generators;

import structures.matrices.Matrix;
import structures.matrices.Vector;

public abstract class Generator<T extends Number> {
    protected Matrix<T> matrix;
    protected Vector<T> vector, exactSolution;

    public abstract void generate(int n);

    public void reset() {
        matrix = null;
        vector = null;
        exactSolution = null;
    }

    public Matrix<T> getGeneratedMatrix() {
        return matrix;
    }

    public Vector<T> getGeneratedVector() {
        return vector;
    }

    public Vector<T> getGeneratedExactSolution() {
        return exactSolution;
    }

}
