import methods.*;
import quadraticMethods.*;
import structures.*;

import java.util.ArrayList;
import java.util.List;

public class Testing {
    public static void main(String[] args) throws NotConvexFunctionException {
        QuadraticMethod method = new GradientDescent();
        DiagonalMatrix a = new DiagonalMatrix(new double[]{1, 2, 3, 4});
        System.out.println(a.getMaxEigenvalue());
    }
}
