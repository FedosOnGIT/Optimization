package methods;

import structures.matrices.*;

public class Gauss extends Method {
    @Override
    public Vector evaluate(Matrix matrix, Vector vector) {
        checkInput(matrix, vector);
        SwappableMatrix m = new SwappableMatrix(matrix.copy());
        int n = m.rowsCount();
        iterations = 0;
        forwardStroke(m, vector, n);
        return reverseStroke(m, vector, n);
    }

    private void checkInput(Matrix matrix, Vector vector) {
        if (matrix.rowsCount() != matrix.columnsCount()) {
            throw new IllegalArgumentException("Gauss works only for square matrices");
        }
        if (matrix.rowsCount() != vector.size()) {
            throw new IllegalArgumentException("vector and matrix row must be same length");
        }
    }

    private void forwardStroke(SwappableMatrix m, Vector v, int n) {
        for (int i = 0; i < n - 1; i++) {
            // Модификация метода Гаусса: делим на наибольший элемент в текущем столбце
            MatrixColumn column = new MatrixColumn(m, i);
            double max = column.get(i);
            int index = i;
            for (int j = i + 1; j < n; j++) {
                double value = column.get(j);
                if (max < value) {
                    max = value;
                    index = j;
                }
            }
            m.swapRows(i, index);
            v.swap(i, index);

            if (isZero(max)) {
                throw new IllegalArgumentException("det(matrix) = 0");
            }

            double b = v.get(i);
            for (int j = i + 1; j < n; j++) {
                double t = m.get(j, i) / max;
                v.set(j, v.get(j) - t * b);
                for (int k = i + 1; k < n; k++) {
                    m.set(j, k, m.get(j, k) - t * m.get(i, k));
                }
                iterations += n - i + 1;
            }
        }
    }

    private Vector reverseStroke(SwappableMatrix m, Vector v, int n) {
        double[] mixedSolution = new double[n];

        mixedSolution[n - 1] =  v.get(n - 1) / m.get(n - 1, n - 1);
        ++iterations;
        for (int i = n - 2; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += m.get(i, j) * mixedSolution[j];
            }
            mixedSolution[i] = (v.get(i) - sum) / m.get(i, i);
            iterations += n - i;
        }

        // Переставляем элементы ответа в нужном порядке
        int[] permutation = m.getPermutation();
        Double[] solution = new Double[n];
        for (int i = 0; i < n; i++) {
            solution[permutation[i]] = mixedSolution[i];
        }
        return new Vector(solution);
    }
}
