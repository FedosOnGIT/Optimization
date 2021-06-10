package structures.matrices;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Matrix {
    /**
     * Используется внутри {@link Matrix#get(int, int)}.
     * Возвращает элемент, который находится в i строке и в j стобце матрицы.
     */
    protected abstract double getImpl(int i, int j);

    /**
     * Используется внутри {@link Matrix#set(int, int, double)}.
     * Изменяет элемент матрицы, который находится в i строке, j стоблце на value
     */
    protected abstract void setImpl(int i, int j, double value);

    /**
     * @return Количество строк матрицы
     */
    public abstract int rowsCount();

    /**
     * @return Количество столбцов матрицы
     */
    public abstract int columnsCount();

    /**
     * Делает копию исходной матрицы, чтобы возращаемая матрица могла быть изменена независимо от исходной
     *
     * @return копия исходной матрицы
     */
    public abstract Matrix copy();

    private void checkCell(int i, int j) {
        assert i >= 0 && j >= 0 && i < rowsCount() && j < columnsCount();
    }

    public double get(int i, int j) {
        checkCell(i, j);
        return getImpl(i, j);
    }

    public void set(int i, int j, double value) {
        checkCell(i, j);
        setImpl(i, j, value);
    }

    private Vector getVector(IntFunction<Double> mapper, int size) {
        return new Vector(IntStream.range(0, size).mapToObj(mapper));
    }

    public Vector getRow(int i) {
        return getVector(j -> get(i, j), columnsCount());
    }

    public Vector getColumn(int j) {
        return getVector(i -> get(i, j), rowsCount());
    }

    private void setVector(Consumer<Integer> setter, int size) {
        for (int j = 0; j < size; j++) {
            setter.accept(j);
        }
    }

    public void setRow(int i, Vector row) {
        setVector(j -> set(i, j, row.get(j)), columnsCount());
    }

    public void setColumn(int j, Vector column) {
        setVector(i -> set(i, j, column.get(i)), rowsCount());
    }

    @Override
    public String toString() {
        return IntStream.range(0, rowsCount())
                .mapToObj(this::getRow)
                .map(Objects::toString)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    public Vector multiply(Vector vector) {
        assert columnsCount() == vector.size();
        return new Vector(IntStream.range(0, rowsCount()).mapToObj(i -> new MatrixRow(this, i).scalar(vector)));
    }

    public Matrix multiply(Double alpha) {
        double[][] matrix = new double[rowsCount()][columnsCount()];
        for (int i = 0; i < rowsCount(); i++) {
            for (int j = 0; j < columnsCount(); j++) {
                matrix[i][j] = get(i, j) * alpha;
            }
        }
        return new DenseMatrix(matrix);
    }

    public Matrix add(Matrix other) {
        assert other.columnsCount() == columnsCount() && other.rowsCount() == rowsCount();
        double[][] matrix = new double[rowsCount()][columnsCount()];
        for (int i = 0; i < rowsCount(); i++) {
            for (int j = 0; j < columnsCount(); j++) {
                matrix[i][j] = get(i, j) + other.get(i, j);
            }
        }
        return new DenseMatrix(matrix);
    }

    public Matrix subtract(Matrix other) {
        assert other.columnsCount() == columnsCount() && other.rowsCount() == rowsCount();
        return add(other.multiply(-1.0));
    }

    public static void checkIsSquare(double[][] values) {
        assert values.length > 0;
        int columns = values[0].length;
        for (int i = 1; i < values.length; i++) {
            assert columns == values[i].length;
        }
    }

    public static boolean checkIsEqual(Matrix m1, Matrix m2) {
        if (m1.rowsCount() != m2.rowsCount() || m1.columnsCount() != m2.columnsCount()) {
            return false;
        }
        for (int i = 0; i < m1.columnsCount(); i++) {
            for (int j = 0; j < m1.rowsCount(); j++) {
                if (Math.abs(m1.get(i, j) - m2.get(i, j)) > 1e-14) {
                    System.err.printf("m1.get{i, j) = %f, m2.get(i, j)= %f\n", m1.get(i, j), m2.get(i, j));
                    return false;
                }
            }
        }
        return true;
    }
}
