package structures.generators;

import structures.matrices.Diagonal;
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
        List<Diagonal> matrix;
        Vector exactSolution;
        try (var writer = Files.newBufferedWriter(directory.resolve(matrixFile))) {
            matrix = generateMatrix();
            matrix.sort(Comparator.comparing(Diagonal::getNumber));
            for (Diagonal d : matrix) {
                writer.write(d.getNumber() + " : " + d.getVector());
                writer.write('\n');
            }
        }
        try (var writer = Files.newBufferedWriter(directory.resolve(exactSolutionFile))) {
            exactSolution = new Vector(IntStream.rangeClosed(1, n).asDoubleStream().boxed());
            writer.write(exactSolution.toString());
            writer.write('\n');
        }
        try (var writer = Files.newBufferedWriter(directory.resolve(rhsFile))) {
            writer.write(
                    new Vector(IntStream.range(0, n).mapToDouble(i -> getRow(matrix, i).scalar(exactSolution))).toString()
            );
            writer.write('\n');
        }
    }

    private Vector getRow(List<Diagonal> matrix, int row) {
        List<Integer> numbers = IntStream.range(0, n).map(i -> matrix.get(i).getNumber()).boxed().collect(Collectors.toList());
        return new Vector(IntStream.range(0, n)
                .mapToDouble(column -> {
                    int number, pos;
                    if (column < row) {
                        number = -(row - column + 1);
                        pos = column;
                    } else {
                        number = column - row;
                        pos = row;
                    }
                    int index = Collections.binarySearch(numbers, number);
                    return index < 0 ? 0 : matrix.get(index).getVector().get(pos);
                })
                .boxed()
        );
    }

    protected List<Diagonal> toDiag(double[][] matrix) {
        List<List<Double>> res = new ArrayList<>(2 * n - 1);
        for (int i = 0; i < 2 * n - 1; i++) {
            res.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int index = 0;
                if (i != j) {
                    index = i > j ? -1 * (i - j) : j - i;
                }
                index += n - 1;
                res.get(index).add(matrix[i][j]);
            }
        }
        return IntStream.range(0, 2 * n - 1)
                .mapToObj(i -> new Diagonal(i - (n - 1), new Vector(res.get(i))))
                .collect(Collectors.toList());
    }

    protected abstract List<Diagonal> generateMatrix();
}