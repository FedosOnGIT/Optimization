package structures.matrices;

import java.util.Arrays;
import java.util.stream.IntStream;

class SwappableMatrix implements Matrix {
    private final Matrix original;
    private final int[] permutation;

    public SwappableMatrix(Matrix original) {
        this.original = original;
        permutation = IntStream.range(0, original.size()).toArray();
    }

    @Override
    public double get(int i, int j) {
        return original.get(permutation[i], j);
    }

    @Override
    public void set(int i, int j, double value) {
        original.set(permutation[i], j, value);
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
