package structures.matrices;

import java.util.Objects;

public class Diagonal {
    private Integer number;
    private Vector vector;

    public Diagonal(Integer number, Vector vector) {
        this.number = number;
        this.vector = vector;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Vector getVector() {
        return vector;
    }

    public void setVector(Vector vector) {
        this.vector = vector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Diagonal diagonal = (Diagonal) o;
        return Objects.equals(number, diagonal.number) && Objects.equals(vector, diagonal.vector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, vector);
    }

    public Double getMatrixRowValue(int row) {
        int i = number < 0 ? -number : 0;
        if (row < i || row - i >= vector.size()) {
            return null;
        }
        return vector.get(row - i);
    }

    public Double getMatrixColumnValue(int column) {
        int j = number > 0 ? number : 0;
        if (column < j || column - j >= vector.size()) {
            return null;
        }
        return vector.get(column - j);
    }
}
