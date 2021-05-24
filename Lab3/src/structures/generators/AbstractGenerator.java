package structures.generators;

import structures.matrices.*;
import structures.matrices.Vector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static launcher.Launcher.*;

public abstract class AbstractGenerator implements Generator {
    private final String matrixFile, rhsFile, exactSolutionFile;
    protected final int n;
    protected final Random random = new Random();

    public AbstractGenerator(int n, String matrixFile, String rhsFile, String exactSolutionFile) {
        assert n > 0 && matrixFile != null && rhsFile != null && exactSolutionFile != null;
        this.matrixFile = matrixFile;
        this.rhsFile = rhsFile;
        this.exactSolutionFile = exactSolutionFile;
        this.n = n;
    }

    public AbstractGenerator(int n) {
        this(n, MATRIX_FILE, RHS_VECTOR_FILE, EXACT_SOLUTION_FILE);
    }

    @Override
    public void generate(Path dir) throws IOException {
        if (Files.notExists(dir)) {
            Files.createDirectories(dir);
        }
        List<Diagonal> diagonals;
        Vector exactSolution;
        try (var writer = Files.newBufferedWriter(dir.resolve(matrixFile))) {
            diagonals = generateDiagonals();
            diagonals.sort(Comparator.comparing(Diagonal::getNumber));
            for (Diagonal d : diagonals) {
                writer.write(d.getNumber() + " : " + d.getVector());
                writer.write('\n');
            }
        }
        try (var writer = Files.newBufferedWriter(dir.resolve(exactSolutionFile))) {
            exactSolution = new Vector(IntStream.rangeClosed(1, n).asDoubleStream());
            writer.write(exactSolution.toString());
            writer.write('\n');
        }
        try (var writer = Files.newBufferedWriter(dir.resolve(rhsFile))) {
            writer.write(new SparseMatrix(diagonals).multiply(exactSolution).toString());
            writer.write('\n');
        }
    }

    protected List<Diagonal> toDiagonals(double[][] matrix) {
        int diagCnt = 2 * n - 1, shift = n - 1;
        List<List<Double>> res = IntStream.range(0, diagCnt)
                .mapToObj(i -> new ArrayList<Double>())
                .collect(Collectors.toList());
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int index = shift;
                if (i != j) {
                    index += i > j ? -1 * (i - j) : j - i;
                }
                res.get(index).add(matrix[i][j]);
            }
        }
        return IntStream.range(0, diagCnt)
                .mapToObj(i -> new Diagonal(i - shift, new Vector(res.get(i))))
                .collect(Collectors.toList());
    }

    protected abstract List<Diagonal> generateDiagonals();

    public Set<Integer> generateSelection(int from, int to, int count) {
        int bound = to - from;
        if (count < 0 || count > bound) {
            throw new IllegalArgumentException("count < 0 || count > bound : invalid state");
        }
        Set<Integer> result = new HashSet<>();
        for (int i = 0; i < count; ++i) {
            int x;
            do {
                x = from + random.nextInt(bound);
            } while (x == 0 || result.contains(x));
            result.add(x);
        }
        return result;
    }
}
