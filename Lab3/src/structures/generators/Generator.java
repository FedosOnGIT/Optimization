package structures.generators;

import structures.matrices.Matrix;
import structures.matrices.Vector;

public interface Generator {
    void generate(int n);

    void reset();

    Matrix getGeneratedMatrix();

    Vector getGeneratedVector();

    Vector getGeneratedExactSolution();
}
