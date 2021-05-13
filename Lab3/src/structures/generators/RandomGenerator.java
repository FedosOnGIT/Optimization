package structures.generators;

import structures.elements.Element;
import structures.matrices.DenseMatrix;
import structures.matrices.Matrix;
import structures.matrices.Vector;

import java.util.function.Supplier;
import java.util.stream.IntStream;

public class RandomGenerator<T> extends Generator<T> {
    final Supplier<Element<T>> randomizer;

    public RandomGenerator(Supplier<Element<T>> randomizer) {
        this.randomizer = randomizer;
    }

    private Vector<T> generateVector(int n) {
        return new Vector<>(IntStream.range(0, n).mapToObj(i -> randomizer.get()));
    }

    @Override
    protected void generateImpl(int n) {
        matrix = new DenseMatrix<>(n, n, generateVector(n * n));
        exactSolution = generateVector(n);
        vector = Matrix.multiply(matrix, exactSolution);
    }
}
