package quadraticMethods;

import structures.QuadraticFunction;
import structures.Vector;

public interface QuadraticMethod {
    Vector minimum(QuadraticFunction function, Vector point, double epsilon);
}
