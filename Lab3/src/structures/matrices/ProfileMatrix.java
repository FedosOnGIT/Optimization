package structures.matrices;

import structures.FileReadable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class ProfileMatrix extends FileReadableMatrix {
    private final double[] Diagonal;
    private final List<Double> Down;
    private final List<Double> Up;
    private final int[] IndexUp;
    private final int[] IndexDown;
    private final int size;

    public ProfileMatrix(final double[]... values) {
        assert values.length > 0;
        this.size = values.length;
        for (double[] array : values) {
            if (array.length != size) {
                throw new IllegalArgumentException("Not a Matrix!");
            }
        }
        Diagonal = IntStream.range(0, size).mapToDouble(i -> values[i][i]).toArray();
        // Down triangle.
        Down = new ArrayList<>();
        IndexDown = new int[size + 1];
        fill(Down, IndexDown, false, values);
        // Up triangle.
        Up = new ArrayList<>();
        IndexUp = new int[size + 1];
        fill(Up, IndexUp, true, values);
    }

    public ProfileMatrix(Path file) throws IOException {
        this(readToDense(file));
    }

    public void fill(
            final List<Double> triangle,
            final int[] positions,
            final boolean Up,
            final double[]... values) {
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
        return null;
    }

    @Override
    protected double getImpl(int i, int j) {
        if (i == j) {
            return Diagonal[i];
        }
        if (j < i) {
            return realGet(i, j, IndexDown, Down);
        } else {
            return realGet(j, i, IndexUp, Up);
        }
    }

    private double realGet(final int x, final int y, final int[] positions, final List<Double> triangle) {
        int profile = positions[x + 1] - positions[x];
        if (x - profile > y) {
            return 0;
        } else {
            return triangle.get(positions[x] + y + profile - x - 1);
        }
    }

    @Override
    protected void setImpl(final int i, final int j, final double value) {
        if (i == j) {
            Diagonal[i] = value;
        }
        if (j < i) {
            realSet(i, j, IndexDown, Down, value);
        } else {
            realSet(j, i, IndexUp, Up, value);
        }
    }

    private void realSet(
            final int x,
            final int y,
            final int[] positions,
            final List<Double> triangle,
            final double value) {
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
