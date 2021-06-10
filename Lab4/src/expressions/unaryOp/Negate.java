package expressions.unaryOp;

import expressions.Expression;

public class Negate extends UnaryOperation {
    public Negate(Expression x) {
        super(x, "-");
    }

    @Override
    protected Double doEvaluate(Double a) {
        return -a;
    }

    @Override
    protected Expression doDiff(Expression dx) {
        return new Negate(dx);
    }
}
