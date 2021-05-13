package structures.matrices;

import structures.elements.Element;

public class DenseMatrix<T extends Number> extends Matrix<T> {
    final int rows, columns;
    final Vector<T> values;

    public DenseMatrix(int rows, int columns, Vector<T> values) {
        assert rows > 0 && columns > 0 && rows * columns == values.size();
        this.rows = rows;
        this.columns = columns;
        this.values = values;
    }

    @Override
    public int size() {
        return values.size();
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
        return values.get(i * columns + j);
    }

    @Override
    protected void setImpl(int i, int j, Element<T> value) {
        values.set(i * columns + j, value);
    }

}
