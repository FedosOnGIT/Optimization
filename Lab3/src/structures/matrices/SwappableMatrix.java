package structures.matrices;

import java.util.Arrays;
import java.util.stream.IntStream;

public class SwappableMatrix extends Matrix {
    private final Matrix original;
    private final int[] permutation;

    public SwappableMatrix(Matrix original) {
        this.original = original;
        permutation = IntStream.range(0, original.size()).toArray();
    }

    private SwappableMatrix(Matrix original, int[] permutation) {
        this.original = original;
        this.permutation = permutation;
    }

    @Override
    public SwappableMatrix copy() {
        return new SwappableMatrix(original.copy(), Arrays.copyOf(permutation, permutation.length));
    }

    @Override
    protected double getImpl(int i, int j) {
        return original.get(permutation[i], j);
    }

    @Override
    protected void setImpl(int i, int j, double value) {
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

    public int[] getPermutation() {
        return permutation;
    }
}
