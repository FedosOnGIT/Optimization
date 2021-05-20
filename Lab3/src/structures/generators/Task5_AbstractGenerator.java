package structures.generators;

import structures.matrices.Diagonal;
import structures.matrices.SparseMatrix;
import structures.matrices.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Task5_AbstractGenerator extends AbstractGenerator {
    private final static int DIAG_COUNT = 3;

    public Task5_AbstractGenerator(int n) {
        super(n);
    }

    protected abstract double generateElement();

    private Vector generateVector(int n) {
        Vector result = new Vector(n);
        for (int i = 0; i < n; ++i) {
            result.set(i, generateElement());
        }
        return result;
    }

    @Override
    protected List<Diagonal> generateDiagonals() {
        List<Diagonal> result = new ArrayList<>();
        int halfCount = Math.min(n-1, (DIAG_COUNT-1)/2);
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
                int dPos = (d.getNumber() > 0 ? i : i + d.getNumber());
                if (dPos >= 0 && dPos < d.getVector().size()) {
                    x -= d.getVector().get(dPos);
                }
            }
            mainDiagonal.set(i, x);
        }
        result.add(new Diagonal(0, mainDiagonal));
        return result;
    }
}
