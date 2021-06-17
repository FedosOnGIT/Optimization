package methods4.quasi_methods;

import methods4.one_dim_methods.AbstractMinimizationMethod;
import structures.matrices.Matrix;
import structures.matrices.Vector;

/**
 * @author Vladislav Gusev (vladislav.sg@yandex.ru)
 */
public class DfpMethod extends QuasiMethod {
    public DfpMethod(AbstractMinimizationMethod minimization) {
        super(minimization);
    }

    @Override
    protected Matrix nextGImpl(Matrix G, Vector deltaW, Vector deltaX) {
        Vector v = G.multiply(deltaW);
        return G.subtract(deltaX.multiply(deltaX).multiply(1 / deltaW.scalar(deltaX)))
                .subtract(v.multiply(v).multiply(1 / v.scalar(deltaW)));
    }
}
