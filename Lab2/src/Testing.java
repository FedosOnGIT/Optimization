import methods.*;
import quadraticMethods.*;
import structures.*;

import java.util.ArrayList;
import java.util.List;

public class Testing {
    public static void main(String[] args) throws NotConvexFunctionException {
        QuadraticMethod method = new ConjugateGradients();
        QuadraticFunction function = new QuadraticFunction(new DiagonalMatrix(new double[]{2, 2}), new Vector(new double[]{1, 0}), 0);
        method.minimum(function, new Vector(new double[]{1, 3}), 0.00001).print();
    }
}
