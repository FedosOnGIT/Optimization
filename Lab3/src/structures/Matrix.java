package structures;

public interface Matrix {
    double get(final int row, final int column);

    Vector getRow(final int index);

    Vector getColumn(final int index);

    int size();
}
