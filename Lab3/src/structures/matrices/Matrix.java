package structures.matrices;

import structures.elements.Element;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Matrix<T extends Number> {
    public abstract int size();

    public abstract int rowsCount();

    public abstract int columnsCount();

    protected abstract Element<T> getImpl(int i, int j);

    protected abstract void setImpl(int i, int j, Element<T> element);

    private void checkCell(int i, int j) {
        assert i >= 0 && j >= 0 && i < rowsCount() && j < columnsCount();
    }

    public Element<T> get(int i, int j) {
        checkCell(i, j);
        return getImpl(i, j);
    }

    public void set(int i, int j, Element<T> element) {
        checkCell(i, j);
        setImpl(i, j, element);
    }

    private Vector<T> getVector(IntFunction<Element<T>> mapper) {
        return new Vector<>(IntStream.range(0, size()).mapToObj(mapper));
    }

    public Vector<T> getRow(int i) {
        return getVector(j -> get(i, j));
    }

    public Vector<T> getColumn(int j) {
        return getVector(i -> get(i, j));
    }

    private void setVector(Consumer<Integer> setter) {
        int size = size();
        for (int j = 0; j < size; j++) {
            setter.accept(j);
        }
    }

    public void setRow(int i, Vector<T> row) {
        setVector(j -> set(i, j, row.get(j)));
    }

    public void setColumn(int j, Vector<T> column) {
        setVector(i -> set(i, j, column.get(i)));
    }

    @Override
    public String toString() {
        return IntStream.range(0, size())
                .mapToObj(this::getRow)
                .map(Objects::toString)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    public static <T extends Number> Vector<T> mul(Matrix<T> matrix, Vector<T> vector) {
        assert matrix.columnsCount() == vector.size();
        return new Vector<>(IntStream.range(0, matrix.rowsCount()).mapToObj(i -> matrix.getRow(i).scalar(vector)));
    }

}
