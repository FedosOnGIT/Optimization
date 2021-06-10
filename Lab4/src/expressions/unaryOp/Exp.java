package expressions.unaryOp;

import expressions.Expression;
import expressions.binaryOp.Divide;
import expressions.binaryOp.Multiply;

public class Exp extends UnaryOperation {
    public Exp(Expression x) {
        super(x, "exp");
    }

    @Override
    protected Double doEvaluate(Double a) {
        return Math.exp(a);
    }

    @Override
    protected Expression doDiff(Expression dx) {
        return new Multiply(new Exp(x), dx);
    }
}
