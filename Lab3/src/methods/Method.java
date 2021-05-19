package methods;

import structures.matrices.Matrix;
import structures.matrices.Vector;

import java.lang.reflect.InvocationTargetException;

public abstract class Method {
    private final static double EPS = 1e-14;

    protected long iterations = 0;

    public abstract Vector evaluate(Matrix matrix, Vector vector);

    protected boolean isZero(double value) {
        return Math.abs(value) < EPS;
    }

    public long getIterations() {
        return iterations;
    }

    public static <T extends Method> T init(Class<T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("can not create method instance from class token");
        }
    }
}
