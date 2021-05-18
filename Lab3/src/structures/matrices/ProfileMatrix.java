package structures.matrices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class ProfileMatrix extends Matrix {
    private final double[] diagonal;
    private final List<Double> down;
    private final List<Double> up;
    private final int[] indexUp;
    private final int[] indexDown;
    private final int size;

    public ProfileMatrix(final double[]... values) {
        assert values.length > 0;
        this.size = values.length;
        for (double[] array : values) {
            if (array.length != size) {
                throw new IllegalArgumentException("Not a Matrix!");
            }
        }
        diagonal = IntStream.range(0, size).mapToDouble(i -> values[i][i]).toArray();
        // Down triangle.
        down = new ArrayList<>();
        indexDown = new int[size + 1];
        fill(down, indexDown, size, false, values);
        // Up triangle.
        up = new ArrayList<>();
        indexUp = new int[size + 1];
        fill(up, indexUp, size,true, values);
    }

    public static void fill(
            final List<Double> triangle,
            final int[] positions,
            final int size,
            final boolean up,
            final double[]... values) {
        positions[0] = 1;
        for (int i = 0; i < size; i++) {
            int profile = i;
            int index = 0;
            while (index < i) {
                int x = up ? index : i;
                int y = up ? i : index;
                if (values[x][y] == 0) {
                    index++;
                    profile--;
                } else {
                    break;
                }
            }
            positions[i + 1] = positions[i] + profile;
            for (int j = index; j < i; j++) {
                int x = up ? j : i;
                int y = up ? i : j;
                triangle.add(values[x][y]);
            }
        }
    }

    @Override
    public ProfileMatrix copy() {
        return null;
    }

    @Override
    protected double getImpl(int i, int j) {
        if (i == j) {
            return diagonal[i];
        }
        if (j < i) {
            int profile = indexDown[i + 1] - indexDown[i];
            if (i - profile > j) {
                return 0;
            } else {
                return down.get(indexDown[i] + j + profile - i);
            }
        } else {
            int profile = indexUp[j + 1] - indexUp[j];
            if (j - profile > i) {
                return 0;
            } else {
                return up.get(indexUp[j] + i + profile - j);
            }
        }
    }

    @Override
    protected void setImpl(final int i, final int j, final double value) {
        if (i == j) {
            diagonal[i] = value;
        }
        if (j < i) {
            int profile = indexDown[i + 1] - indexDown[i];
            if (i - profile > j) {
                throw new IllegalArgumentException("Out of range!");
            } else {
                down.set(indexDown[i] + j + profile - i, value);
            }
        } else {
            int profile = indexUp[j + 1] - indexUp[j];
            if (j - profile > i) {
                throw new IllegalArgumentException("Out of range!");
            } else {
                up.set(indexUp[j] + i + profile - j, value);
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
    public int size() {
        return size;
    }
}
