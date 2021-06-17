package expressions.binaryOp;

import expressions.Expression;
import expressions.Variable;
import structures.matrices.Vector;

import java.util.Map;

public abstract class BinaryOperation implements Expression {
    private final static Map<String, Integer> getHashByOp = Map.of(
            "+", 1,
            "-", 2,
            "*", 3,
            "/", 4,
            "log", 5,
            "pow", 6
    );

    protected Expression x, y;
    protected String operationType;
    protected int operationHash;
    protected StringBuilder stringFormat;

    public BinaryOperation(Expression x, Expression y, String operationType) {
        this.x = x;
        this.y = y;
        this.operationType = operationType;
        operationHash = getHashByOp(operationType);
    }

    public int getHashByOp(String op) {
        if (getHashByOp.containsKey(op)) {
            return getHashByOp.get(op);
        }
        throw new IllegalArgumentException(
                String.format("No operation %s.\nSupported operations: %s", op, getHashByOp.keySet().toString())
        );
    }

    @Override
    public String toString() {
        if (stringFormat == null) {
            stringFormat = new StringBuilder();
            stringFormat.append("(").append(x).append(" ").append(operationType).append(" ").append(y).append(")");
        }
        return stringFormat.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        BinaryOperation arg = (BinaryOperation) obj;
        return operationHash == arg.operationHash && x.equals(arg.x)
                && y.equals(arg.y);
    }

    @Override
    public int hashCode() {
        final int a = 29;
        final int b = 37;
        final int c = 71 * 31 * 39;
        return (a * x.hashCode() + b * y.hashCode() + operationHash) % c;
    }

    @Override
    public Double evaluate(Vector v) {
        return doEvaluate(x.evaluate(v), y.evaluate(v));
    }

    protected abstract Double doEvaluate(Double a, Double b);

    @Override
    public Expression diff(Variable v) {
        return doDiff(x.diff(v), y.diff(v));
    }

    protected abstract Expression doDiff(Expression dx, Expression dy);
}
