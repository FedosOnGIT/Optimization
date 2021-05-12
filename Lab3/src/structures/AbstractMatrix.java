package structures;

import java.util.stream.IntStream;

public abstract class AbstractMatrix implements Matrix {

    @Override
    public Vector getRow(final int index) {
        return new Vector(IntStream.range(0, size()).mapToDouble(i -> get(index, i)).toArray());
    }

    @Override
    public Vector getColumn(final int index) {
        return new Vector(IntStream.range(0, size()).mapToDouble(i -> get(i, index)).toArray());
    }
}
