package structures.generators;

import structures.elements.Element;
import structures.matrices.DenseMatrix;
import structures.matrices.Matrix;
import structures.matrices.Vector;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class RandomGenerator<T extends Number> extends Generator<T> {
    final Supplier<Element<T>> randomizer;

    RandomGenerator(Supplier<Element<T>> randomizer) {
        this.randomizer = randomizer;
    }

    private Vector<T> generateVector(int n) {
        return new Vector<T>(IntStream.range(0, n).mapToObj(i -> randomizer.get()));
    }

    @Override
    public void generate(int n) {
        matrix = new DenseMatrix<T>(n, n, generateVector(n * n));
        exactSolution = generateVector(n);
        vector = Matrix.mul(matrix, exactSolution);
    }

}
