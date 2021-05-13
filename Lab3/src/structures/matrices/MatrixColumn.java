package structures.matrices;

import structures.elements.Element;

public class MatrixColumn<T> extends MatrixVector<T> {
    public MatrixColumn(Matrix<T> matrix, int index) {
        super(matrix, index);
    }

    @Override
    protected Element<T> getImpl(int index) {
        return matrix.get(index, this.index);
    }

    @Override
    protected void setImpl(int index, Element<T> element) {
        matrix.setImpl(index, this.index, element);
    }

    @Override
    public int size() {
        return matrix.rowsCount();
    }

}
