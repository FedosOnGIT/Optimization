package launcher;

import methods.ConjugateGradients;
import methods.Gauss;
import methods.LU;
import methods.Method;
import structures.generators.*;
import structures.matrices.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import static launcher.Statistics.Field.*;
import static launcher.Statistics.generateStringFormat;

public class Launcher {
    private static final SimpleFileVisitor<Path> DELETE_VISITOR = new SimpleFileVisitor<>() {
        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }
    };

    private final static Path TESTS = Path.of("tests");

    private final static Path TASK2_PATH = TESTS.resolve("task2");

    private final static Path TASK3_PATH = TESTS.resolve("task3");

    private final static Path TASK4_PATH = TESTS.resolve("task4");
    private final static Path TASK4_TASK2_GENERATOR_PATH = TASK4_PATH.resolve("task2Generator");
    private final static Path TASK4_GILBERT_GENERATOR_PATH = TASK4_PATH.resolve("gilbertGenerator");

    private final static Path TASK5_PATH = TESTS.resolve("task5");
    private final static Path TASK5_2_PATH = TASK5_PATH.resolve("2");
    private final static Path TASK5_3_PATH = TASK5_PATH.resolve("3");
    private final static Path TASK5_4_PATH = TASK5_PATH.resolve("4");

    private final static Path TABLES = TESTS.resolve("tables");

    // input test files
    public final static String MATRIX_FILE = "matrix.txt";
    public final static String RHS_VECTOR_FILE = "rhs_vector.txt";
    public final static String EXACT_SOLUTION_FILE = "exact_solution.txt";

    // output test files
    public final static String TASK2_RESULT_FILE = "task2.csv";

    public final static String TASK3_RESULT_FILE = "task3.csv";

    public final static String TASK4_RESULT_FILE_TASK2_GENERATOR_GAUSS = "task4_2_gauss.csv";
    public final static String TASK4_RESULT_FILE_TASK2_GENERATOR_LU = "task4_2_lu.csv";
    public final static String TASK4_RESULT_FILE_TASK3_GENERATOR_GAUSS = "task4_3_gauss.csv";
    public final static String TASK4_RESULT_FILE_TASK3_GENERATOR_LU = "task4_3_lu.csv";

    public final static String TASK5_RESULT_FILE_2 = "task5_2.csv";
    public final static String TASK5_RESULT_FILE_3 = "task5_3.csv";
    public final static String TASK5_RESULT_FILE_4 = "task5_4.csv";

    // Test data
    private final static Map<Integer, Integer> TASK2_ARGS = Map.of(10, 10, 100, 10, 1000, 10);
    private final static List<Integer> TASK3_ARGS = List.of(5, 10, 15, 25, 50, 75, 100, 175, 200, 250, 400, 650, 800, 1000);
    private final static List<Integer> TASK5_ARGS = List.of(2, 3, 4, 5, 6, 7, 8, 9, 10, 25, 50, 75, 100, 250, 500, 1000, 5000, 10_000, 25_000, 50_000, 75_000, 100_000);

    private static <T extends Generator> void generateData(Path taskDir, Class<T> generatorClazz,
                                                           Map<Integer, Integer> testData) {
        try {
            Constructor<T> constructor = generatorClazz.getConstructor(int.class);
            Files.createDirectories(taskDir);
            Files.walkFileTree(taskDir, DELETE_VISITOR);
            for (var data : testData.entrySet()) {
                int n = data.getKey();
                Generator generator = constructor.newInstance(n);
                Integer k = data.getValue();
                if (k != null) {
                    for (int i = 0; i <= k; i++) {
                        Path dir = taskDir.resolve(String.format(generateStringFormat(N, K), n, i));
                        Files.createDirectories(dir);
                        generator.generate(dir);
                    }
                } else {
                    Path dir = taskDir.resolve(String.format(generateStringFormat(N), n));
                    Files.createDirectories(dir);
                    generator.generate(dir);
                }
            }
        } catch (Exception e) {
            System.err.printf("generateData: taskDir=%s, generatorClass=%s, testData=%s%n",
                    taskDir.toString(), generatorClazz.toString(), testData.toString());
            e.printStackTrace();
            throw new IllegalStateException(e.getMessage());
        }
    }

    private static <T extends Generator> void generateData(Path taskDir, Class<T> generatorClazz, List<Integer> testData) {
        Map<Integer, Integer> map = new HashMap<>();
        testData.forEach(v -> map.put(v, null));
        generateData(taskDir, generatorClazz, map);
    }

    private static <T extends FileReadableMatrix, M extends Method>
    void solveTask(Path dir, Class<T> matrixClass, Class<M> methodClass, String resultFileName, Statistics.Field... fields) {
        SolverVisitor<T, M> solverVisitor = new SolverVisitor<>(matrixClass, methodClass, dir.getFileName().toString());
        try (var writer = Files.newBufferedWriter(TABLES.resolve(resultFileName))) {
            Statistics.logHeadLn(writer, fields);
            Files.walkFileTree(dir, solverVisitor);
            List<Statistics> statisticsList = solverVisitor.getStatisticsList();
            statisticsList.sort(Comparator.comparing(Statistics::getN).thenComparing(Statistics::getK));
            for (Statistics stat : statisticsList) {
                stat.logLn(writer, fields);
            }
        } catch (IOException e) {
            System.err.printf("solveTask: dir=%s, matrixClass=%s, methodClass=%s, resultFileName=%s, fields=%s%n",
                    dir, matrixClass.toString(), methodClass.toString(), resultFileName, Arrays.toString(fields));
            throw new IllegalStateException(e.getMessage());
        }
    }

    private static void generateTask2() {
        generateData(TASK2_PATH, Task2Generator.class, TASK2_ARGS);
    }

    private static void solveTask2() {
        solveTask(TASK2_PATH, ProfileMatrix.class, LU.class, TASK2_RESULT_FILE, N, K, RATIO_ERROR, ABSOLUTE_ERROR);
    }

    private static void generateTask3() {
        generateData(TASK3_PATH, GilbertGenerator.class, TASK3_ARGS);
    }

    private static void solveTask3() {
        solveTask(TASK3_PATH, ProfileMatrix.class, LU.class, TASK3_RESULT_FILE, N, RATIO_ERROR, ABSOLUTE_ERROR);
    }

    private static void generateTask4() {
        generateData(TASK4_TASK2_GENERATOR_PATH, Task2Generator.class, TASK2_ARGS);
        generateData(TASK4_GILBERT_GENERATOR_PATH, GilbertGenerator.class, TASK3_ARGS);
    }

    private static void solveTask4() {
        solveTask(TASK4_TASK2_GENERATOR_PATH, DenseMatrix.class, Gauss.class, TASK4_RESULT_FILE_TASK2_GENERATOR_GAUSS, N, K, RATIO_ERROR, ABSOLUTE_ERROR, ITERATIONS);
        solveTask(TASK4_TASK2_GENERATOR_PATH, ProfileMatrix.class, LU.class, TASK4_RESULT_FILE_TASK2_GENERATOR_LU, N, K, RATIO_ERROR, ABSOLUTE_ERROR, ITERATIONS);
        solveTask(TASK4_GILBERT_GENERATOR_PATH, DenseMatrix.class, Gauss.class, TASK4_RESULT_FILE_TASK3_GENERATOR_GAUSS, N, RATIO_ERROR, ABSOLUTE_ERROR, ITERATIONS);
        solveTask(TASK4_GILBERT_GENERATOR_PATH, ProfileMatrix.class, LU.class, TASK4_RESULT_FILE_TASK3_GENERATOR_LU, N, RATIO_ERROR, ABSOLUTE_ERROR, ITERATIONS);
    }

    private static void generateTask5() {
        generateData(TASK5_2_PATH, Task5_2Generator.class, TASK5_ARGS);
        generateData(TASK5_3_PATH, Task5_3Generator.class, TASK5_ARGS);
        generateData(TASK5_4_PATH, GilbertGenerator.class, TASK3_ARGS);
    }

    private static void solveTask5() {
        solveTask(TASK5_2_PATH, SparseMatrix.class, ConjugateGradients.class, TASK5_RESULT_FILE_2, N, ITERATIONS, RATIO_ERROR, ABSOLUTE_ERROR, COND_A);
        solveTask(TASK5_3_PATH, SparseMatrix.class, ConjugateGradients.class, TASK5_RESULT_FILE_3, N, ITERATIONS, RATIO_ERROR, ABSOLUTE_ERROR, COND_A);
        solveTask(TASK5_4_PATH, SparseMatrix.class, ConjugateGradients.class, TASK5_RESULT_FILE_4, N, ITERATIONS, RATIO_ERROR, ABSOLUTE_ERROR, COND_A);
    }

    private static void generateWithLog(Runnable runnable, String name) {
        System.out.print("generate data for " + name + ": START");
        runnable.run();
        System.out.println(" END");
    }

    private static void generateTasks() {
        generateWithLog(Launcher::generateTask2, "task2");
        generateWithLog(Launcher::generateTask3, "task3");
        generateWithLog(Launcher::generateTask4, "task4");
        generateWithLog(Launcher::generateTask5, "task5");
        System.out.println();
    }

    private static void solveTasks() {
        solveTask2();
        solveTask3();
        solveTask4();
        solveTask5();
    }

    public static void main(String[] args) {
        try {
            if (Files.notExists(TESTS)) {
                Files.createDirectories(TESTS);
            }
            if (Files.notExists(TABLES)) {
                Files.createDirectories(TABLES);
            }
        } catch (IOException e) {
            System.err.println("Can not create TESTS directory");
        }
//        generateTasks();
//        solveTasks();
    }
}
