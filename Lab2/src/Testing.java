import methods.*;
import quadraticMethods.*;
import structures.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
            vector[i] = Math.random() * 20;
        }
        return new Vector(vector);
    }

    static void generate() throws NotConvexFunctionException {
        QuadraticMethod methodOne = new GradientDescent();
        QuadraticMethod methodTwo = new SteepestDescent(new GoldenRatioMethod());
        QuadraticMethod methodThree = new ConjugateGradients();
        for (int i = 2; i < 100; i++) {
            for (double k = 1; k < 1000; k += 0.5) {
                QuadraticFunction function = generateFunction(i, k);
                Vector start = generateVector(i);
                methodOne.minimum(function, start, 0.00001);
                methodTwo.minimum(function, start, 0.00001);
                methodThree.minimum(function, start, 0.00001);
            }
        }
    }

    static void write(QuadraticMethod method, PrintWriter writer, QuadraticFunction function, String name) {
        MethodResult<Vector> result;
        writer.println(name);
        result = method
                .minimum(function, generateVector(2), 0.00001);
        writer.println(result.getMinimal().print());
        writer.println(result.iterations());
        for (int i = 0; i < result.iterations(); i++) {
            writer.println(result.get(i).print());
        }
    }

    static void steepest(PrintWriter writer, Method method, QuadraticFunction function, String name) {
        SteepestDescent descent = new SteepestDescent(method);
        writer.println(name);
        MethodResult<Vector> result = descent.minimum(function, generateVector(2), 0.00001);
        writer.println(result.getMinimal().print());
        int number = 0;
        ArrayList<AlphaPair> alphaPairs = descent.getAlphas();
        for (AlphaPair alphaPair : alphaPairs) {
            number += alphaPair.getIterations();
        }
        writer.println(result.iterations() + number);
        for (int i = 0; i < result.iterations() - 1; i++) {
            writer.print(result.get(i).print());
            writer.print(" ");
            writer.println(alphaPairs.get(i).getIterations());
        }
        writer.println(result.getMinimal().print());
    }

    public static void main(String[] args) throws NotConvexFunctionException {
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Path.of("SteepestGradient.txt")))) {
            QuadraticFunction function = new QuadraticFunction(
                    new SquareMatrix(new double[][]{{2, 1}, {1, 18}}, new double[]{5 - Math.sqrt(17), 5 + Math.sqrt(17)}),
                    new Vector(new double[]{5, 6}),
                    0);
            steepest(writer, new DichotMethod(0.00001), function, "DichotMethod");
            writer.println();
            steepest(writer, new GoldenRatioMethod(), function, "GoldenRationMethod");
        } catch (final IOException e) {
            System.out.println("Ty dolboeb!");
        }
    }
}
