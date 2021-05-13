package structures.generators;

import structures.matrices.Matrix;
import structures.matrices.Vector;

public abstract class Generator<T> {
    protected Matrix<T> matrix;
    protected Vector<T> vector, exactSolution;

    protected abstract void generateImpl(int n);

    public void generate(int n) {
        assert n > 0;
        generateImpl(n);
    }

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
