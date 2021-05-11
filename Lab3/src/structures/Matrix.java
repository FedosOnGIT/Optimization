package structures;

public interface Matrix {
    double get(int row, int column);

    Vector getRow(int index);

    Vector getColumn(int index);

    int size();
}
