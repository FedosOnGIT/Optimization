package expressions.binaryOp;

import expressions.Expression;

public class Divide extends BinaryOperation {
    public Divide(Expression x, Expression y) {
        super(x, y, "/");
    }

    @Override
    protected Double doEvaluate(Double a, Double b) {
        return a / b;
    }

    @Override
    protected Expression doDiff(Expression dx, Expression dy) {
        return new Divide(new Subtract(new Multiply(dx, y), new Multiply(dy, x)), new Multiply(y, y));
    }
}
