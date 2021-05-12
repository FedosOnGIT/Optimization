package structures.matrices;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ProfileMatrix implements Matrix {
    private final double[] diagonal;
    private final List<Double> down;
    private final List<Double> up;
    private final int[] indexUp;
    private final int[] indexDown;
    private final int size;

    private void downProfile(double[]... values) {

    }

    ProfileMatrix(double[]... values) {
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

    private void fill(List<Double> triangle, int[] positions, boolean up, double[]... values) {
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
    public double get(int i, int j) {
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
    public void set(int i, int j, double value) {

    }

    @Override
    public int size() {
        return size;
    }
}
