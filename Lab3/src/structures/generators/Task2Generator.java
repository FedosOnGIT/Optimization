package structures.generators;

import structures.elements.Element;
import structures.matrices.DenseMatrix;
import structures.matrices.Matrix;
import structures.matrices.Vector;

import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Task2Generator<T> extends Generator<T> {
    final Supplier<Element<T>> supplier;
    final Element<T> zero, one;

    public Task2Generator(Supplier<Element<T>> supplier, Element<T> zero, Element<T> one) {
        this.supplier = supplier;
        this.zero = zero;
        this.one = one;
    }

    protected void generateMatrix(int n) {
        matrix = new DenseMatrix<>(n, n, zero);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix.set(i, j, supplier.get());
            }
        }
        for (int i = 0; i < n; i++) {
            Element<T> sum = zero;
            for (int j = 0; j < n; j++) {
                if (j == i) continue;
                sum = sum.add(matrix.get(i, j));
            }
            matrix.set(i, i, sum.negate());
        }
        matrix.set(0, 0, matrix.get(0, 0).add(one));
    }

    private void generateExactSolution(int n) {
        Element<T> current = zero;
        exactSolution = new Vector<>(n, zero);
        for (int i = 0; i < n; i++) {
            current = current.add(one);
            exactSolution.set(i, current);
        }
    }

    @Override
    protected void generateImpl(int n) {
        generateMatrix(n);
        generateExactSolution(n);
        exactSolution = Matrix.multiply(matrix, exactSolution);
    }

}
