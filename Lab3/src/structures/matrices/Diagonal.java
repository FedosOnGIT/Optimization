package structures.matrices;

import structures.FileReadable;

import java.util.Objects;
import java.util.stream.IntStream;

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diagonal diagonal = (Diagonal) o;
        return Objects.equals(number, diagonal.number) && Objects.equals(vector, diagonal.vector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, vector);
    }

    public static Diagonal parseDiagonal(String diag) {
        String[] parts = diag.split(" ");
        return new Diagonal(Integer.parseInt(parts[0]),
                new Vector(IntStream.range(2, parts.length)
                        .mapToObj(i -> FileReadable.clearElement(parts[i]))
                        .map(Double::parseDouble)
                )
        );
    }
}
