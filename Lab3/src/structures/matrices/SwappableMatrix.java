package structures.matrices;

import structures.elements.Element;

import java.util.Arrays;
import java.util.stream.IntStream;

class SwappableMatrix<T extends Number> extends Matrix<T> {
    private final Matrix<T> original;
    private final int[] permutation;

    public SwappableMatrix(Matrix<T> original) {
        this.original = original;
        permutation = IntStream.range(0, original.size()).toArray();
    }

    @Override
    protected Element<T> getImpl(int i, int j) {
        return original.get(permutation[i], j);
    }

    @Override
    protected void setImpl(int i, int j, Element<T> value) {
        original.set(permutation[i], j, value);
    }

    @Override
    public int rowsCount() {
        return original.rowsCount();
    }

    @Override
    public int columnsCount() {
        return original.columnsCount();
    }

    @Override
    public int size() {
        return original.size();
    }

    public void swapRows(int i, int j) {
        permutation[i] ^= permutation[j];
        permutation[j] ^= permutation[i];
        permutation[i] ^= permutation[j];
    }
}
