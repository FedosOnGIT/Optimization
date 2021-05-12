package structures.generators;

import structures.matrices.DenseMatrix;
import structures.matrices.Matrix;
import structures.matrices.Vector;

import java.util.Random;
import java.util.stream.IntStream;

public class Task2Generator implements Generator {
    @Override
    public Matrix generateMatrix(int n) {
        double[] values = new double[n * n];
        Random random = new Random();
        for (int i = 0; i < values.length; ++i) {
            values[i] = -(1 + random.nextInt(4));
        }
        for (int i = 0; i < n; i++) {
             int sum = 0;
             for (int j = 0; j < n; j++) {
                 sum += values[i * n + j];
             }
             values[i * n + i] = -sum;
        }
        values[0] += 1;
        return new DenseMatrix(n, n, values);
    }

    @Override
    public Vector generateVector(int n) {
        return null;
    }

    public Vector generateExactSolution(int n) {
        return new Vector(IntStream.range(1, n + 1).asDoubleStream().toArray());
    }
}
