package structures;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ProfileMatrix extends AbstractMatrix {
    private final double[] diagonal;
    private final List<Double> down;
    private final List<Double> up;
    private final int[] indexUp;
    private final int[] indexDown;
    private final int size;

    private void downProfile(final double[]... values) {

    }

    ProfileMatrix(final double[]... values) {
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
        fill(down, indexDown, true, values);
        // Up triangle.
        up = new ArrayList<>();
        indexUp = new int[size + 1];
        fill(up, indexUp, true, values);
    }

    private void fill(final List<Double> triangle, final int[] positions, final boolean up, final double[]... values) {
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
    public double get(final int row, final int column) {
        if (row == column) {
            return diagonal[row];
        }
        if (column < row) {
            int profile = indexDown[row + 1] - indexDown[row];
            if (row - profile > column) {
                return 0;
            } else {
                return down.get(indexDown[row] + column + profile - row);
            }
        } else {
            int profile = indexUp[column + 1] - indexUp[column];
            if (column - profile > row) {
                return 0;
            } else {
                return up.get(indexUp[column] + row + profile - column);
            }
        }
    }

    @Override
    public int size() {
        return size;
    }
}
