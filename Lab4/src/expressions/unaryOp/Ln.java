package expressions.unaryOp;

import expressions.Expression;
import expressions.binaryOp.Divide;

public class Ln extends UnaryOperation {
    public Ln(Expression x) {
        super(x, "ln");
    }

    @Override
    protected Double doEvaluate(Double a) {
        return Math.log(a);
    }

    @Override
    protected Expression doDiff(Expression dx) {
        return new Divide(dx, x);
    }
}
