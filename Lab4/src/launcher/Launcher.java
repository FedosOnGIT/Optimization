package launcher;

import expressions.Const;
import expressions.Expression;
import expressions.Variable;
import expressions.binaryOp.*;
import expressions.unaryOp.Square;

import java.util.List;

import static expressions.Variable.createVariable;

public class Launcher {
    private final static List<FunctionData> TASK_1_2, TASK_2;
    private final static FunctionData TASK_BONUS;

    // init all tasks
    static {
        TASK_1_2 = List.of(
                new FunctionData(new Subtract(new Add(new Square(new Variable("x1")), new Square(new Variable("x2"))), new Multiply(new Const(1.2), new Multiply(new Variable("x1"), new Variable("x2")))), 2),
                new FunctionData(new Add(new Multiply(new Const(100), new Square(new Subtract(new Variable("x2"), new Square(new Variable("x1"))))), new Square(new Subtract(Const.ONE, new Variable("x1")))), 2)
        );

        TASK_2 = List.of(
                new FunctionData(new Add(new Multiply(new Const(100), new Square(new Subtract(new Variable("x2"), new Square(new Variable("x1"))))), new Square(new Subtract(Const.ONE, new Variable("x1")))), 2),
                new FunctionData(new Add(new Square(new Add(new Square(new Variable("x1")), new Subtract(new Variable("x2"), new Const(11)))), new Square(new Add(new Variable("x1"), new Subtract(new Square(new Variable("x2")), new Const(7))))), 2),
                new FunctionData(new Add(new Add(new Square(new Add(new Variable("x1"), new Multiply(new Const(10), new Variable("x2")))), new Multiply(new Const(5), new Square(new Subtract(new Variable("x3"), new Variable("x4"))))), new Add(new Pow(new Subtract(new Variable("x2"), new Multiply(new Const(2), new Variable("x3"))), new Const(4)), new Multiply(new Const(10), new Pow(new Subtract(new Variable("x1"), new Variable("x4")), new Const(4))))), 4),
                new FunctionData(new Subtract(new Subtract(new Const(100), new Divide(new Const(2), new Add(new Add(new Const(1), new Square(new Divide(new Subtract(new Variable("x1"), new Const(1)), new Const(2)))), new Square(new Divide(new Subtract(new Variable("x2"), Const.ONE), new Const(3)))))), new Divide(Const.ONE, new Add(new Add(Const.ONE, new Square(new Divide(new Subtract(new Variable("x1"), new Const(2)), new Const(2)))), new Square(new Divide(new Subtract(new Variable("x2"), Const.ONE), new Const(3)))))), 2)
        );

        Expression expression = Const.ZERO;
        final Expression HUNDRED = new Const(100);
        for (int i = 1; i < 100; i++) {
            Variable x_i = createVariable(i);
            expression = new Add(expression, new Add(new Multiply(HUNDRED, new Subtract(createVariable(i + 1), new Square(x_i))), new Square(new Subtract(Const.ONE, x_i))));
        }
        TASK_BONUS = new FunctionData(expression, 100);
    }

}
