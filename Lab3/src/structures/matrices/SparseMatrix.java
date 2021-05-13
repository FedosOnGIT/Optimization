package structures.matrices;

import structures.elements.Element;

public class SparseMatrix<T extends Number> extends Matrix<T> {
    @Override
    public int size() {
        return 0;
    }

    @Override
    public int rowsCount() {
        return 0;
    }

    @Override
    public int columnsCount() {
        return 0;
    }

    @Override
    protected Element<T> getImpl(int i, int j) {
        return null;
    }

    @Override
    protected void setImpl(int i, int j, Element<T> element) {

    }
}
