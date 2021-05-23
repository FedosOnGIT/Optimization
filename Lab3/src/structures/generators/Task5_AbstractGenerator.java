package structures.generators;

import structures.matrices.Diagonal;
import structures.matrices.SparseMatrix;
import structures.matrices.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public abstract class Task5_AbstractGenerator extends AbstractGenerator {
    private final static int DIAG_COUNT = 7;

    public Task5_AbstractGenerator(int n) {
        super(n);
    }

    protected abstract double generateElement();

    private Vector generateVector(int n) {
        return new Vector(IntStream.range(0, n).mapToObj(i -> generateElement()));
    }

    @Override
    protected List<Diagonal> generateDiagonals() {
        List<Diagonal> result = new ArrayList<>();
        int halfCount = Math.min(n - 1, (DIAG_COUNT - 1) / 2);
        for (int x : generateSelection(1, n, halfCount)) {
            int length = n - x;
            Vector diagData = generateVector(length);
            result.add(new Diagonal(x, diagData));
            result.add(new Diagonal(-x, diagData));
        }
        Vector mainDiagonal = new Vector(n);
        for (int i = 0; i < n; ++i) {
            int x = 0;
            for (Diagonal d : result) {
                Double value = d.getMatrixRowValue(i);
                if (value != null) {
                    x -= value;
                }
            }
            mainDiagonal.set(i, x);
        }
        mainDiagonal.set(0, mainDiagonal.get(0) + 1);
        result.add(new Diagonal(0, mainDiagonal));
        return result;
    }
}
