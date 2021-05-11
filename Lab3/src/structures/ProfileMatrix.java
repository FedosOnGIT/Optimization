package structures;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ProfileMatrix implements Matrix {
    private final double[] diagonal;
    private final List<Double> down;
    //private final List<Double> up;
    //private final double[] indexUp;
    private final double[] indexDown;
    private final int size;

    ProfileMatrix(final double[] ...values) {
        this.size = values.length;
        for (double[] array : values) {
            if (array.length != size) {
                throw new IllegalArgumentException("Not a Matrix!");
            }
        }
        diagonal = IntStream.range(0, size).mapToDouble(i -> values[i][i]).toArray();
        down = new ArrayList<>();
        indexDown = new double[size + 1];
        for (int i = 0; i < size; i++) {

        }
    }
    @Override
    public double get(int row, int column) {
        return 0;
    }

    @Override
    public Vector getRow(int index) {
        return null;
    }

    @Override
    public Vector getColumn(int index) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
