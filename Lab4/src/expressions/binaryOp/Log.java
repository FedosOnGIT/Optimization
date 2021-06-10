package expressions.binaryOp;

import expressions.Const;
import expressions.Expression;
import expressions.unaryOp.Ln;

public class Log extends BinaryOperation {
    public Log(Expression x, Expression y) {
        super(x, y, "log");
    }

    @Override
    public String toString() {
        if (stringFormat == null) {
            stringFormat = new StringBuilder();
            stringFormat.append(operationType).append('(').append(x).append(')').append('(').append(y).append(')');
        }
        return stringFormat.toString();
    }

    @Override
    protected Double doEvaluate(Double a, Double b) {
        return Math.log(b) / Math.log(a);
    }

    @Override
    protected Expression doDiff(Expression dx, Expression dy) {
        final Expression lnx = new Ln(x);
        return new Divide(
                new Subtract(
                        new Multiply(new Divide(dy, y), lnx),
                        new Multiply(new Divide(dx, x), new Ln(y))
                ),
                new Multiply(lnx, lnx)
        );
    }
}
