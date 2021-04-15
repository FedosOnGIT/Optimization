package quadraticMethods;

import structures.MethodResult;
import structures.QuadraticFunction;
import structures.Vector;

import java.util.ArrayList;

public interface QuadraticMethod {
    MethodResult<Vector> minimum(QuadraticFunction function, Vector point, double epsilon);
}
