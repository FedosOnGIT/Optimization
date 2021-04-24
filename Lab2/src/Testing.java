import methods.*;
import quadraticMethods.*;
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
    private static final double EPS = 1e-5;
    private static final QuadraticFunction function1 = new QuadraticFunction(
            new DiagonalMatrix(
                    new double[]{4, 6}),
            new Vector(new double[]{0, 0}),
            0);
    private static final QuadraticFunction function2 = new QuadraticFunction(
            new DiagonalMatrix(
                    new double[]{2, 4000}),
            new Vector(new double[]{2, 10}),
            0);
    private static final QuadraticFunction function3 = new QuadraticFunction(
            new SquareMatrix(
                    new double[][]{{128, 126}, {126, 128}},
                    new double[]{2, 254}),
            new Vector(new double[]{-10, 30}),
            13);

    static QuadraticFunction generateFunction(int dimension, double condition) {
        assert condition >= 1;
        double[] matrix = new double[dimension];
        matrix[0] = 2;
        matrix[dimension - 1] = condition * 2;
        for (int i = 1; i < dimension - 1; i++) {
            matrix[i] = Math.random() * (condition * 2 - 2) + 2;
        }
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

    static void generate() {
        QuadraticMethod[] methods = {
                new GradientDescent(),
                new SteepestDescent(new GoldenRatioMethod()),
                new ConjugateGradients()};
        for (int i = 2; i < 10000; i += 500) {
            for (double k = 1; k < 1000; k += 0.5) {
                QuadraticFunction function = generateFunction(i, k);
                Vector start = generateVector(i);
                Arrays.stream(methods).map(m -> m.minimum(function, start, EPS));
            }
        }
    }

    static void writeAllResults(PrintWriter writer, MethodResult<Vector> result) {
        writer.println(result.getMinimal().toString());
        writer.println(result.iterations());
        for (int i = 0; i < result.iterations(); i++) {
            writer.println(result.get(i).toString());
        }
    }

    private static double randomVectorTest(final QuadraticMethod method, final QuadraticFunction func) {
        return Stream
                .generate(() -> generateVector(2))
                .limit(100)
                .map(v -> method.minimum(func, v, EPS).iterations())
                .collect(Collectors.averagingInt(x -> x));
    }

    private static void runIterations(final QuadraticMethod method, final String name, final QuadraticFunction function) {
        try (PrintWriter writer = createLogger(name)) {
            writer.println(method.getClass().getSimpleName());
            writeAllResults(writer, method.minimum(function, generateVector(2), EPS));
        } catch (final IOException e) {
            System.out.println("Can't write!");
        }
    }

    private static PrintWriter createLogger(final String name) throws IOException {
        return new PrintWriter(Files.newBufferedWriter(Path.of("logs/" + name + ".cvs")));
    }

    public static void test1() {
        Method[] methods = {new DichotMethod(EPS),
                new GoldenRatioMethod()};
        try (PrintWriter writer = createLogger("test1_log")) {
            writer.print("method name,");
            writer.println("avg iterations");
            Arrays.stream(methods)
                    .map(m -> Map.entry(
                            m.getClass().getSimpleName(),
                            randomVectorTest(new SteepestDescent(m), function3)))
                    .forEach(me -> {
                        writer.print(me.getKey());
                        writer.print(",");
                        writer.println(me.getValue());
                    });
        } catch (IOException e) {
            System.err.println("Write or create file exception");
        }
    }

    public static void test2() throws IOException {
        List<QuadraticMethod> methods = List.of(
                new GradientDescent(),
                new SteepestDescent(new GoldenRatioMethod()),
                new ConjugateGradients()
        );
        List<QuadraticFunction> functions = List.of(
                function1,
                function2,
                function3
        );

        Map<Integer, QuadraticFunction> functionMap = IntStream.range(0, functions.size())
                .boxed()
                .collect(Collectors.toMap(Function.identity(), functions::get));
        methods.forEach(
                m -> functionMap.forEach(
                        (key, value) -> runIterations(
                                m,
                                "Function" + (key + 1) + "_" + m.getClass().getSimpleName(),
                                value
                        )));

//        QuadraticFunction function3 = new QuadraticFunction(
//                new SquareMatrix(new double[][]{{2, 1}, {1, 18}}, new double[]{10 - Math.sqrt(65), 10 + Math.sqrt(65)}),
//                new Vector(new double[]{5, 6}),
//                0
//        );
    }

    public static void main(String[] args) {
        try {
            if (!Files.isDirectory(Path.of("logs"))) {
                Files.createDirectory(Path.of("logs"));
            }
            test2();
        } catch (IOException e) {
            System.err.println("Can't create logs folder");
        }

    }
}
