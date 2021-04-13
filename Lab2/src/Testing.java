import methods.GoldenRatioMethod;
import quadraticMethods.*;
import structures.*;

import java.util.ArrayList;
import java.util.List;

public class Testing {
    public static void main(String[] args) throws NotConvexFunctionException {
        QuadraticMethod method = new GradientDescent();
        QuadraticFunction function = new QuadraticFunction(3,
                vector -> {
                    double x = vector.getCoordinate(0);
                    double y = vector.getCoordinate(1);
                    double z = vector.getCoordinate(2);
                    return x * x + y * y + z * z;
                },
                new ArrayList<>(List.of(new Double[]{2.0, 2.0, 2.0})),
                vector -> {
                    double x = vector.getCoordinate(0);
                    double y = vector.getCoordinate(1);
                    double z = vector.getCoordinate(2);
                    return new Vector(List.of(new Double[]{2 * x, 2 * y, 2 * z}));
                });
        method.minimum(function, new Vector(List.of(new Double[]{1.0, 2.0, 3.0})), 0.0000001).print();
    }
}
