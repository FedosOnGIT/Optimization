package structures.matrices;

import structures.elements.Element;

public class MatrixRow<T> extends MatrixVector<T> {
    public MatrixRow(Matrix<T> matrix, int index) {
        super(matrix, index);
    }

    @Override
    protected Element<T> getImpl(int index) {
        return matrix.get(this.index, index);
    }

    @Override
    protected void setImpl(int index, Element<T> element) {
        matrix.setImpl(this.index, index, element);
    }

    @Override
    public int size() {
        return matrix.columnsCount();
    }

}
