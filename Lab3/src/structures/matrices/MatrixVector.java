package structures.matrices;

import structures.elements.Element;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class MatrixVector<T extends Number> extends Tuple<T> {
    protected final Matrix<T> matrix;
    protected final int index;

    MatrixVector(Matrix<T> matrix, int index) {
        this.matrix = matrix;
        this.index = index;
    }

    private void checkPos(int index) {
        assert index >= 0 && index < size();
    }

    protected abstract Element<T> getImpl(int index);

    protected abstract void setImpl(int index, Element<T> element);

    public Element<T> get(int index) {
        checkPos(index);
        return getImpl(index);
    }

    public void set(int index, Element<T> element) {
        checkPos(index);
        setImpl(index, element);
    }

    public abstract int size();

}
