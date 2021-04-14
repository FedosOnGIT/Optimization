package structures;

import java.util.function.Function;

public class QuadraticFunction implements Function<Vector, Double> {
    private final int size;
    private final Matrix A;
    private final Vector B;
    private final double C;

    public QuadraticFunction(final Matrix A,
                             final Vector B,
                             final double C) throws NotConvexFunctionException {
        if (A.size() != B.size()) {
            throw new SizeException("Dimensions A and B are not equal!");
        }
        size = A.size();
        this.A = A;
        this.B = B;
        this.C = C;
        if (A.getMinEigenvalue() <= 0) {
            throw new NotConvexFunctionException("All eigenvalues must be more than zero!");
        }
    }

    public Double apply(final Vector variable) {
        assert variable.size() == size;
        return A.multiply(variable).multiply(variable) + B.multiply(variable) + C;
    }

    public int size() {
        return size;
    }

    public double minEigenValue() {
        return A.getMinEigenvalue();
    }

    public double maxEigenValue() {
        return A.getMaxEigenvalue();
    }

    public Vector applyGradient(final Vector variable) {
        return A.multiply(variable).multiply(2).plus(B);
    }

    public Matrix getMatrix() {
        return A;
    }
}
