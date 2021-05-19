package structures.matrices;

import java.util.stream.IntStream;

public class LUMatrix {
    private final Matrix L;
    private final Matrix U;
    private final int size;
    private long iterations;

    public LUMatrix(final Matrix main) {
        size = main.size();
        double[][] LMatrix = IntStream.range(0, size).mapToObj(i -> new double[size]).toArray(double[][]::new);
        double[][] UMatrix = IntStream.range(0, size).mapToObj(i -> new double[size]).toArray(double[][]::new);
        iterations = 0;
        count(LMatrix, UMatrix, main);
        // Left.
        L = new ProfileMatrix(LMatrix);
        // Up.
        U = new ProfileMatrix(UMatrix);
    }

    private void count(final double[][] LMatrix, final double[][] UMatrix, final Matrix main) {
        LMatrix[0][0] = main.get(0, 0);
        UMatrix[0][0] = 1;
        for (int i = 1; i < size; i++) {
            if (LMatrix[i - 1][i - 1] == 0) {
                throw new IllegalArgumentException("Minor is zero!");
            }
            for (int j = 0; j < i; j++) {
                LMatrix[i][j] = main.get(i, j);
                for (int k = 0; k < j; k++) {
                    LMatrix[i][j] -= LMatrix[i][k] * UMatrix[k][j];
                    ++iterations;
                }
                UMatrix[j][i] = main.get(j, i);
                for (int k = 0; k < j; k++) {
                    UMatrix[j][i] -= LMatrix[j][k] * UMatrix[k][i];
                    ++iterations;
                }
                UMatrix[j][i] /= LMatrix[j][j];
                ++iterations;
            }
            LMatrix[i][i] = main.get(i, i);
            for (int j = 0; j < i; j++) {
                LMatrix[i][i] -= LMatrix[i][j] * UMatrix[j][i];
                ++iterations;
            }
            UMatrix[i][i] = 1;
        }
    }

    private Vector straight(final Vector result) {
        assert result.size() == size;
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                if (L.get(j, i) != 0) {
                    double coefficient = L.get(j, i) / L.get(i, i);
                    ++iterations;
                    result.set(j, result.get(j) - result.get(i) * coefficient);
                    ++iterations;
                }
            }
        }
        return new Vector(IntStream
                .range(0, size)
                .mapToDouble(i ->
                {
                    ++iterations;
                    return result.get(i) / L.get(i, i);
                }));
    }

    private Vector reverse(final Vector result) {
        assert result.size() == size;
        for (int i = size - 1; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if (U.get(j, i) != 0) {
                    result.set(j, result.get(j) - result.get(i) * U.get(j, i));
                    ++iterations;
                }
            }
        }
        return new Vector(IntStream.range(0, size).mapToDouble(result::get));
    }

    public Vector solve(final Vector result) {
        return reverse(straight(result));
    }

    public long getIterations() {
        return iterations;
    }
}
