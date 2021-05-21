package structures.generators;

import structures.matrices.Diagonal;
import structures.matrices.Vector;

import java.util.*;

public class Task2Generator extends AbstractGenerator {
    private double value;
    private List<Diagonal> diagonals;
    private Vector mainDiag;

    public Task2Generator(int n) {
        super(n);
        value = 1;
    }

    @Override
    protected List<Diagonal> generateDiagonals() {
        if (diagonals == null) {
            double[][] matrix = new double[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == j) {
                        continue;
                    }
                    matrix[i][j] = -random.nextInt(5);
                }
            }
            for (int i = 0; i < n; i++) {
                double sum = 0;
                for (int j = 0; j < n; j++) {
                    if (j == i) {
                        continue;
                    }
                    sum += matrix[i][j];
                }
                matrix[i][i] = -sum;
            }
            diagonals = toDiagonals(matrix);
            for (Diagonal d : diagonals) {
                if (d.getNumber() == 0) {
                    mainDiag = d.getVector();
                    break;
                }
            }
        }
        mainDiag.set(0, mainDiag.get(0) + value);
        value = (-value + 0.1 * value);
        return diagonals;
    }
}
