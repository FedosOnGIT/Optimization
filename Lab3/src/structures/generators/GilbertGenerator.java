package structures.generators;

import structures.matrices.Diagonal;
import structures.matrices.ProfileMatrix;

import java.util.List;

public class GilbertGenerator extends AbstractGenerator {
    public GilbertGenerator(int n) {
        super(n);
    }

    @Override
    protected List<Diagonal> generateDiagonals() {
        double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = 1d / (i + j + 1);
            }
        }
        return toDiag(matrix);
    }
}
