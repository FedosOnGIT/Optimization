package structures.generators;

import structures.matrices.Diagonal;
import structures.matrices.Matrix;
import structures.matrices.SparseMatrix;
import structures.matrices.Vector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static structures.FileReadable.createDirectoryIfNotExists;

public abstract class AbstractGenerator implements Generator {
    private final String matrixFile, rhsFile, exactSolutionFile;
    protected final int n;

    public AbstractGenerator(int n, String matrixFile, String rhsFile, String exactSolutionFile) {
        assert n > 0 && matrixFile != null && rhsFile != null && exactSolutionFile != null;
        this.matrixFile = matrixFile;
        this.rhsFile = rhsFile;
        this.exactSolutionFile = exactSolutionFile;
        this.n = n;
    }

    public AbstractGenerator(int n) {
        this(n, "matrix.txt", "rhs_vector.txt", "exact_solution.txt");
    }

    @Override
    public void generate(Path directory) throws IOException {
        createDirectoryIfNotExists(directory);
        List<Diagonal> diagonals;
        Vector exactSolution;
        try (var writer = Files.newBufferedWriter(directory.resolve(matrixFile))) {
            diagonals = generateDiagonals();
            diagonals.sort(Comparator.comparing(Diagonal::getNumber));
            for (Diagonal d : diagonals) {
                writer.write(d.getNumber() + " : " + d.getVector());
                writer.write('\n');
            }
        }
        try (var writer = Files.newBufferedWriter(directory.resolve(exactSolutionFile))) {
            exactSolution = new Vector(IntStream.rangeClosed(1, n).asDoubleStream());
            writer.write(exactSolution.toString());
            writer.write('\n');
        }
        try (var writer = Files.newBufferedWriter(directory.resolve(rhsFile))) {
            writer.write(new SparseMatrix(diagonals).multiply(exactSolution).toString());
            writer.write('\n');
        }
    }

    protected List<Diagonal> toDiagonals(double[][] matrix) {
        List<List<Double>> res = new ArrayList<>(2 * n - 1);
        for (int i = 0; i < 2 * n - 1; i++) {
            res.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int index = n - 1;
                if (i != j) {
                    index += i > j ? -1 * (i - j) : j - i;
                }
                res.get(index).add(matrix[i][j]);
            }
        }
        return IntStream.range(0, 2 * n - 1)
                .mapToObj(i -> new Diagonal(i - (n - 1), new Vector(res.get(i))))
                .collect(Collectors.toList());
    }

    protected abstract List<Diagonal> generateDiagonals();
}