package expressions.unaryOp;

import expressions.Const;
import expressions.Expression;
import expressions.binaryOp.Multiply;

public class Square extends UnaryOperation {
    public Square(Expression x) {
        super(x, "square");
    }

    @Override
    protected Double doEvaluate(Double a) {
        return a * a;
    }

    @Override
    protected Expression doDiff(Expression dx) {
        return new Multiply(new Const(2.), new Multiply(x, dx));
    }

    @Override
    public String toString() {
        if (stringFormat == null) {
            stringFormat = new StringBuilder();
            stringFormat.append(x).append('^').append(2);
        }
        return stringFormat.toString();
    }
}
