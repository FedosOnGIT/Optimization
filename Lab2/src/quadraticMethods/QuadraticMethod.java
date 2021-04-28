package quadraticMethods;

import structures.QuadraticFunction;
import structures.QuadraticMethodResult;
import structures.Vector;

public interface QuadraticMethod {
    QuadraticMethodResult minimum(QuadraticFunction function, Vector point, double epsilon);
}
