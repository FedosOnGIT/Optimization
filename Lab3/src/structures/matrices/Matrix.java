package structures.matrices;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Matrix {
    public abstract Matrix copy();

    protected abstract double getImpl(int i, int j);

    protected abstract void setImpl(int i, int j, double value);

    public abstract int rowsCount();

    public abstract int columnsCount();

    public abstract int size();

    private void checkCell(int i, int j) {
        assert i >= 0 && j >= 0 && i < rowsCount() && j < columnsCount();
    }

    public double get(int i, int j) {
        checkCell(i, j);
        return getImpl(i, j);
    }

    public void set(int i, int j, double value) {
        checkCell(i, j);
        setImpl(i, j, value);
    }

    private Vector getVector(IntFunction<Double> mapper) {
        return new Vector(IntStream.range(0, size()).mapToObj(mapper));
    }

    public Vector getRow(int i) {
        return getVector(j -> get(i, j));
    }

    public Vector getColumn(int j) {
        return getVector(i -> get(i, j));
    }

    private void setVector(Consumer<Integer> setter) {
        int size = size();
        for (int j = 0; j < size; j++) {
            setter.accept(j);
        }
    }

    public void setRow(int i, Vector row) {
        setVector(j -> set(i, j, row.get(j)));
    }

    public void setColumn(int j, Vector column) {
        setVector(i -> set(i, j, column.get(i)));
    }

    @Override
    public String toString() {
        return IntStream.range(0, size())
                .mapToObj(this::getRow)
                .map(Objects::toString)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    public Vector multiply(Vector vector) {
        assert columnsCount() == vector.size();
        return new Vector(IntStream.range(0, rowsCount()).mapToObj(i -> new MatrixRow(this, i).scalar(vector)));
    }
}
