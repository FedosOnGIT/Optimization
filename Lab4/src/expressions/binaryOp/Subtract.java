package expressions.binaryOp;

import expressions.Expression;

public class Subtract extends BinaryOperation {
    public Subtract(Expression firstArg, Expression secondArg) {
        super(firstArg, secondArg, "-");
    }

    @Override
    protected Double doEvaluate(Double a, Double b) {
        return a - b;
    }

    @Override
    protected Expression doDiff(Expression dx, Expression dy) {
        return new Subtract(dx, dy);
    }
}
