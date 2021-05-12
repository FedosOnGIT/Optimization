package structures.matrices;

import java.util.stream.IntStream;

public interface Matrix {
    double get(int i, int j);

    void set(int i, int j, double value);

    default Vector getRow(int i) {
        return new Vector(IntStream.range(0, size()).mapToDouble(j -> get(i, j)).toArray());
    }

    default void setRow(int i, Vector row) {
        int size = size();
        assert row.size() == size;
        for (int j = 0; j < size; j++) {
            set(i, j, row.get(j));
        }
    }

    default Vector getColumn(int j) {
        return new Vector(IntStream.range(0, size()).mapToDouble(i -> get(i, j)).toArray());
    }

    default void setColumn(int j, Vector column) {
        int size = size();
        assert column.size() == size;
        for (int i = 0; i < size; i++) {
            set(i, j, column.get(i));
        }
    }

    int size();
}
