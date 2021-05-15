package structures.generators;

public class Task3GilbertGenerator extends AbstractGenerator {
    @Override
    protected double[][] generateMatrix(int n, double[][] matrix) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = 1d / (i + j + 1);
            }
        }
        return matrix;
    }
}
