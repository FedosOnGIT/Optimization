package expressions.unaryOp;

import expressions.Expression;
import expressions.Variable;
import structures.matrices.Vector;

import java.util.Map;

public abstract class UnaryOperation implements Expression {
    private final static Map<String, Integer> getHashByOp = Map.of(
            "-", 1,
            "ln", 2,
            "exp", 3,
            "square", 4
    );

    protected Expression x;
    protected String operationType;
    protected int operationHash;
    protected StringBuilder stringFormat;

    public UnaryOperation(Expression x, String operationType) {
        this.x = x;
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
            stringFormat.append(operationType).append('(').append(x).append(')');
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
        UnaryOperation arg = (UnaryOperation) obj;
        return operationHash == arg.operationHash && this.x.equals(arg);
    }

    @Override
    public int hashCode() {
        final int b = 29;
        final int c = 71 * 17 * 39;
        return (b * x.hashCode() + operationHash) % c;
    }

    @Override
    public Double evaluate(Vector v) {
        return doEvaluate(x.evaluate(v));
    }

    protected abstract Double doEvaluate(Double a);

    @Override
    public Expression diff(Variable v) {
        return doDiff(x.diff(v));
    }

    protected abstract Expression doDiff(Expression dx);
}
