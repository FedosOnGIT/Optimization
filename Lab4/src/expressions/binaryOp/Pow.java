package expressions.binaryOp;

import expressions.Expression;
import expressions.unaryOp.Exp;
import expressions.unaryOp.Ln;

public class Pow extends BinaryOperation {
    public Pow(Expression x, Expression y) {
        super(x, y, "pow");
    }

    @Override
    public String toString() {
        if (stringFormat == null) {
            stringFormat = new StringBuilder();
            stringFormat.append('(').append(x).append(')').append('^').append('(').append(y).append(')');
        }
        return stringFormat.toString();
    }

    @Override
    protected Double doEvaluate(Double a, Double b) {
        return Math.pow(a, b);
    }

    @Override
    protected Expression doDiff(Expression dx, Expression dy) {
        final Expression lnx = new Ln(x);
        return new Multiply(
                new Exp(
                        new Multiply(y, lnx)
                ),
                new Add(
                        new Multiply(dy, lnx),
                        new Multiply(y, new Divide(dx, x))
                )
        );
    }
}
