package methods.quasi_methods;

import methods.one_dim_methods.MinimizationMethod;
import structures.matrices.Matrix;
import structures.matrices.Vector;

/**
 * @author Vladislav Gusev (vladislav.sg@yandex.ru)
 */
public class DfpMethod extends QuasiMethod {
    public DfpMethod(MinimizationMethod minimization) {
        super(minimization);
    }

    @Override
    protected Matrix nextG(final Matrix G, final Vector deltaW, final Vector deltaX) {
        Vector v = G.multiply(deltaW);
        return G.
                subtract(deltaX.multiply(deltaX).
                        multiply(1 / deltaW.scalar(deltaX))).
                subtract(v.multiply(v).
                        multiply(1 / v.scalar(deltaW)));
    }
}
