package structures.matrices;

import structures.FileReadable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class SparseMatrix extends FileReadableMatrix {
    private final double[] diagonal;
    private final List<Double> Triangle;
    private final int[] Indices;
    private final List<Integer> Positions;
    private final int size;

    public SparseMatrix(final double[]... values) {
        size = values.length;
        diagonal = IntStream.range(0, size).mapToDouble(i -> values[i][i]).toArray();
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

    public SparseMatrix(final List<Diagonal> diagonals) {
        size = diagonals.get(0).getVector().size();
        diagonal = IntStream.range(0, size).mapToDouble(i -> diagonals.get(0).getVector().get(0)).toArray();
        Indices = new int[size + 1];
        Triangle = new ArrayList<>();
        Positions = new ArrayList<>();
        Indices[0] = 0;
        Indices[1] = 0;
        int index = 1;
        for (int i = 1; i < size; i++) {
            if (diagonals.size() > index && diagonals.get(index).getNumber() == i) {
                index++;
            }
            int profile = 0;
            for (int j = index - 1; j > 0; j--) {
                int column = i - diagonals.get(j).getNumber();
                double element = diagonals.get(j).getVector().get(column);
                if (element != 0) {
                    Triangle.add(element);
                    Positions.add(column);
                    profile++;
                }
            }
            Indices[i + 1] = Indices[i] + profile;
        }
    }

    @Override
    public SparseMatrix copy() {
        return null;
    }

    private int getPosition(final int i, final int j) {
        int x = Math.max(i, j);
        int y = Math.min(i, j);
        int left = Indices[x];
        int right = Indices[x + 1];
        int index = Collections.binarySearch(Positions.subList(left, right), y);
        if (index > -1) {
            return left + index;
        } else {
            return -1;
        }
    }

    @Override
    protected double getImpl(final int i, final int j) {
        if (i == j) {
            return diagonal[i];
        }
        int position = getPosition(i, j);
        if (position > -1) {
            return Triangle.get(position);
        } else {
            return 0;
        }
    }

    @Override
    protected void setImpl(int i, int j, double value) {
        if (i == j) {
            diagonal[i] = value;
            return;
        }
        int position = getPosition(i, j);
        if (position > -1) {
            Triangle.set(position, value);
        } else {
            throw new IllegalArgumentException("Can't set on zero!");
        }
    }

    @Override
    public int rowsCount() {
        return size;
    }

    @Override
    public int columnsCount() {
        return size;
    }

    @Override
    public int size() {
        return size;
    }
}