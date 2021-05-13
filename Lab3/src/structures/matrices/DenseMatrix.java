package structures.matrices;

import structures.elements.Element;

public class DenseMatrix<T> extends Matrix<T> {
    final int rows, columns;
    final Vector<T> vector;

    public DenseMatrix(int rows, int columns, Vector<T> vector) {
        assert rows > 0 && columns > 0 && rows * columns == vector.size();
        this.rows = rows;
        this.columns = columns;
        this.vector = vector;
    }

    public DenseMatrix(int rows, int columns, Element<T> value) {
        this(rows, columns, new Vector<T>(rows * columns, value));
    }

    @Override
    public int size() {
        return vector.size();
    }

    @Override
    public int rowsCount() {
        return rows;
    }

    @Override
    public int columnsCount() {
        return columns;
    }

    @Override
    protected Element<T> getImpl(int i, int j) {
        return vector.get(i * columns + j);
    }

    @Override
    protected void setImpl(int i, int j, Element<T> element) {
        vector.set(i * columns + j, element);
    }

}
