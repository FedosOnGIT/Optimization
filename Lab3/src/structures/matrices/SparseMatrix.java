package structures.matrices;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class SparseMatrix extends FileReadableMatrix {
    private double[] diagonal;
    private final List<Double> down;
    private final List<Double> up;
    private final int[] indicesUp;
    private final int[] indicesDown;
    private final List<Integer> positionsDown;
    private final List<Integer> positionsUp;
    private int size;

    public SparseMatrix(double[]... values) {
        size = values.length;
        diagonal = IntStream.range(0, size).mapToDouble(i -> values[i][i]).toArray();
        down = new ArrayList<>();
        up = new ArrayList<>();
        positionsDown = new ArrayList<>();
        positionsUp = new ArrayList<>();
        indicesDown = new int[size + 1];
        indicesUp = new int[size + 1];
        fillFull(down, positionsDown, indicesDown, false, values);
        fillFull(up, positionsUp, indicesUp, true, values);
    }

    void fillFull(List<Double> triangle, List<Integer> positions, int[] indices, boolean up, double[] ...values) {
        indices[0] = 0;
        for (int i = 0; i < size; i++) {
            int profile = 0;
            for (int j = 0; j < i; j++) {
                int x = up ? j : i;
                int y = up ? i : j;
                if (values[x][y] != 0) {
                    profile++;
                    triangle.add(values[x][y]);
                    positions.add(j);
                }
            }
            indices[i + 1] = indices[i] + profile;
        }
    }

    public SparseMatrix(List<Diagonal> diagonals) {
        List<Diagonal> upDiagonals = new ArrayList<>();
        List<Diagonal> downDiagonals = new ArrayList<>();
        for (Diagonal diagonal : diagonals) {
            if (diagonal.getNumber() > 0) {
                upDiagonals.add(diagonal);
            } else if (diagonal.getNumber() < 0) {
                downDiagonals.add(diagonal);
            } else {
                size = diagonal.getVector().size();
                this.diagonal = IntStream.range(0, size).mapToDouble(i -> diagonal.getVector().get(i)).toArray();
            }
        }
        upDiagonals.sort(Comparator.comparing(Diagonal::getNumber));
        downDiagonals.sort(Comparator.comparing(Diagonal::getNumber).reversed());
        indicesDown = new int[size + 1];
        down = new ArrayList<>();
        positionsDown = new ArrayList<>();
        indicesUp = new int[size + 1];
        up = new ArrayList<>();
        positionsUp = new ArrayList<>();
        fillDiagonal(down, positionsDown, indicesDown, false, downDiagonals);
        fillDiagonal(up, positionsUp, indicesUp, true, upDiagonals);
    }

    void fillDiagonal(List<Double> triangle, List<Integer> positions, int[] indices, boolean up, List<Diagonal> diagonals) {
        indices[0] = 0;
        indices[1] = 0;
        int index = 0;
        int multi = up? 1 : -1;
        for (int i = 0; i < size; i++) {
            if (diagonals.size() > index && diagonals.get(index).getNumber() == multi * i) {
                index++;
            }
            int profile = 0;
            for (int j = index - 1; j >= 0; j--) {
                int line = i - multi * diagonals.get(j).getNumber();
                double element = diagonals.get(j).getVector().get(line);
                if (element != 0) {
                    triangle.add(element);
                    positions.add(line);
                    profile++;
                }
            }
            indices[i + 1] = indices[i] + profile;
        }
    }

    public SparseMatrix(Path source) throws IOException {
        this(readToDiagonals(source));
    }

    @Override
    public SparseMatrix copy() {
        throw new UnsupportedOperationException("SparseMatrix does not support copy()");
    }

    private int getPosition(int x, int y, int[] indices, List<Integer> positions) {
        int left = indices[x];
        int right = indices[x + 1];
        int index = Collections.binarySearch(positions.subList(left, right), y);
        if (index > -1) {
            return left + index;
        } else {
            return -1;
        }
    }

    @Override
    protected double getImpl(int i, int j) {
        if (i == j) {
            return diagonal[i];
        }
        if (i > j) {
            int position = getPosition(i, j, indicesDown, positionsDown);
            if (position > -1) {
                return down.get(position);
            } else {
                return 0;
            }
        } else {
            int position = getPosition(j, i, indicesUp, positionsUp);
            if (position > -1) {
                return up.get(position);
            } else {
                return 0;
            }
        }
    }

    @Override
    protected void setImpl(int i, int j, double value) {
        if (i == j) {
            diagonal[i] = value;
            return;
        }
        if (i > j) {
            int position = getPosition(i, j, indicesDown, positionsDown);
            if (position > -1) {
                down.set(position, value);
            } else {
                throw new IllegalArgumentException("Can't set on zero!");
            }
        } else {
            int position = getPosition(j, i, indicesUp, positionsUp);
            if (position > -1) {
                up.set(position, value);
            } else {
                throw new IllegalArgumentException("Can't set on zero!");
            }
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
    public Vector multiply(Vector vector) {
        Vector result = new Vector(size);
        for (int i = 0; i < size; ++i) {
            double x = 0;
            for (int j = indicesDown[i]; j < indicesDown[i+1]; ++j) {
                x += down.get(j) * vector.get(positionsDown.get(j));
            }
            x += diagonal[i] * vector.get(i);
            result.set(i, x);
        }
        for (int i = 0; i < size; ++i) {
            for (int j = indicesUp[i]; j < indicesUp[i+1]; ++j) {
                double x = result.get(positionsUp.get(j));
                x += up.get(j) * vector.get(i);
                result.set(positionsUp.get(j), x);
            }
        }
        return result;
    }
}
