import quadraticMethods.*;
import structures.*;

public class Testing {
    public static void main(String[] args) throws NotConvexFunctionException {
        QuadraticMethod method = new ConjugateGradients();
        MatrixMinimal hardMatrix = new Matrix(new double[][]{{2, 1, 0}, {1, 2, 0}, {0, 0, 40}}, new double[]{1, 3, 40});
        Vector hardVector = new Vector(new double[]{4, 7, 10});
        QuadraticFunction function = new QuadraticFunction(hardMatrix, hardVector, 5);
        Vector point = method.minimum(function, new Vector(new double[]{1, 1, 1}), 0.00001);
        point.print();
        System.out.println(function.apply(point));
    }
}
