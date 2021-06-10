package expressions.binaryOp;

import expressions.Expression;

public class Multiply extends BinaryOperation {
    public Multiply(Expression firstArg, Expression secondArg) {
        super(firstArg, secondArg, "*");
    }

    @Override
    protected Double doEvaluate(Double a, Double b) {
        return a * b;
    }

    @Override
    protected Expression doDiff(Expression dx, Expression dy) {
        return new Add(new Multiply(dx, y), new Multiply(x, dy));
    }
}
