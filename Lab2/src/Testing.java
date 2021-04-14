import methods.*;
import quadraticMethods.*;
import structures.*;

import java.util.ArrayList;
import java.util.List;

public class Testing {
    public static void main(String[] args) throws NotConvexFunctionException {
        QuadraticMethod method = new ConjugateGradients();
        Matrix hardMatrix = new SquareMatrix(new double[][]{{1, 0.5, 0}, {0.5, 1, 0}, {0, 0, 20}}, new double[]{0.5, 1.5, 20});
        Vector hardVector = new Vector(new double[]{4, 7, 10});
        QuadraticFunction function = new QuadraticFunction(hardMatrix, hardVector, 5);
        Vector point = method.minimum(function, new Vector(new double[]{1, 1, 1}), 0.00001);
        point.print();
        System.out.println(function.apply(point));
    }
}
