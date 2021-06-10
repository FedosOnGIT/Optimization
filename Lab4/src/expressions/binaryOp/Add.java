package expressions.binaryOp;

import expressions.Expression;

public class Add extends BinaryOperation {
    public Add(Expression x, Expression y) {
        super(x, y, "+");
    }

    @Override
    protected Double doEvaluate(Double a, Double b) {
        return a + b;
    }

    @Override
    protected Expression doDiff(Expression dx, Expression dy) {
        return new Add(dx, dy);
    }
}
