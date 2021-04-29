import methods.BrentMethod;
import methods.DichotMethod;
import methods.GoldenRatioMethod;
import methods.Method;
import quadraticMethods.ConjugateGradients;
import quadraticMethods.GradientDescent;
import quadraticMethods.QuadraticMethod;
import quadraticMethods.SteepestDescent;
import structures.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Testing {
    private static final double MAX_RANDOM_CORD = 20;
    private static final double EPS = 1e-2;
    public static final QuadraticFunction FUNCTION_1 = new QuadraticFunction(
            new DiagonalMatrix(
                    new double[]{4, 6}),
            new Vector(new double[]{0, 0}),
            0);
    public static final QuadraticFunction FUNCTION_2 = new QuadraticFunction(
            new DiagonalMatrix(
                    new double[]{2, 4000}),
            new Vector(new double[]{2, 10}),
            0);
    public static final QuadraticFunction FUNCTION_3 = new QuadraticFunction(
            new SquareMatrix(
                    new double[][]{{128, 126}, {126, 128}},
                    new double[]{2, 254}),
            new Vector(new double[]{-10, 30}),
            13);
    public static final List<QuadraticFunction> FUNCTIONS = List.of(
            FUNCTION_1,
            FUNCTION_2,
            FUNCTION_3
    );
    public static final List<QuadraticMethod> QUADRATIC_METHODS = List.of(
            new GradientDescent(),
            new SteepestDescent(new GoldenRatioMethod()),
            new ConjugateGradients()
    );
    public static final List<Method> LINEAR_METHODS = List.of(
            new DichotMethod(EPS/10),
            new GoldenRatioMethod(),
            new BrentMethod());


    static QuadraticFunction generateFunction(int dimension, double condition) {
        assert condition >= 1;
        double[] matrix = new double[dimension];
        matrix[0] = 2;
        matrix[dimension - 1] = condition * 2;
        for (int i = 1; i < dimension - 1; i++) {
            matrix[i] = Math.random() * (matrix[dimension - 1] - matrix[0]) + matrix[0];
        }
        Arrays.parallelSort(matrix);
        double[] vector = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            vector[i] = Math.random() * 2000;
        }
        return new QuadraticFunction(new DiagonalMatrix(matrix), new Vector(vector), Math.random() * 2000);
    }

    static Vector generateVector(int number) {
        double[] vector = new double[number];
        Arrays.setAll(vector, i -> Math.random() * MAX_RANDOM_CORD);
        return new Vector(vector);
    }

    private static double randomVectorTest(final QuadraticMethod method, final QuadraticFunction func) {
        return Stream
                .generate(() -> generateVector(2))
                .limit(100)
                .map(v -> method.minimum(func, v, EPS))
                .collect(Collectors.averagingInt(MethodResult::iterations));
    }

    private static void runIterations(final QuadraticMethod method, final String name, final QuadraticFunction function) {
        try (PrintWriter writer = createLogger(name)) {
            method.minimum(function, generateVector(2), EPS).write(writer);
        } catch (final IOException e) {
            System.out.println("Can't write!");
        }
    }

    private static PrintWriter createLogger(final String name) throws IOException {
        return new PrintWriter(Files.newBufferedWriter(Path.of("logs/" + name + ".csv")));
    }

    public static void task1() {
        try (PrintWriter writer = createLogger("task1")) {
            writer.println("method name,avg iterations");
            LINEAR_METHODS.stream()
                    .collect(Collectors.toMap(
                            m -> m.getClass().getSimpleName(),
                            m -> randomVectorTest(new SteepestDescent(m), FUNCTION_3)))
                    .forEach((key, value) -> {
                        writer.print(key);
                        writer.print(',');
                        writer.println(value);
                    });
        } catch (IOException e) {
            System.err.println("Write or create file exception");
        }
    }

    public static void task2() {
        Map<Integer, QuadraticFunction> functionMap = IntStream.range(0, FUNCTIONS.size())
                .boxed()
                .collect(Collectors.toMap(Function.identity(), FUNCTIONS::get));
        QUADRATIC_METHODS.forEach(
                m -> functionMap.forEach(
                        (key, value) -> runIterations(
                                m,
                                "task2_function" + (key + 1) + "_" + m.getClass().getSimpleName(),
                                value)));
    }

    public static void task3() {
        QUADRATIC_METHODS.forEach(m -> {
            try (PrintWriter writer = createLogger("task3_" + m.getClass().getSimpleName())) {
                for (int n = 10; n <= 10000; n *= 10) {
                    System.out.println("Progress: n = " + n);
                    writer.println("n = " + n);
                    for (int k = 1; k <= 2000; k += 100) {
                        System.out.println((k - 1) / 20 + "%");

                        final int deg = n;
                        QuadraticFunction function = generateFunction(deg, k);

                        Double avgIter = IntStream.range(0, 5)
                                .mapToObj(x -> {
                                    Vector start = generateVector(deg);
                                    return m.minimum(function, start, EPS);
                                })
                                .collect(Collectors.averagingInt(MethodResult::iterations));

                        writer.print(k);
                        writer.print(',');
                        writer.println(avgIter);
                    }
                    writer.println();
                }
            } catch (IOException e) {
                System.err.println("Write or create file exception");
            }
        });
    }

    public static void main(String[] args) {
        try {
            if (!Files.isDirectory(Path.of("logs"))) {
                Files.createDirectory(Path.of("logs"));
            }
            task1();
            task2();
            //task3();
        } catch (IOException e) {
            System.err.println("Can't create logs folder");
        }

    }
}
