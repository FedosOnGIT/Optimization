package structures.generators;

import structures.elements.Element;
import structures.matrices.DenseMatrix;
import structures.matrices.Matrix;
import structures.matrices.Vector;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Task2Generator<T extends Number> extends Generator<T> {
    final Supplier<Element<T>> supplier;
    final Element<T> zero, one;

    public Task2Generator(Supplier<Element<T>> supplier, Element<T> zero, Element<T> one) {
        this.supplier = supplier;
        this.zero = zero;
        this.one = one;
    }

    private void generateMatrix(int n) {
        Vector<T> values = new Vector<T>(n * n, zero);
        for (int i = 0; i < values.size(); ++i) {
            values.set(i, supplier.get());
        }
        for (int i = 0; i < n; i++) {
            Element<T> sum = zero.copy();
            for (int j = 0; j < n; j++) {
                if (j == i) continue;
                sum.add(values.get(i * n + j));
            }
            values.set(i * n + i, sum.negate());
        }
        values.get(0).add(one);
        matrix = new DenseMatrix<T>(n, n, values);
    }

    private void generateExactSolution(int n) {
        Element<T> current = one.copy();
        exactSolution = new Vector<T>(IntStream.range(0, n).mapToObj(i -> current.add(one).copy()));
    }

    @Override
    public void generate(int n) {
        generateMatrix(n);
        generateExactSolution(n);
        exactSolution = Matrix.mul(matrix, exactSolution);
    }

}
