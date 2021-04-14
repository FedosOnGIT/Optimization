import methods.*;
import quadraticMethods.*;
import structures.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Testing {
    static QuadraticFunction generateFunction(int dimension, double condition) throws NotConvexFunctionException {
        assert condition >= 1;
        double[] matrix = new double[dimension];
        matrix[0] = 2;
        matrix[dimension - 1] = condition * 2;
        for (int i = 1; i < dimension - 1; i++) {
            matrix[i] = Math.random() * (condition * 2 - 2) + 2;
        }
        double[] vector = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            vector[i] = Math.random() * 2000;
        }
        return new QuadraticFunction(new DiagonalMatrix(matrix), new Vector(vector), Math.random() * 2000);
    }

    static Vector generateVector(int number) {
        double[] vector = new double[number];
        for (int i = 0; i < number; i++) {
            vector[i] = Math.random() * 2000;
        }
        return new Vector(vector);
    }

    static void generate(QuadraticMethod method) throws NotConvexFunctionException {
        for (int i = 2; i < 100; i++) {
            for (double k = 1; k < 1000; k += 0.5) {
                method.minimum(generateFunction(i, k), generateVector(i), 0.001);
            }
        }
    }

    public static void main(String[] args) throws NotConvexFunctionException {
        // GradientDescent
        // System.out.println("Gradient descent");
        // generate(new GradientDescent());
        // SteepestDescent
        // System.out.println("Steepest descent");
        // generate(new SteepestDescent(new GoldenRatioMethod()));
        // ConjugateGradients
        System.out.println("Conjugated gradients");
        generate(new ConjugateGradients());
    }
}
