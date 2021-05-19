package launcher;

import methods.Gauss;
import methods.LU;
import methods.Method;
import structures.FileReadable;
import structures.generators.Generator;
import structures.generators.Task2Generator;
import structures.generators.GilbertGenerator;
import structures.matrices.DenseMatrix;
import structures.matrices.Matrix;
import structures.matrices.ProfileMatrix;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final static Path TASK4_TASK3_GENERATOR_PATH = TASK4_PATH.resolve("task3Generator");
    private final static Path TASK5_PATH = TESTS.resolve("task5");

    // input test files
    public final static String MATRIX_FILE = "matrix.txt";
    public final static String RHS_VECTOR_FILE = "rhs_vector.txt";
    public final static String EXACT_SOLUTION_FILE = "exact_solution.txt";

    // output test files
    public final static String RESULT_FILE = "result.csv";
    public final static String RESULT_GAUSS_FILE = "result_gauss.csv";
    public final static String RESULT_LU_FILE = "result_lu.csv";

    private final static Map<Integer, Integer> TASK2_ARGS = Map.of(10, 10, 100, 10, 1000, 10);
    private final static List<Integer> TASK3_ARGS = List.of(10, 100, 1000);

    private static <T extends Generator> void generateData(Path taskDir, Class<T> generatorClazz,
                                                           Map<Integer, Integer> testData) throws Exception {
        Constructor<T> constructor = generatorClazz.getConstructor(int.class);
        Files.createDirectories(taskDir);
        Files.walkFileTree(taskDir, DELETE_VISITOR);
        for (var data : testData.entrySet()) {
            int n = data.getKey();
            Generator generator = constructor.newInstance(n);
            Integer k = data.getValue();
            if (k != null) {
                for (int i = 0; i <= k; i++) {
                    Path dir = taskDir.resolve(String.format(generateStringFormat(N, K), n, k));
                    Files.createDirectories(dir);
                    generator.generate(dir);
                }
            } else {
                Path dir = taskDir.resolve(String.format(generateStringFormat(N), n));
                Files.createDirectories(dir);
                generator.generate(dir);
            }
        }
    }

    private static void generateTask2TestData(Map<Integer, Integer> args) throws Exception {
        generateData(TASK2_PATH, Task2Generator.class, args);
    }

    private static void generateTask3TestData(List<Integer> args) throws Exception {
        generateData(TASK3_PATH, GilbertGenerator.class, toMapTestData(args));
    }

    private static Map<Integer, Integer> toMapTestData(List<Integer> list) {
        Map<Integer, Integer> map = new HashMap<>();
        list.forEach(v -> map.put(v, null));
        return map;
    }

    private static void generateTask4TestData(Map<Integer, Integer> task2Args, List<Integer> task3Args) throws Exception {
        if (task2Args != null) {
            generateData(TASK4_TASK2_GENERATOR_PATH, Task2Generator.class, task2Args);
        }
        if (task3Args != null) {
            generateData(TASK4_TASK3_GENERATOR_PATH, GilbertGenerator.class, toMapTestData(task3Args));
        }
    }

    private static void generateTestData() throws Exception {
        Files.createDirectories(TESTS);
        generateTask2TestData(TASK2_ARGS);
        generateTask3TestData(TASK3_ARGS);
        generateTask4TestData(TASK2_ARGS, TASK3_ARGS);
    }

    private static <T extends Matrix & FileReadable, M extends Method>
    void solveTask(Path dir, Class<T> matrixClass, Class<M> methodClass, String resultFileName, Statistics.Field... fields) throws IOException {
        SolverVisitor<T, M> solverVisitor = new SolverVisitor<>(matrixClass, methodClass);
        Files.walkFileTree(dir, solverVisitor);
        try (var writer = Files.newBufferedWriter(dir.resolve(resultFileName))) {
            Statistics.logHeadLn(writer, fields);
            List<Statistics> statisticsList = solverVisitor.getStatisticsList();
            statisticsList.sort(Comparator.comparing(Statistics::getN).thenComparing(Statistics::getK));
            for (Statistics stat : statisticsList) {
                stat.logLn(writer, fields);
            }
        }
    }

    private static void task2() throws IOException {
        solveTask(TASK2_PATH, ProfileMatrix.class, LU.class, RESULT_FILE, N, K, RATIO_ERROR, ABSOLUTE_ERROR);
    }

    private static void task3() throws IOException {
        solveTask(TASK3_PATH, ProfileMatrix.class, LU.class, RESULT_FILE, N, K, RATIO_ERROR, ABSOLUTE_ERROR);
    }

    private static void task4() throws IOException {
        solveTask(TASK4_TASK2_GENERATOR_PATH, DenseMatrix.class, Gauss.class, RESULT_GAUSS_FILE, N, K, RATIO_ERROR, ABSOLUTE_ERROR, ITERATIONS);
        solveTask(TASK4_TASK2_GENERATOR_PATH, DenseMatrix.class, LU.class, RESULT_LU_FILE, N, K, RATIO_ERROR, ABSOLUTE_ERROR, ITERATIONS);
        solveTask(TASK4_TASK3_GENERATOR_PATH, DenseMatrix.class, Gauss.class, RESULT_GAUSS_FILE, N, K, RATIO_ERROR, ABSOLUTE_ERROR, ITERATIONS);
        solveTask(TASK4_TASK3_GENERATOR_PATH, DenseMatrix.class, LU.class, RESULT_LU_FILE, N, K, RATIO_ERROR, ABSOLUTE_ERROR, ITERATIONS);
    }

    private static void task5() {

    }

    public static void main(String[] args) {
        try {
            generateTestData();
            task2();
            task3();
            task4();
            task5();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
