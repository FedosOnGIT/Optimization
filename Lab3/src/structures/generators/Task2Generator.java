package structures.generators;

import structures.matrices.Diagonal;
import structures.matrices.ProfileMatrix;
import structures.matrices.Vector;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Task2Generator extends AbstractGenerator {
    private double value;
    private List<Diagonal> m;

    public Task2Generator(int n) {
        super(n);
        value = 1;
    }

    @Override
    protected List<Diagonal> generateMatrix() {
        if (m == null) {
            double[][] matrix = new double[n][n];
            Random randomizer = new Random();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = -randomizer.nextInt(5);
                }
            }
            for (int i = 0; i < n; i++) {
                double sum = 0;
                for (int j = 0; j < n; j++) {
                    if (j == i) continue;
                    sum += matrix[i][j];
                }
                matrix[i][i] = -sum;
            }
            m = toDiag(matrix);
        }
        Vector mainDiag = m.get(n - 1).getVector();
        mainDiag.set(0, mainDiag.get(0) + value);
        value = (-value + 0.1 * value);

        return m;
    }

}
