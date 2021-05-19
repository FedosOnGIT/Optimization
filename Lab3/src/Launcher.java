import structures.Statistics;
import structures.generators.Generator;
import structures.generators.Task2Generator;
import structures.generators.GilbertGenerator;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Launcher {
    private enum TASK {
        N, K, RATIO_ERROR, ABSOLUTE_ERROR, ITERATIONS, COND_A
    }
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
    private static Statistics stat;

    private final static Path TESTS = Path.of("tests");
    private final static Path TASK2_PATH = TESTS.resolve("task2");
    private final static Path TASK3_PATH = TESTS.resolve("task3");
    private final static Path TASK4_PATH = TESTS.resolve("task4");
    private final static Path TASK5_PATH = TESTS.resolve("task5");

    private final static Map<Integer, Integer> TASK2_ARGS = Map.of(10, 10, 100, 10, 1000, 10);
    private final static List<Integer> TASK3_ARGS = List.of(10, 100, 1000);

    private static void log(Writer writer, TASK... tasks) throws IOException {
        for (int i = 0; i < tasks.length; i++) {
            String value = switch (tasks[i]) {
                case N -> stat.getN().toString();
                case K -> stat.getK().toString();
                case ITERATIONS -> stat.getIterations().toString();
                case RATIO_ERROR -> stat.getRatioError().toString();
                case ABSOLUTE_ERROR -> stat.getAbsoluteError().toString();
                case COND_A -> stat.getCondA().toString();
            };
            writer.write(value);
            if (i != tasks.length - 1) {
                writer.write(',');
            }
        }
        writer.write('\n');
    }

    private static void logHead(Writer writer, TASK... tasks) throws IOException {
        for (int i = 0; i < tasks.length; i++) {
            writer.write(tasks[i].toString());
            if (i != tasks.length - 1) {
                writer.write(',');
            }
        }
        writer.write('\n');
    }

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
                    Path dir = taskDir.resolve(String.format("n=%d_k=%d", n, k));
                    Files.createDirectories(dir);
                    generator.generate(dir);
                }
            } else {
                Path dir = taskDir.resolve("n=" + n);
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
            generateData(TASK4_PATH.resolve("task2Generator"), Task2Generator.class, task2Args);
        }
        if (task3Args != null) {
            generateData(TASK4_PATH.resolve("task3Generator"), GilbertGenerator.class, toMapTestData(task3Args));
        }
    }

    private static void generateTestData() throws Exception {
        Files.createDirectories(TESTS);
        generateTask2TestData(TASK2_ARGS);
        generateTask3TestData(TASK3_ARGS);
        generateTask4TestData(TASK2_ARGS, TASK3_ARGS);
    }

    private static void task2() throws IOException {
        private static final SimpleFileVisitor<Path> DELETE_VISITOR = new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                try (var reader = Files.newBufferedReader(file)) {

                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return super.preVisitDirectory(dir, attrs);
            }
        };
    }

    private static void task3() {

    }

    private static void task4() {

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
