package structures.matrices;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ProfileMatrix extends FileReadableMatrix {
    private final double[] diagonal;
    private final List<Double> down;
    private final List<Double> up;
    private final int[] indexUp;
    private final int[] indexDown;
    private final int size;

    public ProfileMatrix(double[]... rows) {
        checkIsSquare(rows);
        size = rows.length;
        diagonal = IntStream.range(0, size).mapToDouble(i -> rows[i][i]).toArray();
        // Down triangle.
        down = new ArrayList<>();
        indexDown = new int[size + 1];
        fill(down, indexDown, false, rows);
        // Up triangle.
        up = new ArrayList<>();
        indexUp = new int[size + 1];
        fill(up, indexUp, true, rows);
    }

    public ProfileMatrix(Path file) throws IOException {
        this(readToDense(file));
    }

    private void fill(List<Double> triangle, int[] positions, boolean Up, double[]... values) {
        positions[0] = 1;
        for (int i = 0; i < size; i++) {
            int profile = i;
            int index = 0;
            while (index < i) {
                int x = Up ? index : i;
                int y = Up ? i : index;
                if (values[x][y] == 0) {
                    index++;
                    profile--;
                } else {
                    break;
                }
            }
            positions[i + 1] = positions[i] + profile;
            for (int j = index; j < i; j++) {
                int x = Up ? j : i;
                int y = Up ? i : j;
                triangle.add(values[x][y]);
            }
        }
    }

    @Override
    public ProfileMatrix copy() {
        throw new UnsupportedOperationException("ProfileMatrix does not support copy()");
    }

    @Override
    protected double getImpl(int i, int j) {
        if (i == j) {
            return diagonal[i];
        }
        if (j < i) {
            return realGet(i, j, indexDown, down);
        } else {
            return realGet(j, i, indexUp, up);
        }
    }

    private double realGet(int x, int y, int[] positions, List<Double> triangle) {
        int profile = positions[x + 1] - positions[x];
        if (x - profile > y) {
            return 0;
        } else {
            return triangle.get(positions[x] + y + profile - x - 1);
        }
    }

    @Override
    protected void setImpl(int i, int j, double value) {
        if (i == j) {
            diagonal[i] = value;
        }
        if (j < i) {
            realSet(i, j, indexDown, down, value);
        } else {
            realSet(j, i, indexUp, up, value);
        }
    }

    private void realSet(int x, int y, int[] positions, List<Double> triangle, double value) {
        int profile = positions[x + 1] - positions[x];
        if (x - profile > y) {
            throw new IllegalArgumentException("Out of range!");
        } else {
            triangle.set(positions[x] + y + profile - x - 1, value);
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
