package methods.quasi_methods;

import methods.one_dim_methods.MinimizationMethod;
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
        return G.subtract(waveX.multiply(waveX).multiply(1/deltaW.scalar(waveX)));
    }
}
