package structures.generators;

import structures.matrices.Matrix;
import structures.matrices.Vector;

public interface Generator<T extends Number> {
    Matrix<T> generateMatrix(int n);

    Vector<T> generateVector(int n);

    Vector<T> generateExactSolution(int n);
}
