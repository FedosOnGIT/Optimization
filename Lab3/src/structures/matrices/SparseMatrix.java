package structures.matrices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class SparseMatrix extends Matrix {
    private final double[] Diagonal;
    private final List<Double> Triangle;
    private final int[] Indices;
    private final List<Integer> Positions;
    private final int size;

    public SparseMatrix(final double[]... values) {
        size = values.length;
        Diagonal = IntStream.range(0, size).mapToDouble(i -> values[i][i]).toArray();
        Triangle = new ArrayList<>();
        Positions = new ArrayList<>();
        Indices = new int[size + 1];
        Indices[0] = 0;
        for (int i = 0; i < size; i++) {
            int profile = 0;
            for (int j = 0; j < i; j++) {
                if (values[i][j] != 0) {
                    profile++;
                    Triangle.add(values[i][j]);
                    Positions.add(j);
                }
            }
            Indices[i + 1] = Indices[i] + profile;
        }
    }

    @Override
    public SparseMatrix copy() {
        return null;
    }

    @Override
    protected double getImpl(final int i, final int j) {
        if (i == j) {
            return Diagonal[i];
        }
        int x = Math.max(i, j);
        int y = Math.min(i, j);
        int left = Indices[x];
        int right = Indices[x + 1];
        int index = Collections.binarySearch(Positions.subList(left, right), y);
        if (index > -1) {
            return Triangle.get(left + index);
        } else {
            return 0;
        }
    }

    @Override
    protected void setImpl(int i, int j, double value) {

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
    public int size() {
        return 0;
    }
}