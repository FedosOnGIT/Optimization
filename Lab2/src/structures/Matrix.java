package structures;

public interface Matrix {
    Vector multiply(Vector vector);

    int size();

    double getMinEigenvalue();

    double getMaxEigenvalue();
}
