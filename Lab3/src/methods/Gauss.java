package methods;

import structures.elements.Element;
import structures.matrices.*;

import javax.xml.stream.events.EndElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Gauss extends Method {
    public <T> Vector<T> evaluate(Matrix<T> matrix, Vector<T> vector) {
        checkInput(matrix, vector);
        SwappableMatrix<T> m = new SwappableMatrix<>(matrix);
        int n = m.rowsCount();
        Element<T> zero = vector.getZero();
        forwardStroke(m, vector, n, zero);
        return reverseStroke(m, vector, n, zero);
    }

    private <T> void checkInput(Matrix<T> matrix, Vector<T> vector) {
        if (matrix.rowsCount() != matrix.columnsCount()) {
            throw new IllegalArgumentException("Gauss works only for square matrices");
        }
        if (matrix.rowsCount() != vector.size()) {
            throw new IllegalArgumentException("vector and matrix row must be same length");
        }
    }

    private <T> void forwardStroke(SwappableMatrix<T> m, Vector<T> v, int n, Element<T> zero) {
        for (int i = 0; i < n - 1; i++) {
            // Модификация метода Гаусса: делим на наибольший элемент в текущем столбце
            MatrixColumn<T> column = new MatrixColumn<>(m, i);
            Element<T> max = column.get(i);
            int index = i;
            for (int j = i + 1; j < n; j++) {
                Element<T> value = column.get(j);
                if (max.compareTo(value) < 0) {
                    max = value;
                    index = j;
                }
            }
            m.swapRows(i, index);
            v.swap(i, index);

            if (max.compareTo(zero) == 0) {
                throw new IllegalArgumentException("det(matrix) = 0");
            }

            Element<T> b = v.get(i);
            for (int j = i + 1; j < n; j++) {
                Element<T> t = m.get(j, i).divide(max);
                v.set(j, v.get(j).subtract(t.multiply(b)));
                for (int k = i + 1; k < n; k++) {
                    m.set(j, k, m.get(j, k).subtract(t.multiply(m.get(i, k))));
                }
            }
        }
    }

    private <T> Vector<T> reverseStroke(SwappableMatrix<T> m, Vector<T> v, int n, Element<T> zero) {
        List<Element<T>> mixedSolution = new ArrayList<>(Collections.nCopies(n, null));

        mixedSolution.set(n - 1, v.get(n - 1).divide(m.get(n - 1, n - 1)));
        for (int i = n - 2; i >= 0; i--) {
            Element<T> sum = zero;
            for (int j = i + 1; j < n; j++) {
                sum = sum.add(m.get(i, j).multiply(mixedSolution.get(j)));
            }
            mixedSolution.set(i, (v.get(i).subtract(sum)).divide(m.get(i, i)));
        }

        // Переставляем элементы ответа в нужном порядке
        List<Element<T>> solution = new ArrayList<>(Collections.nCopies(n, null));
        int[] permutation = m.getPermutation();
        for (int i = 0; i < n; i++) {
            solution.set(permutation[i], mixedSolution.get(i));
        }
        return new Vector<>(solution);
    }

}
