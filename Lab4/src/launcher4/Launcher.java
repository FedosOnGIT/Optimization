package launcher4;

import expressions.Const;
import expressions.Variable;
import expressions.binaryOp.*;
import expressions.unaryOp.Ln;
import expressions.unaryOp.Square;
import methods4.AbstractMethod;
import methods4.SteepestDescent;
import methods4.newton_methods.ChoosingNewton;
import methods4.newton_methods.ClassicNewton;
import methods4.newton_methods.DescentNewton;
import methods4.one_dim_methods.BrentMethod;
import methods4.one_dim_methods.GoldenRatioMethod;
import methods4.quasi_methods.DfpMethod;
import methods4.quasi_methods.PowellMethod;
import structures4.Recorder;
import structures.matrices.Vector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Launcher {
    private final static String TAB = "   ";

    private final static double EPS = 1e-7;

    private final static Path TESTS = Path.of("Lab4/tests");

    // task1
    private final static List<AbstractMethod> TASK_1_METHODS = List.of(
            new ClassicNewton(), new DescentNewton(new BrentMethod()), new ChoosingNewton(new BrentMethod()), new SteepestDescent(new BrentMethod())
    );
    private final static List<FunctionData> TASK_1_1 = List.of(
            new FunctionData(new Add(new Add(new Multiply(new Const(7), new Square(new Variable("x1"))), new Multiply(new Const(4), new Square(new Variable("x2")))), new Add(new Add(new Multiply(new Const(5), new Variable("x1")), new Variable("x2")), new Const(2))), 2),
            new FunctionData(new Ln(new Add(new Add(new Multiply(new Const(6), new Square(new Variable("x2"))), new Square(new Square(new Variable("x1")))), new Const(9))), 2),
            new FunctionData(new Add(new Subtract(new Square(new Variable("x1")), new Square(new Variable("x2"))), new Square(new Square(new Variable("x2")))), 2)
    );
    private final static List<List<Vector>> TASK_1_1_STARTING_POINTS = List.of(
            List.of(new Vector(-4., 0.5), new Vector(-2., 1.), new Vector(-4., 100.)),
            List.of(new Vector(-0.5, 0.5), new Vector(1., 1.), new Vector(10., 20.)),
            List.of(new Vector(0., -1.), new Vector(0.1, -2.), new Vector(0.8, 1.))
    );
    private final static List<FunctionData> TASK_1_2 = List.of(
            new FunctionData(new Subtract(new Add(new Square(new Variable("x1")), new Square(new Variable("x2"))), new Multiply(new Const(1.2), new Multiply(new Variable("x1"), new Variable("x2")))), 2),
            new FunctionData(new Add(new Multiply(new Const(100), new Square(new Subtract(new Variable("x2"), new Square(new Variable("x1"))))), new Square(new Subtract(Const.ONE, new Variable("x1")))), 2)
    );
    private final static List<List<Vector>> TASK_1_2_STARTING_POINTS = List.of(
            List.of(new Vector(4., 1.)),
            List.of(new Vector(-1.2, 1.))
    );

    // task2
    private final static List<AbstractMethod> TASK_2_METHODS = List.of(
            new PowellMethod(new GoldenRatioMethod()), new DfpMethod(new GoldenRatioMethod()), new ChoosingNewton(new GoldenRatioMethod())
    );
    private final static List<FunctionData> TASK_2 = List.of(
            new FunctionData(new Add(new Multiply(new Const(100), new Square(new Subtract(new Variable("x2"), new Square(new Variable("x1"))))), new Square(new Subtract(Const.ONE, new Variable("x1")))), 2),
            new FunctionData(new Add(new Square(new Add(new Square(new Variable("x1")), new Subtract(new Variable("x2"), new Const(11)))), new Square(new Add(new Variable("x1"), new Subtract(new Square(new Variable("x2")), new Const(7))))), 2),
            new FunctionData(new Add(new Add(new Square(new Add(new Variable("x1"), new Multiply(new Const(10), new Variable("x2")))), new Multiply(new Const(5), new Square(new Subtract(new Variable("x3"), new Variable("x4"))))), new Add(new Square(new Square(new Subtract(new Variable("x2"), new Multiply(new Const(2), new Variable("x3"))))), new Multiply(new Const(10), new Square(new Square(new Subtract(new Variable("x1"), new Variable("x4"))))))), 4),
            new FunctionData(new Subtract(new Subtract(new Const(100), new Divide(new Const(2), new Add(new Add(new Const(1), new Square(new Divide(new Subtract(new Variable("x1"), new Const(1)), new Const(2)))), new Square(new Divide(new Subtract(new Variable("x2"), Const.ONE), new Const(3)))))), new Divide(Const.ONE, new Add(new Add(Const.ONE, new Square(new Divide(new Subtract(new Variable("x1"), new Const(2)), new Const(2)))), new Square(new Divide(new Subtract(new Variable("x2"), Const.ONE), new Const(3)))))), 2)
    );
    private final static List<List<Vector>> TASK_2_STARTING_POINTS = List.of(
            List.of(new Vector(-1.2, 1.), new Vector(-2., -2.) , new Vector(1.5, 4.)),
            List.of(new Vector(-1., -20.), new Vector(-1., 2.), new Vector(3., 1.5)),
            List.of(new Vector(1., 2., 3., 4.), new Vector(5., -6., 2., 1.), new Vector(10., 20., 30., -10.)),
            List.of(new Vector(-3., -4.), new Vector(20., -4.), new Vector(-2., 0.))
    );

    /*
    private final static List<AbstractMethod> TASK_BONUS_METHODS = List.of();
    private final static List<FunctionData> TASK_BONUS;
    static {
        Expression expression = Const.ZERO;
        final Expression HUNDRED = new Const(100);
        for (int i = 1; i < 100; i++) {
            Variable x_i = createVariable(i);
            expression = new Add(expression, new Add(new Multiply(HUNDRED, new Subtract(createVariable(i + 1), new Square(x_i))), new Square(new Subtract(Const.ONE, x_i))));
        }
        TASK_BONUS = List.of(new FunctionData(expression, 100));
    }
    private final static List<List<Vector>> TASK_BONUS_STARTING_POINTS = List.of(List.of(
            new Vector(IntStream.range(0, 100).asDoubleStream()), new Vector(IntStream.range(0, 100).mapToDouble(i -> random.nextDouble()))
    ));
    */

    private static String getTabs(int tabCount) {
        return IntStream.range(0, tabCount).mapToObj(i -> TAB).collect(Collectors.joining());
    }

    private static void log(int tabCount, String name) {
        System.out.printf("%s==== %s ====%n", getTabs(tabCount), name);
    }

    private static void logTask(String task) {
        log(1, task);
    }

    private static void logFunction(String function) {
        log(3, function);
    }

    private static void logStartingPoint(String startingPoint) {
        log(4, startingPoint);
    }

    private static void logMethod(String methodName) {
        log(2, methodName);
    }

    private static void logInfo(String info) {
        System.out.println(getTabs(5) + info);
    }

    private static void createDir(Path dir) throws IOException {
        if (Files.notExists(dir)) {
            Files.createDirectories(dir);
        }
    }

    private static void task(String taskName, List<AbstractMethod> methods, List<FunctionData> functions, List<List<Vector>> startingPoints) throws IOException {
        assert functions.size() == startingPoints.size();

        logTask(taskName);
        Path taskDir = TESTS.resolve(taskName);

        Map<String, List<Long>> iterationsByMethod = new HashMap<>();

        for (AbstractMethod method : methods) {
            String methodName = method.getClass().getSimpleName();
            logMethod(methodName);
            Path methodDir = taskDir.resolve(methodName);
            createDir(methodDir);

            Recorder iterationsRecorder = new Recorder("function", "point", "iterations");
            for (int i = 0; i < functions.size(); i++) {
                FunctionData functionData = functions.get(i);
                String functionName = "f" + (i + 1);
                logFunction(functionName + " = " + functionData.toString());
                for (Vector point : startingPoints.get(i)) {
                    String pointName = point.toString();
                    logStartingPoint("point = " + pointName);

                    Vector min = method.min(functionData.getFunction(), functionData.getHessian(), functionData.getGradient(), point, EPS);

                    Recorder recorder = method.getRecorder();
                    recorder.record(methodDir.resolve(functionName + "_point=" + pointName + ".csv"));

                    long iterations = recorder.getIterations() - 1;
                    logInfo(String.format("min = %s, iterations = %d", min.toString(), iterations));
                    iterationsRecorder.addIteration(functionName, pointName, iterations);

                    if (point == startingPoints.get(i).get(0)) {
                        iterationsByMethod.computeIfAbsent(methodName, k -> new ArrayList<>());
                        iterationsByMethod.get(methodName).add(iterations);
                    }
                }
                System.out.printf("%n");
            }
            iterationsRecorder.record(methodDir.resolve("iterations.csv"));
            System.out.printf("%n%n");
        }

        List<String> row = new ArrayList<>();
        row.add("method");
        for (int i = 0; i < functions.size(); i++) {
            row.add("f" + (i + 1) + "_point=" + startingPoints.get(i).get(0).toString().replace(',', ';'));
        }
        row.add("avg");
        Recorder recorder = new Recorder(row);
        row.clear();
        for (var pair : iterationsByMethod.entrySet()) {
            row.add(pair.getKey());
            Long sum = 0L;
            for (var iterations : pair.getValue()) {
                sum += iterations;
                row.add(iterations.toString());
            }
            row.add((sum / pair.getValue().size()) + "");
            recorder.addIteration(row);
            row.clear();
        }
        recorder.record(taskDir.resolve("iterations.csv"));
    }

    private static void task1() throws IOException {
        task("task1.1", TASK_1_METHODS, TASK_1_1, TASK_1_1_STARTING_POINTS);
        task("task1.2", TASK_1_METHODS, TASK_1_2, TASK_1_2_STARTING_POINTS);
    }

    private static void task2() throws IOException {
        task("task2", TASK_2_METHODS, TASK_2, TASK_2_STARTING_POINTS);
    }

    /*
    private static void taskBonus() throws IOException {
        task("task_bonus", TASK_BONUS_METHODS, TASK_BONUS, TASK_BONUS_STARTING_POINTS);
    }
    */

    public static void main(String[] args) {
        try {
            task1();
            task2();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
