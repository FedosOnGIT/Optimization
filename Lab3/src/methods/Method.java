package methods;

import structures.elements.Element;
import structures.matrices.Matrix;
import structures.matrices.SwappableMatrix;
import structures.matrices.Vector;

public abstract class Method {
    public abstract <T> Vector<T> evaluate(Matrix<T> matrix, Vector<T> vector);
}
