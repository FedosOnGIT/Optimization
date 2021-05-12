package structures.generators;

import structures.matrices.DenseMatrix;
import structures.matrices.Matrix;
import structures.matrices.Vector;

import java.util.Random;

public class RandomGenerator implements Generator {
    private double min, max;

    RandomGenerator(double min, double max) {
        this.min = min;
        this.max = max;
    }

    private double[] getGenerated(double[] values) {
        Random random = new Random();
        for (int i = 0; i < values.length; i++) {
            values[i] = min + (max - min) * random.nextDouble();
        }
        return values;
    }

    @Override
    public Matrix generateMatrix(int n) {
        return new DenseMatrix(n, n, getGenerated(new double[n * n]));
    }

    @Override
    public Vector generateVector(int n) {
        return new Vector(getGenerated(new double[n]));
    }
}
