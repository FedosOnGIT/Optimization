package launcher;

import methods.Method;
import structures.FileReadable;
import structures.matrices.FileReadableMatrix;
import structures.matrices.Matrix;
import structures.matrices.Vector;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static launcher.Launcher.*;

public class SolverVisitor<T extends FileReadableMatrix, M extends Method> extends SimpleFileVisitor<Path> {
    private final Statistics stat = new Statistics();
    private final List<Statistics> statisticsList = new ArrayList<>();
    private final Class<T> matrixClass;
    private final Class<M> methodClass;
    private final String mainDirName;
    private Matrix matrix;
    private Vector rhsVector, exactSolution;

    public SolverVisitor(Class<T> matrixClass, Class<M> methodClass, String mainDirName) {
        this.mainDirName = mainDirName;
        this.matrixClass = matrixClass;
        this.methodClass = methodClass;
    }

    public List<Statistics> getStatisticsList() {
        return statisticsList;
    }

    public void reset() {
        resetIteration();
        statisticsList.clear();
    }

    private void resetIteration() {
        stat.reset();
        matrix = null;
        rhsVector = null;
        exactSolution = null;
    }

    private static boolean isSame(Path file, String name) {
        return file.getFileName().toString().equals(name);
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        if (isSame(dir, mainDirName)) {
            return FileVisitResult.CONTINUE;
        }
        String[] parts = dir.getFileName().toString().split("_");
        for (String part : parts) {
            String[] lexemes = part.split("=");
            stat.set(Statistics.Field.valueOf(lexemes[0]), lexemes[1]);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (isSame(file, MATRIX_FILE)) {
            matrix = FileReadable.init(matrixClass, file);
            return FileVisitResult.CONTINUE;
        }
        if (isSame(file, RHS_VECTOR_FILE)) {
            rhsVector = new Vector(file);
            return FileVisitResult.CONTINUE;
        }
        if (isSame(file, EXACT_SOLUTION_FILE)) {
            exactSolution = new Vector(file);
            return FileVisitResult.CONTINUE;
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        throw new IllegalArgumentException("Error occurred during walking in directory: " + exc.getMessage());
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        if (matrix == null || rhsVector == null || exactSolution == null) {
            return FileVisitResult.CONTINUE;
        }
        M method = Method.init(methodClass);
        Vector solution = method.evaluate(matrix, rhsVector);
        System.out.printf("%s\n%s\n\n", dir.toString(), solution.toString());
        double absoluteError = exactSolution.subtract(solution).norm();
        stat.setIterations(method.getIterations());
        stat.setRatioError(absoluteError / exactSolution.norm());
        stat.setAbsoluteError(absoluteError);
        stat.setCondA(absoluteError / (rhsVector.subtract(solution).norm() / rhsVector.norm()));
        statisticsList.add(new Statistics(stat));
        resetIteration();
        return FileVisitResult.CONTINUE;
    }
}
