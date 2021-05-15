package structures.generators;

public class Task5_3Generator extends Task5_2Generator {
    @Override
    protected double[][] generateMatrix(int n, double[][] matrix) {
        matrix = super.generateMatrix(n, matrix);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                matrix[i][j] = -matrix[i][j];
            }
        }
        return matrix;
    }
}
