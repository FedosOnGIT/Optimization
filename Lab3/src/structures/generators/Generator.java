package structures.generators;

import structures.matrices.Matrix;
import structures.matrices.Vector;

public interface Generator {
    Matrix generateMatrix(int n);

    Vector generateVector(int n);
}
