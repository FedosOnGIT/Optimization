import methods.*;
import quadraticMethods.*;
import structures.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
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
            vector[i] = Math.random() * 20;
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

    public static void main(String[] args) throws NotConvexFunctionException {
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Path.of("first.txt")))) {
            QuadraticFunction function = new QuadraticFunction(
                    new DiagonalMatrix(new double[]{2, 3}),
                    new Vector(new double[]{0, 0}),
                    0);
            write(new GradientDescent(), writer, function, "GradientDescent");
            writer.println();
            write(new SteepestDescent(), writer, function, "SteepestDescent");
            writer.println();
            write(new ConjugateGradients(), writer, function, "ConjugatedGradients");
        } catch (final IOException e) {
            System.out.println("Ty dolboeb!");
        }
    }
}
