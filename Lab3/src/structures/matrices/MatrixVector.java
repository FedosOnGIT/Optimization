package structures.matrices;

import java.util.Arrays;
import java.util.stream.IntStream;

public abstract class MatrixVector extends Tuple {
    protected final Matrix matrix;
    protected final int index;

    public MatrixVector(Matrix matrix, int index) {
        this.matrix = matrix;
        this.index = index;
    }

    @Override
    public Vector copy() {
        return new Vector(IntStream.range(0, size()).mapToDouble(this::get).boxed());
    }
}
