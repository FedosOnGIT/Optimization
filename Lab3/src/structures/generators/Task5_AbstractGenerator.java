package structures.generators;

import structures.matrices.Diagonal;
import structures.matrices.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public abstract class Task5_AbstractGenerator extends AbstractGenerator {
    private final static int HELP_DIAG_CNT = 4;

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
        // не даем матрице быть вырожденной
        Vector nearDiagonal = new Vector(IntStream.range(0, n - 1).mapToObj(i -> {
            double res = generateElement();
            while (res == 0) {
                res = generateElement();
            }
            return res;
        }));
        result.add(new Diagonal(1, nearDiagonal));
        result.add(new Diagonal(-1, nearDiagonal));
        // генерируем оставшиеся диагонали
        if (n-2 > 0) {
            for (int x : generateSelection(2, n, Math.min(HELP_DIAG_CNT, n-2))) {
                int length = n - x;
                Vector diagData = generateVector(length);
                result.add(new Diagonal(x, diagData));
                result.add(new Diagonal(-x, diagData));
            }
        }
        // инициализируем главную диагональ
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
