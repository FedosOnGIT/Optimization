package structures;

public interface MatrixMinimal {
    Vector multiply(Vector vector);

    int size();

    double getMinEigenvalue();

    double getMaxEigenvalue();
}
