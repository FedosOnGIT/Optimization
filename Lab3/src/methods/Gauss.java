package methods;

import structures.elements.Element;
import structures.matrices.*;

import javax.xml.stream.events.EndElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Gauss {
    public static <T> Vector<T> evaluate(Matrix<T> matrix, Vector<T> vector) {
        checkInput(matrix, vector);
        SwappableMatrix<T> m = new SwappableMatrix<>(matrix);
        forwardStroke(m, vector);
        return reverseStroke(m, vector);
    }

    private static <T> void checkInput(Matrix<T> matrix, Vector<T> vector) {
        if (matrix.rowsCount() != matrix.columnsCount()) {
            throw new IllegalArgumentException("Gauss works only for square matrices");
        }
        if (matrix.rowsCount() != vector.size()) {
            throw new IllegalArgumentException("vector and matrix row must be same length");
        }
    }

    private static <T> void forwardStroke(SwappableMatrix<T> m, Vector<T> vector) {
        int n = m.rowsCount();
        Element<T> zero = vector.get(0).getZero();

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
            Element<T> value = vector.get(i);
            vector.set(i, vector.get(index));
            vector.set(index, value);

            if (max.compareTo(zero) == 0) {
                throw new IllegalArgumentException("det Matrix = 0");
            }

            Element<T> b = vector.get(i);
            for (int j = i + 1; j < n; j++) {
                Element<T> t = m.get(j, i).divide(max);
                vector.set(j, vector.get(j).subtract(t.multiply(b)));
                for (int k = i + 1; k < n; k++) {
                    m.set(j, k, m.get(j, k).subtract(t.multiply(m.get(i, k))));
                }
            }
        }
    }

    private static <T> Vector<T> reverseStroke(Matrix<T> m, Vector<T> vector) {
        int n = m.columnsCount();
        Element<T> zero = vector.get(0).getZero();
        List<Element<T>> solution = new ArrayList<>(Collections.nCopies(n, null));

        solution.set(n - 1, vector.get(n - 1).divide(m.get(n - 1, n - 1)));
        for (int i = n - 2; i >= 0; i--) {
            Element<T> sum = zero;
            for (int j = i + 1; j < n; j++) {
                sum = sum.add(m.get(i, j).multiply(solution.get(j)));
            }
            solution.set(i, (vector.get(i).subtract(sum)).divide(m.get(i, i)));
        }
        return new Vector<>(solution);
    }

}
