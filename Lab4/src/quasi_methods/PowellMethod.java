package quasi_methods;

import one_dim_methods.MinimizationMethod;
import structures.matrices.DenseMatrix;
import structures.matrices.Matrix;
import structures.matrices.Vector;

/**
 * @author Vladislav Gusev (vladislav.sg@yandex.ru)
 */
public class PowellMethod extends QuasiMethod {
    public PowellMethod(MinimizationMethod minimization) {
        super(minimization);
    }

    @Override
    protected Matrix nextG(Matrix G, Vector deltaW, Vector deltaX) {
        Vector waveX = deltaX.add(G.multiply(deltaW));
        double[][] tmp = new double[waveX.size()][waveX.size()];
        for (int i = 0; i < waveX.size(); ++i) {
            for (int j = 0; j < waveX.size(); ++j) {
                tmp[i][j] = waveX.get(i) * waveX.get(j);
            }
        }
        return G.subtract(new DenseMatrix(tmp).multiply(1/deltaW.scalar(waveX)));
    }
}
