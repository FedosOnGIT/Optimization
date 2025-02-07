package structures;

public abstract class AbstractMatrix implements Matrix {
    protected int size;
    protected double minEigenvalue;
    protected double maxEigenvalue;

    public double getMinEigenvalue() {
        return minEigenvalue;
    }

    public double getMaxEigenvalue() {
        return maxEigenvalue;
    }

    public int size() {
        return size;
    }
}
