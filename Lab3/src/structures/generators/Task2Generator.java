package structures.generators;

import java.util.Random;

public class Task2Generator extends AbstractGenerator {
    @Override
    protected double[][] generateMatrix(int n, double[][] matrix) {
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
        matrix[0][0] += 1;
        return matrix;
    }
}
