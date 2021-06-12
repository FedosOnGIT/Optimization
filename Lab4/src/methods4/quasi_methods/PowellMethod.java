package methods4.quasi_methods;

import methods4.one_dim_methods.AbstractMinimizationMethod;
import methods4.one_dim_methods.MinimizationMethod;
import structures.matrices.Matrix;
import structures.matrices.Vector;

/**
 * @author Vladislav Gusev (vladislav.sg@yandex.ru)
 */
public class PowellMethod extends QuasiMethod {
    public PowellMethod(AbstractMinimizationMethod minimization) {
        super(minimization);
    }

    @Override
    protected Matrix nextGImpl(Matrix G, Vector deltaW, Vector deltaX) {
        Vector waveX = deltaX.add(G.multiply(deltaW));
        return G.subtract(waveX.multiply(waveX).multiply(1 / deltaW.scalar(waveX)));
    }
}
