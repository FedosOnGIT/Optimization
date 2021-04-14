import methods.*;
import quadraticMethods.*;
import structures.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Testing {
    QuadraticFunction generate(double lines, int number) throws NotConvexFunctionException {
        assert lines >= 1;
        double[] matrix = new double[number];
        matrix[0] = 2;
        matrix[number - 1] = lines * 2;
        for (int i = 1; i < number - 1; i++) {
            matrix[i] = Math.random() * (lines * 2 - 2) + 2;
        }
        double[] vector = new double[number];
        for (int i = 0; i < number; i++) {
            vector[i] = Math.random() * 2000;
        }
        return new QuadraticFunction(new DiagonalMatrix(matrix), new Vector(vector), Math.random() * 2000);
    }

    Vector generateVector(int number) {
        double[] vector = new double[number];
        for (int i = 0; i < number; i++) {
            vector[i] = Math.random() * 2000;
        }
        return new Vector(vector);
    }

    public static void main(String[] args) throws NotConvexFunctionException {
        QuadraticMethod method = new ConjugateGradients();
    }
}
