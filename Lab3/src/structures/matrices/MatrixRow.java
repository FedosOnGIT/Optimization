package structures.matrices;

public class MatrixRow {
    Matrix matrix;
    int i;

    MatrixRow(Matrix matrix, int i) {
        this.matrix = matrix;
        this.i = i;
    }

    public double get(int index) {
        return matrix.get(i, index);
    }

    public void set(int index, double value) {
        matrix.set(i, index, value);
    }

    public int size() {
        return matrix.size();
    }

    @Override
    public String toString() {
        return matrix.getRow(i).toString();
    }
}
